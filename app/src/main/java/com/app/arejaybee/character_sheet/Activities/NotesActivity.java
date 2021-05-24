package com.app.arejaybee.character_sheet.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Note;
import com.app.arejaybee.character_sheet.DialogFragments.NoteDialogFragment;
import com.app.arejaybee.character_sheet.R;

import java.util.ArrayList;

public class NotesActivity extends AbstractCharacterActivity implements NoteDialogFragment.NoticeDialogListener {

    //these are on note
    private int selectedNoteIndex = -1;
    private Note note;
    private boolean editMode = false;
    //these are on edit note
    private EditText title;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = null;
        buildLinearLayout();
    }

    public void onClickNew(View v) {

        NoteDialogFragment nDialog = new NoteDialogFragment();
        Bundle b = new Bundle();
        nDialog.setArguments(b);
        nDialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        selectedNoteIndex = rob.getNotes().size();
    }

    public void onClickEdit(View v) {
        if (selectedNoteIndex >= 0 && rob.getNotes().size() > 0) {
            note = rob.getNoteAt(selectedNoteIndex);
            setContentView(R.layout.activity_edit_note);
            title = findViewById(R.id.aenet_title);
            text = findViewById(R.id.aenet_text);
            title.setText(note.getTitle());
            text.setText(note.getText());
            editMode = true;
        } else {
            makeLongToast("There are no notes to edit.");
        }
    }

    public void onClickDelete(View v) {
        if (selectedNoteIndex >= 0 && rob.getNotes().size() > 0) {
            rob.removeNote(rob.getNoteAt(selectedNoteIndex));
            rob.saveCharacter(this);
            if (selectedNoteIndex == rob.getNotes().size()) {
                selectedNoteIndex--;
            }
            buildLinearLayout();
        } else {
            makeLongToast("There are no notes to delete.");
        }
    }

    private void buildLinearLayout() {
        LinearLayout noteListLayout = (LinearLayout) findViewById(R.id.nll_noteList);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 30);

        ArrayList<Note> notes = rob.getNotes();
        while (noteListLayout.getChildCount() > 0) {
            noteListLayout.removeViewAt(0);
        }
        for (int i = 0; i < notes.size(); i++) {
            TextView tvChar = new TextView(this);
            tvChar.setText(notes.get(i).getTitle());
            tvChar.setTextSize(32);
            tvChar.setLayoutParams(lp);
            tvChar.setBackgroundResource(R.drawable.character_border);
            tvChar.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            tvChar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout noteListLayout = (LinearLayout) findViewById(R.id.nll_noteList);
                    for (int i = 0; i < noteListLayout.getChildCount(); i++) {
                        noteListLayout.getChildAt(i).setBackgroundResource(R.drawable.character_border);
                        if (noteListLayout.getChildAt(i).equals((TextView) v)) {
                            selectedNoteIndex = i;
                            note = notes.get(i);
                        }
                    }
                    v.setBackgroundResource(R.drawable.selected_character_border);
                }
            });
            if (i == selectedNoteIndex) {
                tvChar.setBackgroundResource(R.drawable.selected_character_border);
            }
            noteListLayout.addView(tvChar);
        }
    }

    private void setupContentView() {
        setContentView(R.layout.activity_notes);
        ((Button) findViewById(R.id.navbtn_notes)).setTextColor(getResources().getColor(R.color.green));
        buildLinearLayout();
    }

    //The following are for activity_edit_note

    public void onClickSave(View v) {
        if (title.getText().toString().length() < 1) {
            makeLongToast("The title may not be blank.");
        } else {
            note.setText(text.getText().toString());
            note.setTitle(title.getText().toString());
            rob.updateNote(selectedNoteIndex, note);
            rob.saveCharacter(this);
            setupContentView();
            editMode = false;
        }
    }

    public void onClickEditNoteDelete(View v) {
        rob.removeNoteAt(selectedNoteIndex);
        rob.saveCharacter(this);
        setupContentView();
        editMode = false;
    }

    public void onClickBackNotes(View v) {
        setupContentView();
        editMode = false;
    }

    public void onDialogNegativeClick(NoteDialogFragment df) {
        df.getDialog().cancel();
    }

    public void onDialogPositiveClick(NoteDialogFragment df) {
        Note n = df.getNote();
        if (n.getTitle().length() < 1) {
            makeLongToast("The title may not be blank.");
        } else {
            rob.addNote(n);
            rob.saveCharacter(this);
            note = n;
            setContentView(R.layout.activity_edit_note);
            title = findViewById(R.id.aenet_title);
            text = findViewById(R.id.aenet_text);
            title.setText(n.getTitle());
            editMode = true;
            df.getDialog().cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (editMode) {
            onClickBackNotes(null);
        }
        return;
    }
}
