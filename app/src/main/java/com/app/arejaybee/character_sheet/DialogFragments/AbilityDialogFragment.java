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
import android.widget.EditText;
import android.widget.Spinner;

import com.app.arejaybee.character_sheet.DataObjects.Ability;
import com.app.arejaybee.character_sheet.R;

import java.util.Arrays;

public class AbilityDialogFragment extends DialogFragment {

    EditText title;
    EditText description;
    Spinner type;
    Ability ability;
    int index;
    boolean isEdit;
    String type_val;

    // Use this instance of the interface to deliver action events
    AbilityDialogFragment.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_ability, null);
        view.findViewById(R.id.fnbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(AbilityDialogFragment.this);
            }
        });
        view.findViewById(R.id.fnbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(AbilityDialogFragment.this);
            }
        });

        Bundle b = getArguments();
        ability = (Ability) b.get("ability");
        index = b.getInt("index");
        type_val = ability.getType();
        if (ability != null) {
            title = (EditText) view.findViewById(R.id.faet_title);
            description = (EditText) view.findViewById(R.id.faet_description);
            title.setText(ability.getTitle());
            description.setText(ability.getDescription());

            type = (Spinner) view.findViewById(R.id.faet_type);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.abilityTypes, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type.setAdapter(adapter);
            type.setSelection(Arrays.asList(getResources().getStringArray(R.array.abilityTypes)).indexOf(type_val));

            if (ability.getTitle().trim().length() > 0) {
                title.setText(ability.getTitle().trim());
            } else {
                title.setHint("Enter name of skill");
            }
            type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    type_val = getResources().getStringArray(R.array.abilityTypes)[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
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
            mListener = (AbilityDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public int getIndex() {
        return index;
    }

    public Ability getAbility() {
        return new Ability(title.getText().toString(), type_val, description.getText().toString());
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(AbilityDialogFragment dialog);

        void onDialogNegativeClick(AbilityDialogFragment dialog);
    }
}
