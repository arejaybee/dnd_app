package com.app.arejaybee.character_sheet.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Skill;
import com.app.arejaybee.character_sheet.R;

import java.util.Arrays;

public class EditClassSkillDialogFragment extends DialogFragment {

    Skill m_skill;
    Spinner abilities;
    int m_index;
    int m_ability;
    TextView skillName;
    Boolean isEdit;

    // Use this instance of the interface to deliver action events
    EditClassSkillDialogFragment.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_edit_skill, null));
        view.findViewById(R.id.fesbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(EditClassSkillDialogFragment.this);
            }
        });
        view.findViewById(R.id.fesbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(EditClassSkillDialogFragment.this);
            }
        });

        Bundle b = getArguments();
        m_skill = (Skill) b.get("skill");
        m_index = b.getInt("index");
        m_ability = b.getInt("ability");
        isEdit = b.getBoolean("isEdit");

        ImageButton deleteButton = view.findViewById(R.id.feset_delete);
        if(!isEdit) {
            deleteButton.setVisibility(View.GONE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogDeleteClick(EditClassSkillDialogFragment.this);
            }
        });

        if (m_skill != null) {
            skillName = view.findViewById(R.id.feset_skillName);
            abilities = view.findViewById(R.id.feset_ability);

            skillName.setText(m_skill.getName());

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.abilities, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            abilities.setAdapter(adapter);
            abilities.setSelection(m_ability);
            abilities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    m_ability = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
        }

        builder.setView(view);
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (EditClassSkillDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public Skill getSkill() {
        m_skill.setName(skillName.getText().toString());
        m_skill.setAbility(getResources().getStringArray(R.array.abilities)[m_ability]);
        return m_skill;
    }

    public int getSkillIndex() {
        return m_index;
    }

    public Boolean isEdit() {
        return isEdit;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(EditClassSkillDialogFragment dialog);

        void onDialogNegativeClick(EditClassSkillDialogFragment dialog);

        void onDialogDeleteClick(EditClassSkillDialogFragment dialog);
    }
}
