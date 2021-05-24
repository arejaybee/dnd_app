/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.app.arejaybee.character_sheet.DataObjects.PlayerCharacter;
import com.app.arejaybee.character_sheet.DialogFragments.NewCharacterDialogFragment;
import com.app.arejaybee.character_sheet.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MainActivity extends AbstractActivity implements NewCharacterDialogFragment.NoticeDialogListener {

    ArrayList<String> characterIdList;
    int selectedCharacterIndex = 0;
    PlayerCharacter rob;

    /**
     * Called when the app is opened, or when this screen is created.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasPermissions = checkPermissions();
        if (!hasPermissions) {
            makeLongToast("You must grant the app proper permissions!");
        } else {
            selectedCharacterIndex = 0;
            setContentView(R.layout.activity_main);
            // Permission has already been granted
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setActionBar(toolbar);

            if (!(new File(getExternalFilesDir(null) + PlayerCharacter.CHARACTER_PATH).exists())) {
                new File(getExternalFilesDir(null) + PlayerCharacter.CHARACTER_PATH).mkdirs();
            }


            //build a list of characters from the existing files
            buildCharacterList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPermissions()) {
            makeLongToast("You must grant the app proper permissions!");
        } else {
            selectedCharacterIndex = 0;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setActionBar(toolbar);
            refreshCharacterList();
        }
    }

    public void onClickNew(View v) {
        NewCharacterDialogFragment nDialog = new NewCharacterDialogFragment();
        Bundle b = new Bundle();
        nDialog.setArguments(b);
        nDialog.show(getSupportFragmentManager(), "NewCharacterDialogFragment");
    }

    public void onClickLoad(View v) {
        if (characterIdList.isEmpty() || selectedCharacterIndex > characterIdList.size() || selectedCharacterIndex < 0) {
            makeShortToast("There are no characters to edit.");
            return;
        }
        rob = PlayerCharacter.loadCharacter(characterIdList.get(selectedCharacterIndex), this);
        navigateToCharacterSheet(rob);
    }


    public void onClickEmail(View v) {
        if (characterIdList.isEmpty() || selectedCharacterIndex > characterIdList.size() || selectedCharacterIndex < 0) {
            makeShortToast("There are no characters to email.");
            return;
        }
        rob = PlayerCharacter.loadCharacter(characterIdList.get(selectedCharacterIndex), this);
        ShareViaEmail(PlayerCharacter.CHARACTER_PATH, rob.getCharacterID() + ".char");
    }

    public void onClickDelete(View v) {
        if (characterIdList.isEmpty() || selectedCharacterIndex > characterIdList.size() || selectedCharacterIndex < 0) {
            makeShortToast("There are no characters to delete.");
            return;
        }
        rob = PlayerCharacter.loadCharacter(characterIdList.get(selectedCharacterIndex), this);
        String name = rob.getName();
        File f = new File(rob.getFilePath(this));
        f.delete();
        refreshCharacterList();
        makeShortToast(name + " has been deleted.");
    }

    public void onDialogNegativeClick(NewCharacterDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(NewCharacterDialogFragment df) {
        String edition = df.getEdition();
        boolean genSkills = df.areSkillsGenerated();
        df.getDialog().cancel();
        PlayerCharacter rob = new PlayerCharacter(edition, genSkills);
        navigateToCharacterSheet(rob);
    }

    /**
     * Builds the list of characters that is displayed below the buttons.
     * Called when the app is opened and after a character is deleted.
     */
    private void buildCharacterList() {
        LinearLayout characterListLayout = (LinearLayout) findViewById(R.id.llms_characterListLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 30);
        File characterFolder = new File(getExternalFilesDir(null) + PlayerCharacter.CHARACTER_PATH);
        moveCharFiles(characterFolder);

        File[] listOfFiles = characterFolder.listFiles();
        if (listOfFiles == null) {
            return;
        }
        if (characterIdList == null) {
            characterIdList = new ArrayList<>();
        }
        characterIdList.clear();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    PlayerCharacter rob = PlayerCharacter.loadCharacter(listOfFiles[i]);
                    if (rob.isCompanion()) {
                        continue;
                    }
                    String robText = rob.getName().equals("") ? "Character" + i : rob.getName() + "\n" + rob.getRace() + " " + rob.getCharacterClass();
                    TextView tvChar = new TextView(this);
                    tvChar.setText(robText);
                    tvChar.setTextSize(32);
                    tvChar.setLayoutParams(lp);
                    tvChar.setBackgroundResource(R.drawable.character_border);
                    tvChar.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    tvChar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout characterListLayout = (LinearLayout) findViewById(R.id.llms_characterListLayout);
                            for (int i = 0; i < characterListLayout.getChildCount(); i++) {
                                characterListLayout.getChildAt(i).setBackgroundResource(R.drawable.character_border);
                                if (characterListLayout.getChildAt(i).equals((TextView) v)) {
                                    selectedCharacterIndex = i;
                                }
                            }
                            v.setBackgroundResource(R.drawable.selected_character_border);
                        }
                    });
                    if (!rob.getCharacterID().equals(listOfFiles[i].getName())) {
                        rob.setCharacterID(listOfFiles[i].getName().replace(".char", ""));
                        rob.saveCharacter(this);
                    }
                    characterListLayout.addView(tvChar);
                    characterIdList.add(rob.getCharacterID());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "There was an issue loading characters.", Toast.LENGTH_LONG);
                    listOfFiles[i].delete();
                }
            }
        }
        if (characterListLayout.getChildCount() > 0) {
            selectedCharacterIndex = 0;
            characterListLayout.getChildAt(0).setBackgroundResource(R.drawable.selected_character_border);
        }
    }

    private void moveCharFiles(File existingCharactersFolder) {
        FilenameFilter charFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.endsWith("char"));
            }
        };

        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] listOfChars = downloadFolder.listFiles(charFilter);
        for (int i = 0; i < listOfChars.length; i++) {
            try {
                PlayerCharacter pc = PlayerCharacter.loadCharacter(listOfChars[i]);
                while (pc.generateCharacterID(this)) ;
                String filename = pc.getCharacterID() + ".char";
                copyFile(listOfChars[i], new File(getExternalFilesDir(null) + PlayerCharacter.CHARACTER_PATH + "/" + filename), this);
                listOfChars[0].getCanonicalFile().delete();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private void navigateToCharacterSheet(PlayerCharacter pc) {
        Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
        intent.putExtra("character", pc);
        startActivity(intent);
    }

    private void refreshCharacterList() {
        LinearLayout characterListLayout =  findViewById(R.id.llms_characterListLayout);
        if(characterListLayout != null) {
            characterListLayout.removeAllViews();
            buildCharacterList();
        }
    }

    /**
     * Opens the user's preferred email program
     *
     * @param folder_name - the folder the character is held in
     * @param file_name   - the character's file name
     */
    private void ShareViaEmail(String folder_name, String file_name) {
        try {
            File Root = getExternalFilesDir(null);
            String characterLocation = Root.getAbsolutePath() + folder_name + "/" + file_name;
            String emailFileName = Root.getAbsolutePath() + folder_name + "/" + rob.getEmailableFileName() + ".char";
            File emailFile = new File(emailFileName);
            emailFile.delete();
            copyFile(new File(characterLocation), emailFile, this);
            Uri emailURI = Uri.fromFile(emailFile);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String message = "Sending my character.";
            PlayerCharacter pc = PlayerCharacter.loadCharacter(emailFile);

            intent.putExtra(Intent.EXTRA_SUBJECT, "Character File " + pc.getName());
            intent.putExtra(Intent.EXTRA_STREAM, emailURI);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            Thread.sleep(5000); //takes a second to swap to the email intent. We delete the copy afterwards.
            emailFile.delete();
        } catch (Exception e) {
            makeLongToast("Unable to send email.");
        }
    }

    private boolean checkPermissions() {

        boolean hasPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        hasPermissions = hasPermissions && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return hasPermissions;
    }
}
