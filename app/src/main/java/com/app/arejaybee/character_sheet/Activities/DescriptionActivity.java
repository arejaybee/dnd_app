package com.app.arejaybee.character_sheet.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.R;

public class DescriptionActivity extends AbstractCharacterActivity {

    //ability score views
    private TextView strMod;
    private TextView dexMod;
    private TextView conMod;
    private TextView intMod;
    private TextView wisMod;
    private TextView charMod;
    private EditText strScore;
    private EditText dexScore;
    private EditText conScore;
    private EditText intScore;
    private EditText wisScore;
    private EditText charScore;
    private EditText strBonus;
    private EditText dexBonus;
    private EditText conBonus;
    private EditText intBonus;
    private EditText wisBonus;
    private EditText charBonus;

    //detail views
    private EditText name;
    private EditText race;
    private EditText characterClass;
    private EditText level;
    private EditText alignment;
    private EditText gender;
    private EditText height;
    private EditText weight;
    private EditText size;
    private EditText exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildAllLayoutItems();
        loadCharacter();
        addChangeListeners();
    }

    @SuppressLint("SetTextI18n")
    private void loadCharacter() {
        //load attributes for ability score section
        strScore.setText(Integer.toString(rob.getStrScore()));
        dexScore.setText(Integer.toString(rob.getDexScore()));
        conScore.setText(Integer.toString(rob.getConScore()));
        intScore.setText(Integer.toString(rob.getIntScore()));
        wisScore.setText(Integer.toString(rob.getWisScore()));
        charScore.setText(Integer.toString(rob.getCharScore()));
        strBonus.setText(Integer.toString(rob.getStrScoreBonus()));
        dexBonus.setText(Integer.toString(rob.getDexScoreBonus()));
        conBonus.setText(Integer.toString(rob.getConScoreBonus()));
        intBonus.setText(Integer.toString(rob.getIntScoreBonus()));
        wisBonus.setText(Integer.toString(rob.getWisScoreBonus()));
        charBonus.setText(Integer.toString(rob.getCharScoreBonus()));

        strMod.setText(Integer.toString(rob.getStrMod()));
        dexMod.setText(Integer.toString(rob.getDexMod()));
        conMod.setText(Integer.toString(rob.getConMod()));
        intMod.setText(Integer.toString(rob.getIntMod()));
        wisMod.setText(Integer.toString(rob.getWisMod()));
        charMod.setText(Integer.toString(rob.getCharMod()));

        //load attributes for details section
        name.setText(rob.getName());
        race.setText(rob.getRace());
        characterClass.setText(rob.getCharacterClass());
        level.setText(rob.getLevel());
        alignment.setText(rob.getAlignment());
        gender.setText(rob.getGender());
        height.setText(rob.getHeight());
        weight.setText(rob.getWeight());
        size.setText(rob.getSize());
        exp.setText(rob.getExp());
    }

    protected void buildAllLayoutItems() {

        //Get the ability score items
        strMod = findViewById(R.id.dtv_strMod);
        dexMod = findViewById(R.id.dtv_dexMod);
        conMod = findViewById(R.id.dtv_conMod);
        intMod = findViewById(R.id.dtv_intMod);
        wisMod = findViewById(R.id.dtv_wisMod);
        charMod = findViewById(R.id.dtv_charMod);

        strScore = findViewById(R.id.det_strScore);
        dexScore = findViewById(R.id.det_dexScore);
        conScore = findViewById(R.id.det_conScore);
        intScore = findViewById(R.id.det_intScore);
        wisScore = findViewById(R.id.det_wisScore);
        charScore = findViewById(R.id.det_charScore);

        strBonus = findViewById(R.id.det_strBonus);
        dexBonus = findViewById(R.id.det_dexBonus);
        conBonus = findViewById(R.id.det_conBonus);
        intBonus = findViewById(R.id.det_intBonus);
        wisBonus = findViewById(R.id.det_wisBonus);
        charBonus = findViewById(R.id.det_charBonus);

        //Get the detail view items
        name = findViewById(R.id.det_name);
        race = findViewById(R.id.det_race);
        characterClass = findViewById(R.id.det_class);
        level = findViewById(R.id.det_level);
        alignment = findViewById(R.id.det_alignment);
        gender = findViewById(R.id.det_gender);
        height = findViewById(R.id.det_height);
        weight = findViewById(R.id.det_weight);
        size = findViewById(R.id.det_size);
        exp = findViewById(R.id.det_exp);
    }

    private void addChangeListeners() {
        //Text watcher for the ability score section:
        TextWatcher tw_ability = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                rob.setStrScore(getEditTextInt(strScore));
                rob.setDexScore(getEditTextInt(dexScore));
                rob.setConScore(getEditTextInt(conScore));
                rob.setIntScore(getEditTextInt(intScore));
                rob.setWisScore(getEditTextInt(wisScore));
                rob.setCharScore(getEditTextInt(charScore));

                rob.setStrScoreBonus(getEditTextInt(strBonus));
                rob.setDexScoreBonus(getEditTextInt(dexBonus));
                rob.setConScoreBonus(getEditTextInt(conBonus));
                rob.setIntScoreBonus(getEditTextInt(intBonus));
                rob.setWisScoreBonus(getEditTextInt(wisBonus));
                rob.setCharScoreBonus(getEditTextInt(charBonus));

                rob.saveCharacter(DescriptionActivity.this);

                strMod.setText(Integer.toString(rob.getStrMod()));
                dexMod.setText(Integer.toString(rob.getDexMod()));
                conMod.setText(Integer.toString(rob.getConMod()));
                intMod.setText(Integer.toString(rob.getIntMod()));
                wisMod.setText(Integer.toString(rob.getWisMod()));
                charMod.setText(Integer.toString(rob.getCharMod()));
            }
        };

        strScore.addTextChangedListener(tw_ability);
        dexScore.addTextChangedListener(tw_ability);
        conScore.addTextChangedListener(tw_ability);
        intScore.addTextChangedListener(tw_ability);
        wisScore.addTextChangedListener(tw_ability);
        charScore.addTextChangedListener(tw_ability);
        strBonus.addTextChangedListener(tw_ability);
        dexBonus.addTextChangedListener(tw_ability);
        conBonus.addTextChangedListener(tw_ability);
        intBonus.addTextChangedListener(tw_ability);
        wisBonus.addTextChangedListener(tw_ability);
        charBonus.addTextChangedListener(tw_ability);
    }


    @Override
    protected void updatePlayerFields() {
        rob.setName(name.getText().toString());
        rob.setRace(race.getText().toString());
        rob.setCharacterClass(characterClass.getText().toString());
        rob.setLevel(level.getText().toString());
        rob.setAlignment(alignment.getText().toString());
        rob.setGender(gender.getText().toString());
        rob.setHeight(height.getText().toString());
        rob.setWeight(weight.getText().toString());
        rob.setSize(size.getText().toString());
        rob.setExp(exp.getText().toString());
        rob.saveCharacter(DescriptionActivity.this);
    }
}
