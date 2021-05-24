package com.app.arejaybee.character_sheet.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.app.arejaybee.character_sheet.DataObjects.Spell;
import com.app.arejaybee.character_sheet.R;

public class SpellDialogFragment extends DialogFragment {

    String m_name;
    String m_description;
    int m_level;
    EditText name;
    EditText description;
    boolean edit;
    int index;

    // Use this instance of the interface to deliver action events
    SpellDialogFragment.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_spell, null));
        view.findViewById(R.id.fnbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(SpellDialogFragment.this);
            }
        });
        view.findViewById(R.id.fnbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(SpellDialogFragment.this);
            }
        });

        Bundle b = getArguments();
        m_name = b.getString("name");
        m_description = b.getString("description");
        m_level = b.getInt("level");
        edit = b.getBoolean("edit", false);
        index = b.getInt("index");
        name = view.findViewById(R.id.fspet_name);
        description = view.findViewById(R.id.fspet_description);
        name.setText(m_name);
        description.setText(m_description);
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
            mListener = (SpellDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public Spell getSpell() {
        Spell s = new Spell(m_level);
        s.setName(name.getText().toString());
        s.setDescription(description.getText().toString());
        return s;
    }

    public boolean isEdit() {
        return edit;
    }

    public int getIndex() {
        return index;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(SpellDialogFragment dialog);

        void onDialogNegativeClick(SpellDialogFragment dialog);
    }
}
