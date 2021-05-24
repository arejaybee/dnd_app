package com.app.arejaybee.character_sheet.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.ArmorClass;
import com.app.arejaybee.character_sheet.DataObjects.SavingThrow;
import com.app.arejaybee.character_sheet.DataObjects.Weapon;
import com.app.arejaybee.character_sheet.DialogFragments.ArmorDialogFragment35;
import com.app.arejaybee.character_sheet.DialogFragments.CombatSaveDialogFragment35;
import com.app.arejaybee.character_sheet.DialogFragments.GrappleDialogFragment;
import com.app.arejaybee.character_sheet.DialogFragments.InitiativeDialogFragment;
import com.app.arejaybee.character_sheet.R;

import java.util.ArrayList;

public class CombatActivity35 extends AbstractCharacterActivity implements InitiativeDialogFragment.NoticeDialogListener, ArmorDialogFragment35.NoticeDialogListener, GrappleDialogFragment.NoticeDialogListener, CombatSaveDialogFragment35.NoticeDialogListener {

    public RelativeLayout selectedWeaponRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildLayouts();
    }

    private void buildLayouts() {
        buildSaveLayout();
        buildStatLayout();
        buildAttackLayout();
    }

    private void buildSaveLayout() {
        TextView tvDex = findViewById(R.id.c35tv_dexsave);
        TextView tvCon = findViewById(R.id.c35tv_consave);
        TextView tvWis = findViewById(R.id.c35tv_wissave);

        tvDex.setText("" + (rob.getSave("DEX").getTotal()));
        tvCon.setText("" + (rob.getSave("CON").getTotal()));
        tvWis.setText("" + (rob.getSave("WIS").getTotal()));


        EditText etConditional = findViewById(R.id.c35et_conditionalSaves);
        etConditional.setText("" + rob.getConditionalSavingThrowMods());
        addSaveClickeListener(tvDex, "DEX");
        addSaveClickeListener(tvCon, "CON");
        addSaveClickeListener(tvWis, "WIS");

        etConditional.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePlayerFields();
            }
        });

    }

    private void addSaveClickeListener(View v, String saveName) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CombatSaveDialogFragment35 ecsDialog = new CombatSaveDialogFragment35();
                Bundle b = new Bundle();
                b.putSerializable("mod", saveName);
                b.putSerializable("proficiency", rob.getProficiency());
                b.putSerializable("modVal", rob.getModifier(saveName));
                b.putSerializable("class", rob.getSave(saveName).getClassBonus());
                b.putSerializable("bonus", rob.getSave(saveName).getBonus());
                ecsDialog.setArguments(b);
                ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            }
        });
    }

    private void buildStatLayout() {
        TextView tvInitiative = findViewById(R.id.c35tv_initiative);
        EditText eSpeed = findViewById(R.id.c35tv_speed);
        TextView tvAC = findViewById(R.id.c35tv_ac);
        TextView tvtAC = findViewById(R.id.c35tv_tac);
        TextView tvfAC = findViewById(R.id.c35tv_ffac);
        EditText etCurHp = findViewById(R.id.c35et_curHP);
        EditText etMaxHp = findViewById(R.id.c35et_maxHP);
        TextView eGrapple = findViewById(R.id.c35tv_grapple);
        EditText eAttack = findViewById(R.id.c35et_bab);

        eSpeed.setText("" + rob.getSpeed());
        tvInitiative.setText("" + (rob.getDexMod() + rob.getInitiativeBonus()));
        tvAC.setText("" + (10 + rob.getArmorClass().getMod() + rob.getDexMod()));
        tvtAC.setText("" + (10 + rob.getDexMod()));
        tvfAC.setText("" + (10 + rob.getArmorClass().getMod()));
        etCurHp.setText("" + rob.getCurrentHp());
        etMaxHp.setText("" + rob.getMaxHp());
        eAttack.setText("" + rob.getAttackBonus());
        eGrapple.setText("" + rob.getGrapple());

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePlayerFields();
            }
        };
        eSpeed.addTextChangedListener(tw);
        etCurHp.addTextChangedListener(tw);
        etMaxHp.addTextChangedListener(tw);
        eAttack.addTextChangedListener(tw);

    }

    private void buildAttackLayout() {
        selectedWeaponRow = null;
        ArrayList<Weapon> weapons = rob.getWeapons();
        LinearLayout ll = findViewById(R.id.c35ll_weaponLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 30);
        ll.removeAllViews();
        for (int i = 0; i < weapons.size(); i++) {
            RelativeLayout weaponRow = new RelativeLayout(this);
            weaponRow.setLayoutParams(lp);
            weaponRow.setBackgroundResource(R.drawable.character_border);

            EditText etChar = new EditText(this);
            etChar.setText(weapons.get(i).getName());
            etChar.setTextSize(24);
            RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            rllp.setMargins(convertSpToPixels(50), 10, convertSpToPixels(50), 10);
            etChar.setLayoutParams(new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            etChar.setId(View.generateViewId());
            etChar.setLayoutParams(rllp);
            etChar.setGravity(Gravity.CENTER_HORIZONTAL);
            int index = i;
            etChar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Weapon w = rob.getWeaponAt(index);
                    w.setName(getTextFromView(etChar));
                    rob.setWeaponAt(index, w);
                }
            });
            weaponRow.addView(etChar);

            ImageButton btnAbilityDropdown = new ImageButton(this);
            btnAbilityDropdown.setImageResource(android.R.drawable.ic_menu_more);
            rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rllp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rllp.addRule(RelativeLayout.ALIGN_TOP, etChar.getId());
            rllp.addRule(RelativeLayout.ALIGN_BOTTOM, etChar.getId());
            btnAbilityDropdown.setLayoutParams(rllp);
            btnAbilityDropdown.setBackgroundColor(Color.TRANSPARENT);
            btnAbilityDropdown.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    toggleViewVisibility(v, (LinearLayout) ((RelativeLayout) v.getParent()).getChildAt(2));
                    LinearLayout innerLayout = (LinearLayout) ((RelativeLayout) v.getParent()).getChildAt(2);
                    RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    rllp.setMargins(30, 15, 30, 20);
                    rllp.addRule(RelativeLayout.BELOW, etChar.getId());
                    innerLayout.setLayoutParams(rllp);
                }
            });
            weaponRow.addView(btnAbilityDropdown);

            LinearLayout llInner = buildNewWeaponInnerLayout(rob.getWeaponAt(i), i);
            weaponRow.addView(llInner);
            btnAbilityDropdown.setRotation(180);


            weaponRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedWeaponRow != null) {
                        selectedWeaponRow.setBackgroundResource(R.drawable.character_border);
                    }
                    v.setBackgroundResource(R.drawable.selected_character_border);
                    selectedWeaponRow = (RelativeLayout) v;
                }
            });

            toggleViewVisibility(btnAbilityDropdown, ((RelativeLayout) btnAbilityDropdown.getParent()).getChildAt(2));
            ll.addView(weaponRow, 0);
        }
    }

    //buttons
    public void onClickToggleSaves(View v) {
        toggleViewVisibility(v, findViewById(R.id.c35ll_saveLayout));
    }

    public void onClickNew(View v) {
        Weapon w = new Weapon();
        rob.addWeapon(w);
        buildAttackLayout();
    }

    public void onClickDelete(View v) {
        if (selectedWeaponRow == null) {
            makeLongToast("No weapon selected!");
            return;
        }
        rob.removeWeaponAt(layoutPlacementToIndex(((LinearLayout) selectedWeaponRow.getParent()).indexOfChild(selectedWeaponRow), ((View) selectedWeaponRow.getParent())));
        rob.saveCharacter(this);
        buildAttackLayout();
    }

    public void onClickInitiative(View v) {
        InitiativeDialogFragment ecsDialog = new InitiativeDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("dex", rob.getDexMod());
        b.putSerializable("bonus", rob.getInitiativeBonus());
        ecsDialog.setArguments(b);
        ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    public void onClickGrapple(View v) {
        GrappleDialogFragment ecsDialog = new GrappleDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("str", rob.getStrMod());
        b.putSerializable("bab", rob.getAttackBonus());
        b.putSerializable("bonus", rob.getGrappleBonus());
        ecsDialog.setArguments(b);
        ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");

    }

    public void onClickArmor(View v) {
        ArmorDialogFragment35 ecsDialog = new ArmorDialogFragment35();
        Bundle b = new Bundle();
        ArmorClass a = rob.getArmorClass();
        b.putSerializable("dex", rob.getDexMod());
        b.putSerializable("armor", a.getArmor());
        b.putSerializable("shield", a.getShield());
        b.putSerializable("bonus", a.getMisc());
        ecsDialog.setArguments(b);
        ecsDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    //utility
    @Override
    protected void updatePlayerFields() {
        rob.setConditionalSavingThrowMods(getTextFromView(R.id.c35et_conditionalSaves));
        rob.setMaxHp(getIntFromView(R.id.c35et_maxHP));
        rob.setCurrentHp(getIntFromView(R.id.c35et_curHP));
        rob.setSpeed(getTextFromView(R.id.c35tv_speed));
        rob.setAttackBonus(getIntFromView(R.id.c35et_bab));
        rob.saveCharacter(this);

        TextView tvDex = findViewById(R.id.c35tv_dexsave);
        TextView tvCon = findViewById(R.id.c35tv_consave);
        TextView tvWis = findViewById(R.id.c35tv_wissave);
        TextView tvGrapple = findViewById(R.id.c35tv_grapple);

        tvDex.setText("" + (rob.getSave("DEX").getTotal() + rob.getProficiency(rob.getSave("DEX").getProficiency().ordinal())));
        tvCon.setText("" + (rob.getSave("CON").getTotal() + rob.getProficiency(rob.getSave("CON").getProficiency().ordinal())));
        tvWis.setText("" + (rob.getSave("WIS").getTotal() + rob.getProficiency(rob.getSave("WIS").getProficiency().ordinal())));
        tvGrapple.setText("" + rob.getGrapple());
    }

    private LinearLayout buildNewWeaponInnerLayout(Weapon w, int index) {
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);


        ll.setLayoutParams(lp);
        //Row of labels for fields
        LinearLayout labelRow = new LinearLayout(this);
        labelRow.setLayoutParams(lp);
        labelRow.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams innerLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        LinearLayout.LayoutParams innerLP2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        innerLP2.setMargins(convertSpToPixels(15), 0, 0, 0);
        TextView tvDmg = new TextView(this);
        tvDmg.setText("Damage");
        tvDmg.setLayoutParams(innerLP);
        tvDmg.setWidth(convertSpToPixels(75));
        labelRow.addView(tvDmg);
        TextView tvRng = new TextView(this);
        tvRng.setText("Range");
        tvRng.setWidth(convertSpToPixels(75));
        tvRng.setLayoutParams(innerLP2);
        labelRow.addView(tvRng);
        TextView tvHit = new TextView(this);
        tvHit.setText("Hit");
        tvHit.setWidth(convertSpToPixels(75));
        tvHit.setLayoutParams(innerLP2);
        labelRow.addView(tvHit);

        //Row of edit fields
        LinearLayout editRow = new LinearLayout(this);
        editRow.setLayoutParams(lp);
        editRow.setOrientation(LinearLayout.HORIZONTAL);
        EditText etDmg = new EditText(this);
        etDmg.setText(w.getDamage());
        etDmg.setBackgroundResource(R.drawable.edit_text_border);
        etDmg.setPadding(5, 0, 5, 0);
        etDmg.setGravity(Gravity.CENTER);
        etDmg.setLayoutParams(innerLP);
        etDmg.setWidth(convertSpToPixels(75));
        EditText etRng = new EditText(this);
        etRng.setText(w.getRange());
        etRng.setBackgroundResource(R.drawable.edit_text_border);
        etRng.setGravity(Gravity.CENTER);
        etRng.setPadding(5, 0, 5, 0);
        etRng.setWidth(convertSpToPixels(75));
        etRng.setLayoutParams(innerLP2);
        EditText etHit = new EditText(this);
        etHit.setText(w.getToHit());
        etHit.setBackgroundResource(R.drawable.edit_text_border);
        etHit.setGravity(Gravity.CENTER);
        etHit.setPadding(5, 0, 5, 0);
        etHit.setWidth(convertSpToPixels(75));
        etHit.setLayoutParams(innerLP2);


        //note tv
        TextView tvNote = new TextView(this);
        tvNote.setText("Notes");
        tvNote.setLayoutParams(lp);

        //note et
        EditText etNote = new EditText(this);
        etNote.setBackgroundResource(R.drawable.edit_text_border);
        etNote.setMinHeight(convertSpToPixels(20));
        etNote.setWidth(0);
        etNote.setText(w.getNotes());
        etNote.setPadding(convertSpToPixels(5), 0, convertSpToPixels(5), 0);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                w.setDamage(getTextFromView(etDmg));
                w.setRange(getTextFromView(etRng));
                w.setToHit(getTextFromView(etHit));
                w.setNotes(getTextFromView(etNote));
                rob.setWeaponAt(index, w);
                rob.saveCharacter(context);
            }
        };
        etDmg.addTextChangedListener(tw);
        etRng.addTextChangedListener(tw);
        etHit.addTextChangedListener(tw);
        etNote.addTextChangedListener(tw);
        editRow.addView(etDmg);
        editRow.addView(etRng);
        editRow.addView(etHit);
        ll.addView(labelRow);
        ll.addView(editRow);
        ll.addView(tvNote);
        ll.addView(etNote);
        return ll;
    }


    //DialogFragments
    public void onDialogPositiveClick(InitiativeDialogFragment df) {
        rob.setInitiativeBonus(df.getBonus());
        rob.saveCharacter(this);
        df.getDialog().cancel();
        buildStatLayout();
    }

    public void onDialogNegativeClick(InitiativeDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(GrappleDialogFragment df) {
        rob.setGrappleBonus(df.getBonus());
        rob.saveCharacter(this);
        df.getDialog().cancel();
        buildStatLayout();
    }

    public void onDialogNegativeClick(GrappleDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(ArmorDialogFragment35 df) {
        ArmorClass a = df.getArmor();
        rob.setArmorClass(a);
        rob.saveCharacter(this);
        df.getDialog().cancel();
        buildStatLayout();
    }

    public void onDialogNegativeClick(ArmorDialogFragment35 df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(CombatSaveDialogFragment35 df) {
        SavingThrow st = df.getSave();
        rob.updateSave(st);
        rob.saveCharacter(this);
        df.getDialog().cancel();
        buildSaveLayout();
    }

    public void onDialogNegativeClick(CombatSaveDialogFragment35 df) {
        df.getDialog().cancel();
    }
}
