package com.app.arejaybee.character_sheet.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Skill;
import com.app.arejaybee.character_sheet.DialogFragments.EditClassSkillDialogFragment;
import com.app.arejaybee.character_sheet.DialogFragments.EditClassSkillRanksDialogFragment;
import com.app.arejaybee.character_sheet.DialogFragments.EditClassSkillRanksDialogFragment5e;
import com.app.arejaybee.character_sheet.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SkillsActivity extends AbstractCharacterActivity implements EditClassSkillDialogFragment.NoticeDialogListener, EditClassSkillRanksDialogFragment.NoticeDialogListener, EditClassSkillRanksDialogFragment5e.NoticeDialogListener {
    public Context con = this;
    private TextView ranks;
    private GridLayout skillList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildAllLayoutItems();
    }

    protected void buildAllLayoutItems() {
        ranks = findViewById(R.id.sktv_ranks);
        if (rob.getEdition().equals("5e")) {
            ranks.setVisibility(View.GONE);
        }
        skillList = findViewById(R.id.skgl_skillListGrid);
        populateSkillList();
    }

    public void onClickNew(View v) {
        Skill newSkill = new Skill(rob);
        EditClassSkillDialogFragment ecsDialog = new EditClassSkillDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("skill", newSkill);
        b.putInt("index", rob.getSkills().size());
        b.putBoolean("isEdit", false);
        ecsDialog.setArguments(b);
        ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        rob.saveCharacter(con);
    }

    private void populateSkillList() {
        while (skillList.getChildCount() > 8) {
            skillList.removeViewAt(8);
        }
        int rankCount = 0;
        int row = 1;
        final ArrayList<Skill> skills = rob.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            rankCount += skills.get(i).getRank();
            row++;
            CheckBox cb = new CheckBox(this);
            final int index = i;
            cb.setChecked(skills.get(index).isClassSkill());
            final int currentRow = row;
            cb.setOnClickListener(v -> {
                skills.get(index).setIsClassSkill(!skills.get(index).isClassSkill());
                Button b = (Button) skillList.getChildAt(currentRow * 4 + 3);
                b.setText("" + skills.get(index).getMod());
                rob.saveCharacter(con);
            });

            cb.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.CENTER, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 0.5f)));

            TextView skillName = new TextView(this);
            skillName.setText(skills.get(index).getName());
            skillName.setTextColor(getResources().getColor(R.color.black));
            skillName.setEms(10);
            skillName.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.LEFT, 100f)));
            skillName.setOnClickListener(v -> {
                Skill selectedSkill = skills.get(index);
                EditClassSkillDialogFragment ecsDialog = new EditClassSkillDialogFragment();
                Bundle b = new Bundle();
                b.putSerializable("skill", selectedSkill);
                b.putInt("index", index);
                b.putBoolean("isEdit", true);
                b.putInt("ability", Arrays.asList(getResources().getStringArray(R.array.abilities)).indexOf(selectedSkill.getAbility()));
                ecsDialog.setArguments(b);
                ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            });

            TextView ability = new TextView(this);
            ability.setText(skills.get(index).getAbility());
            ability.setTextColor(getResources().getColor(R.color.black));
            GridLayout.LayoutParams glp = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.CENTER, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 2f));
            glp.setGravity(Gravity.CENTER);
            ability.setLayoutParams(glp);
            ability.setOnClickListener(v -> {
                Skill selectedSkill = skills.get(index);
                EditClassSkillDialogFragment ecsDialog = new EditClassSkillDialogFragment();
                Bundle b = new Bundle();
                b.putSerializable("skill", selectedSkill);
                b.putInt("index", index);
                b.putBoolean("isEdit", true);
                b.putInt("ability", Arrays.asList(getResources().getStringArray(R.array.abilities)).indexOf(selectedSkill.getAbility()));
                ecsDialog.setArguments(b);
                ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            });

            Button skillMod = new Button(this);
            skillMod.setText(Integer.toString(skills.get(index).getMod()));
            skillMod.setOnClickListener(v -> {
                Skill selectedSkill = skills.get(index);
                Bundle b = new Bundle();
                b.putSerializable("skill", selectedSkill);
                b.putInt("index", index);
                b.putInt("ability", selectedSkill.getAbilityScore());
                b.putInt("proficiency", rob.getProficiency());
                b.putInt("prof", selectedSkill.getProficiency() == null ? 0 : selectedSkill.getProficiency().ordinal());
                if (rob.getEdition().equals("5e")) {
                    EditClassSkillRanksDialogFragment5e ecsDialog = new EditClassSkillRanksDialogFragment5e();
                    ecsDialog.setArguments(b);
                    ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                } else {
                    EditClassSkillRanksDialogFragment ecsDialog = new EditClassSkillRanksDialogFragment();
                    ecsDialog.setArguments(b);
                    ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                }
            });

            glp = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.CENTER, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 2f));
            glp.setGravity(Gravity.CENTER);
            skillMod.setMinHeight(0);
            skillMod.setMinWidth(0);
            skillMod.setLayoutParams(glp);

            skillList.addView(cb);
            skillList.addView(skillName);
            skillList.addView(ability);
            skillList.addView(skillMod);
        }
        ranks.setText("Ranks: " + String.format("%03d", rankCount));
    }

    public void onDialogPositiveClick(EditClassSkillDialogFragment df) {
        if (df.isEdit()) {
            Skill skill = df.getSkill();
            int skillIndex = df.getSkillIndex();
            rob.updateSkill(skillIndex, skill);
        } else {
            rob.addSkill(df.getSkill());
        }
        rob.saveCharacter(this);
        populateSkillList();
        df.getDialog().cancel();
    }

    public void onDialogDeleteClick(EditClassSkillDialogFragment df) {
        int skillIndex = df.getSkillIndex();
        rob.removeSkill(skillIndex);
        rob.saveCharacter(this);
        populateSkillList();
        df.getDialog().cancel();

    }

    public void onDialogNegativeClick(EditClassSkillDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(EditClassSkillRanksDialogFragment df) {
        Skill skill = df.getSkill();
        int skillIndex = df.getSkillIndex();
        rob.updateSkill(skillIndex, skill);
        rob.saveCharacter(this);
        populateSkillList();
        df.getDialog().cancel();
    }

    public void onDialogNegativeClick(EditClassSkillRanksDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(EditClassSkillRanksDialogFragment5e df) {
        Skill skill = df.getSkill();
        int skillIndex = df.getSkillIndex();
        rob.updateSkill(skillIndex, skill);
        rob.saveCharacter(this);
        populateSkillList();
        df.getDialog().cancel();
    }

    public void onDialogNegativeClick(EditClassSkillRanksDialogFragment5e df) {
        df.getDialog().cancel();
    }
}
