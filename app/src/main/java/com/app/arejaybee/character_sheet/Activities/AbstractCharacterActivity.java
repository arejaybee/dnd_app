package com.app.arejaybee.character_sheet.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Companion;
import com.app.arejaybee.character_sheet.DataObjects.PlayerCharacter;
import com.app.arejaybee.character_sheet.R;

public class AbstractCharacterActivity extends AbstractActivity {

    protected PlayerCharacter rob;
    protected Context context;

    private static int convertSpToPixels(float sp, Context context) {
        return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int selectedButtonInt = 0;
        rob = (PlayerCharacter) getIntent().getSerializableExtra("character");
        switch (getActivityName()) {
            case ("DESCRIPTION"):
                setContentView(R.layout.activity_description);
                selectedButtonInt = R.id.navbtn_description;
                break;
            case ("SKILLS"):
                setContentView(R.layout.activity_skills);
                selectedButtonInt = R.id.navbtn_skills;
                break;
            case ("COMBAT"):
                switch (rob.getEdition()) {
                    case ("3.5"):
                        setContentView(R.layout.activity_combat35);
                        break;
                    case ("5e"):
                        setContentView(R.layout.activity_combat5e);
                        break;
                    default:
                        makeLongToast("What the hell? " + rob.getEdition());
                        break;
                }
                selectedButtonInt = R.id.navbtn_combat;
                break;
            case ("SPELL"):
                setContentView(R.layout.activity_spell);
                selectedButtonInt = R.id.navbtn_spells;
                break;
            case ("INVENTORY"):
                setContentView(R.layout.activity_inventory);
                selectedButtonInt = R.id.navbtn_inventory;
                break;
            case ("COMPANION"):
                setContentView(R.layout.activity_companion);
                selectedButtonInt = R.id.navbtn_companion;
                break;
            case ("ABILITY"):
                setContentView(R.layout.activity_ability);
                selectedButtonInt = R.id.navbtn_abilities;
                break;
            case ("NOTES"):
                setContentView(R.layout.activity_notes);
                selectedButtonInt = R.id.navbtn_notes;
                break;
        }

        Button selectedButton = findViewById(selectedButtonInt);
        ((TextView) findViewById(R.id.TOOLBAR_TITLE)).setText(rob.getName().equals("") ? "New Character" : rob.getName());
        selectedButton.setBackground(getResources().getDrawable(R.drawable.navigation_button_selected));
        selectedButton.setTextColor(getResources().getColor(R.color.white));
    }

    protected void afterCreate() {
        Button selectedButton = null;
        switch (getActivityName()) {
            case ("DESCRIPTION"):
                selectedButton = findViewById(R.id.navbtn_description);
                break;
            case ("SKILLS"):
                selectedButton = findViewById(R.id.navbtn_skills);
                break;
            case ("COMBAT"):
                selectedButton = findViewById(R.id.navbtn_combat);
                break;
            case ("SPELL"):
                selectedButton = findViewById(R.id.navbtn_spells);
                break;
            case ("INVENTORY"):
                selectedButton = findViewById(R.id.navbtn_inventory);
                break;
            case ("COMPANION"):
                selectedButton = findViewById(R.id.navbtn_companion);
                break;
            case ("ABILITY"):
                selectedButton = findViewById(R.id.navbtn_abilities);
                break;
            case ("NOTES"):
                selectedButton = findViewById(R.id.navbtn_notes);
                break;
        }

    }

    protected void updatePlayerFields() {
        return;
    }

    public void onClickNavigation(View v) {
        updatePlayerFields();
        Intent intent = null;
        context = this;
        switch (((Button) v).getText().toString()) {
            case ("DESCRIPTION"):
                if (this instanceof DescriptionActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                break;
            case ("SKILLS"):
                if (this instanceof SkillsActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), SkillsActivity.class);
                break;
            case ("COMBAT"):
                switch (rob.getEdition()) {
                    case ("5e"):
                        if (this instanceof CombatActivity5e) {
                            return;
                        }
                        intent = new Intent(getApplicationContext(), CombatActivity5e.class);
                        break;
                    case ("3.5"):
                        if (this instanceof CombatActivity35) {
                            return;
                        }
                        intent = new Intent(getApplicationContext(), CombatActivity35.class);
                }
                break;
            case ("SPELLS"):
                if (this instanceof SpellActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), SpellActivity.class);
                break;
            case ("INVENTORY"):
                if (this instanceof InventoryActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), InventoryActivity.class);
                break;
            case ("COMPANION"):
                if (this instanceof CompanionActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), CompanionActivity.class);
                break;
            case ("ABILITIES"):
                if (this instanceof AbilityActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), AbilityActivity.class);
                break;
            case ("NOTES"):
                if (this instanceof NotesActivity) {
                    return;
                }
                intent = new Intent(getApplicationContext(), NotesActivity.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("character", rob);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
        return;
    }

    public void onClickHome(View v) {
        updatePlayerFields();
        Intent intent;
        if (rob instanceof Companion) {
            intent = new Intent(getApplicationContext(), CompanionActivity.class);
            intent.putExtra("character", ((Companion) rob).getOwner());
        } else {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public void onClickEmail(View v) {
        try {
            if (rob.getCharacterID().equals("")) {
                makeShortToast("An unedited new character cannot be emailed.");
                return;
            }
            String fileLocation = rob.getFilePath(this);
            rob.setCharacterID("");
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String message = "Sending my character.";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Character File - " + rob.getName());
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + fileLocation));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } catch (Exception e) {
            makeLongToast("Unable to send email.");
            System.out.println(e);
        }
    }

    @Override
    public void onBackPressed() {
        updatePlayerFields();
        onClickHome(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected int getEditTextInt(EditText et) {
        String text = et.getText().toString();
        if (text.equals("") || text.equals("-")) {
            text = "0";
        }
        return Integer.parseInt(text);
    }

    protected void toggleViewVisibility(View buttonPressed, View viewToToggle) {
        buttonPressed.setRotation(buttonPressed.getRotation() + 180);
        if (viewToToggle.getVisibility() == View.VISIBLE) {
            viewToToggle.setVisibility(View.GONE);
        } else {
            viewToToggle.setVisibility(View.VISIBLE);
        }
    }

    protected int convertSpToPixels(float sp) {
        return convertSpToPixels(sp, this);
    }

    protected String getTextFromView(View v) {
        if (v instanceof TextView) {
            return ((TextView) v).getText().toString();
        } else if (v instanceof EditText) {
            return ((EditText) v).getText().toString();
        }
        return "";
    }

    protected int getIntFromView(View v) {
        String t = getTextFromView(v);
        try {
            return Integer.parseInt(t);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    protected int getIntFromView(int r) {
        return getIntFromView(findViewById(r));
    }

    protected String getTextFromView(int r) {
        return getTextFromView(findViewById(r));
    }

    protected int layoutPlacementToIndex(int i, View ll) {
        return ((LinearLayout) ll).getChildCount() - 1 - i;
    }

    private String getActivityName() {
        return this.getClass().getName().toUpperCase().replace("COM.APP.AREJAYBEE.CHARACTER_SHEET.ACTIVITIES.", "").replace("ACTIVITY", "").replace("35", "").replace("5E", "");
    }

    //utility
    public View findViewByIdString(String s) {
        try {
            return findViewById(R.id.class.getDeclaredField(s).getInt(R.id.class.getDeclaredField(s)));
        } catch (Exception e) {
            return null;
        }
    }
}

