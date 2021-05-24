package com.app.arejaybee.character_sheet.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.R;

public class GrappleDialogFragment extends DialogFragment {

    int m_str;
    int m_bab;
    int m_bonus;
    TextView str;
    TextView bab;
    EditText bonus;

    // Use this instance of the interface to deliver action events
    GrappleDialogFragment.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_grapple, null));
        view.findViewById(R.id.fesrbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(GrappleDialogFragment.this);
            }
        });
        view.findViewById(R.id.fesrbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(GrappleDialogFragment.this);
            }
        });

        Bundle b = getArguments();
        m_str = b.getInt("str");
        m_bab = b.getInt("bab");
        m_bonus = b.getInt("bonus");
        str = view.findViewById(R.id.fgtv_str);
        bonus = view.findViewById(R.id.fget_bonus);
        bab = view.findViewById(R.id.fgtv_bab);
        ((TextView) view.findViewById(R.id.fgtv_total)).setText("" + (m_str + m_bab + m_bonus));
        str.setText("" + m_str);
        bab.setText("" + m_bab);
        bonus.setText("" + m_bonus);
        bonus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String b = bonus.getText().toString();
                if (b.length() > 0 && !b.equalsIgnoreCase("-")) {
                    m_bonus = Integer.parseInt(bonus.getText().toString());
                    ((TextView) view.findViewById(R.id.fgtv_total)).setText("" + (m_str + m_bab + m_bonus));
                }
            }
        });
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
            mListener = (GrappleDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public int getBonus() {
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(bonus.getText().toString());
        }

        return bonusVal;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(GrappleDialogFragment dialog);

        void onDialogNegativeClick(GrappleDialogFragment dialog);
    }
}
