package com.app.arejaybee.character_sheet.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.app.arejaybee.character_sheet.R;

import java.util.Arrays;

public class NewCharacterDialogFragment extends DialogFragment {

    Spinner edition;
    CheckBox skills;
    int editionVal;
    boolean skillsIsChecked;

    // Use this instance of the interface to deliver action events
    NewCharacterDialogFragment.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_new_character, null));
        view.findViewById(R.id.fncbtn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(NewCharacterDialogFragment.this);
            }
        });
        view.findViewById(R.id.fncbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(NewCharacterDialogFragment.this);
            }
        });
        edition = view.findViewById(R.id.fncs_edition);
        skills = view.findViewById(R.id.fncs_skills);
        skillsIsChecked = skills.isChecked();
        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillsIsChecked = ((CheckBox) v).isChecked();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.editions, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edition.setAdapter(adapter);
        edition.setSelection(Arrays.asList(getResources().getStringArray(R.array.editions)).indexOf(editionVal));
        edition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editionVal = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        return builder.create();
    }

    public void onCheckSkills(View v) {
        skillsIsChecked = ((CheckBox) v).isChecked();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NewCharacterDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public String getEdition() {
        switch (editionVal) {
            case (0):
                return "3.5";
            case (1):
                return "5e";
            case (2):
                return "Pathfinder";
            default:
                return ""; //should never hit this
        }
    }

    public boolean areSkillsGenerated() {
        return skillsIsChecked;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(NewCharacterDialogFragment dialog);

        void onDialogNegativeClick(NewCharacterDialogFragment dialog);
    }
}
