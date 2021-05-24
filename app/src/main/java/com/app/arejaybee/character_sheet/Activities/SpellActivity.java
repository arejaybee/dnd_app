package com.app.arejaybee.character_sheet.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Spell;
import com.app.arejaybee.character_sheet.DataObjects.SpellList;
import com.app.arejaybee.character_sheet.DialogFragments.SpellDialogFragment;
import com.app.arejaybee.character_sheet.R;

import java.util.ArrayList;

public class SpellActivity extends AbstractCharacterActivity implements SpellDialogFragment.NoticeDialogListener {
    private Spell spell;
    private View selectedSpell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildLayouts();
    }

    private void buildLayouts() {
        buildCastingLayout();
        buildSpellsLayout(false);
        buildEditTextListeners();
    }

    private void buildSpellsLayout(boolean fromToggle) {
        spell = null;
        selectedSpell = null;
        for (int i = 0; i < 10; i++) {
            View v = findViewByIdString("spib_toggle" + i);
            if (!rob.getSpellListAt(i).isHidden()) {
                v.setRotation(180);
                LinearLayout spellListLayout = (LinearLayout) findViewByIdString("spll_lvl" + i);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 0, 30);
                ArrayList<Spell> spells = rob.getSpellListAt(i).getSpells();
                spellListLayout.removeAllViews();
                int slIndex = i;
                for (int j = 0; j < spells.size(); j++) {
                    int index = j;
                    LinearLayout innerll = new LinearLayout(this);
                    innerll.setLayoutParams(lp);
                    innerll.setOrientation(LinearLayout.HORIZONTAL);
                    EditText etPrep = new EditText(this);
                    etPrep.setWidth(convertSpToPixels(50));
                    etPrep.setGravity(Gravity.CENTER);
                    etPrep.setId(View.generateViewId());
                    etPrep.setText("" + spells.get(j).getPrep());
                    etPrep.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etPrep.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() > 0) {
                                rob.getSpellsLists().get(slIndex).getSpells().get(index).setPrep(Integer.parseInt(s.toString()));
                            } else {
                                rob.getSpellsLists().get(slIndex).getSpells().get(index).setPrep(0);
                            }
                        }
                    });
                    innerll.addView(etPrep);

                    RelativeLayout rl = new RelativeLayout(this);
                    rl.setLayoutParams(lp);
                    rl.setBackgroundResource(R.drawable.character_border);

                    TextView tvChar = new TextView(this);
                    tvChar.setText(spells.get(j).getName());
                    tvChar.setTextSize(24);
                    tvChar.setLayoutParams(lp);
                    tvChar.setId(View.generateViewId());
                    tvChar.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    tvChar.setLayoutParams(rllp);
                    rl.addView(tvChar);


                    ImageButton btnAbilityDropdown = new ImageButton(this);
                    btnAbilityDropdown.setImageResource(android.R.drawable.ic_menu_more);
                    rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    rllp.addRule(RelativeLayout.ALIGN_TOP, tvChar.getId());
                    rllp.addRule(RelativeLayout.ALIGN_BOTTOM, tvChar.getId());
                    btnAbilityDropdown.setLayoutParams(rllp);
                    btnAbilityDropdown.setBackgroundColor(Color.TRANSPARENT);
                    if (spells.get(j).getDescription().equalsIgnoreCase("")) {
                        btnAbilityDropdown.setVisibility(View.INVISIBLE);
                    }

                    btnAbilityDropdown.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TextView desc = (TextView) ((RelativeLayout) v.getParent()).getChildAt(2);
                            int spellIndex = ((LinearLayout) v.getParent().getParent().getParent()).indexOfChild((View) v.getParent().getParent());
                            int listIndex = ((LinearLayout) v.getParent().getParent().getParent().getParent().getParent()).indexOfChild((View) v.getParent().getParent().getParent().getParent());
                            toggleViewVisibility(v, desc);
                        }
                    });
                    rl.addView(btnAbilityDropdown);

                    TextView tvDescription = new TextView(this);
                    tvDescription.setText(rob.getSpellListAt(slIndex).getSpellAt(index).getDescription());
                    tvDescription.setTextSize(18);
                    rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    rllp.setMargins(30, 10, 30, 0);
                    rllp.addRule(RelativeLayout.BELOW, tvChar.getId());
                    tvDescription.setLayoutParams(rllp);
                    tvDescription.setVisibility(View.GONE);
                    rl.addView(tvDescription);

                    rl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (selectedSpell != null) {
                                selectedSpell.setBackgroundResource(R.drawable.character_border);
                            }
                            selectedSpell = v;
                            int index = ((LinearLayout) v.getParent().getParent()).indexOfChild((View) v.getParent());
                            spell = spells.get(layoutPlacementToIndex(index, (LinearLayout) (v.getParent().getParent())));
                            selectedSpell.setBackgroundResource(R.drawable.selected_character_border);
                        }
                    });
                    innerll.addView(rl);
                    spellListLayout.addView(innerll, 0);
                }
            } else {
                if (!fromToggle) {
                    toggleViewVisibility(v, findViewByIdString("spll_lvl" + i));
                    toggleViewVisibility(v, findViewByIdString("spgl_lvl" + i));
                    v.setRotation(0);
                }
            }
        }
        switch (rob.getEdition()) {
            case ("5e"):
                findViewByIdString("spgl_lvl0").setVisibility(View.GONE);
                break;
            case ("3.5"):
                ((TextView) findViewById(R.id.sptv_lvl0)).setText("Level 0");
                break;
            default:
                break;
        }

    }

    private void buildCastingLayout() {
        Spinner spinner = (Spinner) findViewById(R.id.spsp_modifier);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.abilities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(rob.getSpellAbility()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                rob.setSpellAbility(value);
                rob.saveCharacter(context);
                updateCastingFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateCastingFields();
    }

    @SuppressLint("SetTextI18n")
    public void updateCastingFields() {
        switch (rob.getEdition()) {
            case ("5e"):
                int attk = rob.getSpellMod() + rob.getProficiency();
                ((TextView) findViewById(R.id.spet_spellAttack)).setText(Integer.toString(attk));
                ((TextView) findViewById(R.id.spet_spellDC)).setText(Integer.toString(8 + attk));
                break;
            case ("Pathfinder"):
            case ("3.5"):
                ((TextView) findViewById(R.id.spet_spellDC)).setText((Integer.toString(10 + rob.getSpellMod())));
                findViewById(R.id.sptv_attack).setVisibility(View.GONE);
                findViewById(R.id.spet_spellAttack).setVisibility(View.GONE);
                break;
        }
    }

    private void buildEditTextListeners() {
        int startingIndex = 0;
        if (rob.getEdition().equals("5e")) {
            startingIndex = 1;
        }
        for (int i = startingIndex; i < rob.getSpellsLists().size(); i++) {
            EditText etPrep = (EditText) findViewByIdString("spet_per" + i);
            EditText etCast = (EditText) findViewByIdString("spet_cast" + i);
            etPrep.setText("" + rob.getSpellListAt(i).getDaily());
            etCast.setText("" + rob.getSpellListAt(i).getUsed());
            etPrep.setInputType(InputType.TYPE_CLASS_NUMBER);
            etCast.setInputType(InputType.TYPE_CLASS_NUMBER);
            int index = i;
            TextWatcher tw = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    SpellList sl = rob.getSpellListAt(index);
                    sl.setDaily(Integer.parseInt(getTextFromView(etPrep).isEmpty() ? "0" : getTextFromView(etPrep)));
                    sl.setUsed(Integer.parseInt(getTextFromView(etCast).isEmpty() ? "0" : getTextFromView(etCast)));
                    rob.setSpellListAt(index, sl);
                    rob.saveCharacter(context);

                }
            };
            etPrep.addTextChangedListener(tw);
            etCast.addTextChangedListener(tw);
        }
    }

    //buttons
    public void onClickToggleSaves(View v) {
        View row = (View) v.getParent().getParent();
        int index = ((LinearLayout) row.getParent()).indexOfChild(row);

        toggleViewVisibility(v, findViewByIdString("spll_lvl" + index));
        toggleViewVisibility(v, findViewByIdString("spgl_lvl" + index));
        v.setRotation(0);


        SpellList sl = rob.getSpellListAt(index);
        sl.setHidden(!sl.isHidden());
        rob.setSpellListAt(index, sl);
        rob.saveCharacter(this);
        buildSpellsLayout(true);
    }

    public void onClickAdd(View v) {
        View row = (View) v.getParent().getParent().getParent();
        int index = ((LinearLayout) row.getParent()).indexOfChild(row);

        SpellDialogFragment aDialog = new SpellDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("name", "");
        b.putSerializable("description", "");
        b.putSerializable("level", index);
        aDialog.setArguments(b);

        aDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        rob.saveCharacter(this);
        buildSpellsLayout(false);
    }

    public void onClickEdit(View v) {
        if (spell == null || selectedSpell == null) {
            makeLongToast("No spell selected!");
            return;
        }
        SpellDialogFragment aDialog = new SpellDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("name", spell.getName());
        b.putSerializable("description", spell.getDescription());
        b.putSerializable("level", spell.getLevel());
        b.putSerializable("edit", true);
        b.putSerializable("index", rob.getSpellListAt(spell.getLevel()).getIndexOfSpell(spell));
        aDialog.setArguments(b);

        aDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        rob.saveCharacter(this);
        buildSpellsLayout(false);
    }

    public void onClickDelete(View v) {
        if (spell == null || selectedSpell == null) {
            makeLongToast("No spell selected!");
            return;
        }
        SpellList sl = rob.getSpellListAt(spell.getLevel());
        sl.removeSpell(spell);
        rob.setSpellListAt(spell.getLevel(), sl);
        spell = null;
        selectedSpell = null;
        rob.saveCharacter(this);
        buildSpellsLayout(false);
    }


    //dialogs
    public void onDialogNegativeClick(SpellDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(SpellDialogFragment df) {
        Spell s = df.getSpell();
        if (s.getName().length() < 1) {
            makeLongToast("The title may not be blank.");
        } else {
            SpellList sl = rob.getSpellListAt(s.getLevel());
            if (df.isEdit()) {
                sl.setSpellAt(df.getIndex(), s);
            } else {
                sl.addSpell(s);
            }
            rob.setSpellListAt(s.getLevel(), sl);
            rob.saveCharacter(this);
            df.getDialog().cancel();
            buildLayouts();
        }
    }


}
