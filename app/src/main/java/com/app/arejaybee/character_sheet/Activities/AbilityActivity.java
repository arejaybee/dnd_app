package com.app.arejaybee.character_sheet.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Ability;
import com.app.arejaybee.character_sheet.DialogFragments.AbilityDialogFragment;
import com.app.arejaybee.character_sheet.R;

import java.util.ArrayList;

public class AbilityActivity extends AbstractCharacterActivity implements AbilityDialogFragment.NoticeDialogListener {

    private RelativeLayout selectedAbilityRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildLinearLayout();
    }


    public void onClickNew(View v) {
        Ability ability = new Ability();
        AbilityDialogFragment aDialog = new AbilityDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("ability", ability);
        b.putInt("index", -1);
        aDialog.setArguments(b);
        aDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        rob.saveCharacter(this);
    }

    public void onClickEdit(View v) {

        if (selectedAbilityRow == null) {
            makeLongToast("Select an ability to edit.");
            return;
        }
        int abilityIndex = ((LinearLayout) selectedAbilityRow.getParent()).indexOfChild(selectedAbilityRow) - 1;
        int typeIndex = ((LinearLayout) selectedAbilityRow.getParent().getParent()).indexOfChild((LinearLayout) selectedAbilityRow.getParent());
        Ability ability = rob.getAbility(typeIndex, abilityIndex);

        AbilityDialogFragment aDialog = new AbilityDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("ability", ability);
        b.putInt("index", abilityIndex);
        aDialog.setArguments(b);
        aDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        rob.saveCharacter(this);
    }

    public void onClickDelete(View v) {
        if (selectedAbilityRow == null) {
            makeLongToast("Select an ability to delete.");
            return;
        }
        int abilityIndex = ((LinearLayout) selectedAbilityRow.getParent()).indexOfChild(selectedAbilityRow) - 1;
        int typeIndex = ((LinearLayout) selectedAbilityRow.getParent().getParent()).indexOfChild((LinearLayout) selectedAbilityRow.getParent());
        rob.removeAbility(typeIndex, abilityIndex);
        rob.saveCharacter(this);
        selectedAbilityRow = null;
        buildLinearLayout();
    }


    public void onDialogNegativeClick(AbilityDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(AbilityDialogFragment df) {
        Ability a = df.getAbility();
        if (a.getTitle().length() < 1) {
            makeLongToast("The title may not be blank.");
        } else {
            rob.addAbility(a, df.getIndex());
            rob.saveCharacter(this);
            df.getDialog().cancel();
            buildLinearLayout();

        }
    }

    private void buildLinearLayout() {
        LinearLayout layoutListLayout = (LinearLayout) findViewById(R.id.abll_layoutList);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 30);

        for (int layouts = 0; layouts < 4; layouts++) {
            LinearLayout innerLayout = (LinearLayout) layoutListLayout.getChildAt(layouts);
            ArrayList<Ability> abilities = rob.getAbilities()[layouts];
            while (innerLayout.getChildCount() > 2) {
                innerLayout.removeViewAt(1);
            }
            if (((RelativeLayout) innerLayout.getChildAt(0)).getChildAt(1).getRotation() != 0) {
                for (int i = 0; i < abilities.size(); i++) {
                    RelativeLayout abilityRow = new RelativeLayout(this);
                    abilityRow.setLayoutParams(lp);
                    abilityRow.setBackgroundResource(R.drawable.character_border);

                    TextView tvChar = new TextView(this);
                    tvChar.setText(abilities.get(i).getTitle());
                    tvChar.setTextSize(24);
                    RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    rllp.setMargins(10, 10, 10, 10);
                    tvChar.setId(View.generateViewId());
                    tvChar.setLayoutParams(rllp);
                    tvChar.setGravity(Gravity.CENTER_HORIZONTAL);
                    abilityRow.addView(tvChar);

                    ImageButton btnAbilityDropdown = new ImageButton(this);
                    btnAbilityDropdown.setImageResource(android.R.drawable.ic_menu_more);
                    rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    rllp.addRule(RelativeLayout.ALIGN_TOP, tvChar.getId());
                    rllp.addRule(RelativeLayout.ALIGN_BOTTOM, tvChar.getId());
                    btnAbilityDropdown.setLayoutParams(rllp);
                    btnAbilityDropdown.setBackgroundColor(Color.TRANSPARENT);
                    if (abilities.get(i).getDescription().equalsIgnoreCase("")) {
                        btnAbilityDropdown.setVisibility(View.INVISIBLE);
                    }
                    btnAbilityDropdown.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TextView desc = (TextView) ((RelativeLayout) v.getParent()).getChildAt(2);
                            int abilityIndex = ((LinearLayout) v.getParent().getParent()).indexOfChild((RelativeLayout) v.getParent()) - 1;
                            int typeIndex = ((LinearLayout) v.getParent().getParent().getParent()).indexOfChild((LinearLayout) v.getParent().getParent());
                            if (desc.getVisibility() == View.INVISIBLE) {
                                desc.setText(rob.getAbility(typeIndex, abilityIndex).getDescription());
                                desc.setTextSize(18);
                                desc.setVisibility(View.VISIBLE);
                            } else {
                                desc.setText("");
                                desc.setTextSize(0);
                                desc.setVisibility(View.INVISIBLE);
                            }
                            RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            rllp.setMargins(30, 15, 30, 20);
                            rllp.addRule(RelativeLayout.BELOW, tvChar.getId());
                            desc.setLayoutParams(rllp);
                            v.setRotation(v.getRotation() + 180);
                        }
                    });
                    abilityRow.addView(btnAbilityDropdown);

                    TextView tvDescription = new TextView(this);
                    tvDescription.setVisibility(View.INVISIBLE);
                    abilityRow.addView(tvDescription);


                    abilityRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (selectedAbilityRow != null) {
                                selectedAbilityRow.setBackgroundResource(R.drawable.character_border);
                            }
                            v.setBackgroundResource(R.drawable.selected_character_border);
                            selectedAbilityRow = (RelativeLayout) v;
                        }
                    });
                    innerLayout.addView(abilityRow, innerLayout.getChildCount() - 1);
                }
            }
        }
    }

    public void onClickToggleFeats(View v) {
        v.setRotation(v.getRotation() == 180 ? 0 : 180);
        buildLinearLayout();
    }

    public void onClickToggleFeatures(View v) {
        v.setRotation(v.getRotation() == 180 ? 0 : 180);
        buildLinearLayout();

    }

    public void onClickToggleTraits(View v) {
        v.setRotation(v.getRotation() == 180 ? 0 : 180);
        buildLinearLayout();

    }

    public void onClickToggleLanguage(View v) {
        v.setRotation(v.getRotation() == 180 ? 0 : 180);
        buildLinearLayout();

    }

}
