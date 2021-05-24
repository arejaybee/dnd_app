package com.app.arejaybee.character_sheet.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.arejaybee.character_sheet.DataObjects.Companion;
import com.app.arejaybee.character_sheet.DataObjects.PlayerCharacter;
import com.app.arejaybee.character_sheet.R;

import java.util.ArrayList;

public class CompanionActivity extends AbstractCharacterActivity {

    ArrayList<String> characterIdList;
    int selectedCharacterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //build a list of characters from the existing files
        buildCharacterList();
    }

    public void onClickNew(View v) {
        Companion bob = new Companion(rob, rob.getCompanions().size(), rob.getEdition(), true);
        rob.addCompanion(bob);
        bob.setIsCompanion(true);
        rob.saveCharacter(this);
        navigateToCharacterSheet(bob);
    }

    public void onClickLoad(View v) {
        if (characterIdList.isEmpty()) {
            makeShortToast("There are no characters to edit.");
            return;
        }
        Companion bob = rob.getCompanionAt(selectedCharacterIndex);
        navigateToCharacterSheet(bob);
    }

    public void onClickDelete(View v) {
        if (characterIdList.isEmpty()) {
            makeShortToast("There are no characters to delete.");
            return;
        }
        rob.removeCompanion(rob.getCompanionAt(selectedCharacterIndex));
        rob.saveCharacter(this);
        buildCharacterList();
    }


    /**
     * Builds the list of characters that is displayed below the buttons.
     * Called when the app is opened and after a character is deleted.
     */
    private void buildCharacterList() {
        LinearLayout characterListLayout = (LinearLayout) findViewById(R.id.acll_characterListLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 30);
        characterListLayout.removeAllViews();
        if (characterIdList == null) {
            characterIdList = new ArrayList<>();
        }
        characterIdList.clear();
        selectedCharacterIndex = 0;
        ArrayList<Companion> companions = rob.getCompanions();
        for (int i = 0; i < companions.size(); i++) {
            try {
                Companion bob = companions.get(i);
                String robText = bob.getName().equals("") ? "Companion" + i : bob.getName() + "\n" + bob.getRace() + " " + bob.getCharacterClass();
                TextView tvChar = new TextView(this);
                tvChar.setText(robText);
                tvChar.setTextSize(32);
                tvChar.setLayoutParams(lp);
                tvChar.setBackgroundResource(R.drawable.character_border);
                tvChar.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                tvChar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout characterListLayout = (LinearLayout) findViewById(R.id.acll_characterListLayout);
                        for (int i = 0; i < characterListLayout.getChildCount(); i++) {
                            characterListLayout.getChildAt(i).setBackgroundResource(R.drawable.character_border);
                            if (characterListLayout.getChildAt(i).equals((TextView) v)) {
                                selectedCharacterIndex = i;
                            }
                        }
                        v.setBackgroundResource(R.drawable.selected_character_border);
                    }
                });
                characterListLayout.addView(tvChar);
                characterIdList.add(bob.getCharacterID());
            } catch (Exception e) {
                Toast.makeText(CompanionActivity.this, "There was an issue loading characters.", Toast.LENGTH_LONG);
            }
        }
        if (characterListLayout.getChildCount() > 0) {
            selectedCharacterIndex = 0;
            characterListLayout.getChildAt(0).setBackgroundResource(R.drawable.selected_character_border);
        }
    }

    private void navigateToCharacterSheet(PlayerCharacter pc) {
        Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
        intent.putExtra("character", pc);
        startActivity(intent);
    }

}
