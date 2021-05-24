package com.app.arejaybee.character_sheet.DialogFragments;

import android.annotation.SuppressLint;
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

import com.app.arejaybee.character_sheet.DataObjects.SavingThrow;
import com.app.arejaybee.character_sheet.R;

public class CombatSaveDialogFragment35 extends DialogFragment {

    String m_mod;
    int m_modVal;
    int m_bonus;
    int m_class;

    TextView total;
    TextView mod;
    TextView modVal;
    EditText classVal;
    EditText bonus;
    // Use this instance of the interface to deliver action events
    CombatSaveDialogFragment35.NoticeDialogListener mListener;

    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_combat_save35, null));
        view.findViewById(R.id.fesrbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(CombatSaveDialogFragment35.this);
            }
        });
        view.findViewById(R.id.fesrbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(CombatSaveDialogFragment35.this);
            }
        });

        Bundle b = getArguments();
        m_mod = b.getString("mod");
        m_modVal = b.getInt("modVal");
        m_bonus = b.getInt("bonus");
        m_class = b.getInt("class");
        mod = view.findViewById(R.id.fcstv_mod);
        modVal = view.findViewById(R.id.fcstv_modVal);
        bonus = view.findViewById(R.id.fcset_bonus);
        classVal = view.findViewById(R.id.fcset_classVal);
        total = view.findViewById(R.id.fcstv_total);

        mod.setText(m_mod);
        modVal.setText(Integer.toString(m_modVal));
        bonus.setText(Integer.toString(m_bonus));
        classVal.setText(Integer.toString(m_class));
        total.setText(Integer.toString(m_modVal + m_class + m_bonus));


        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int bonusVal = 0;
                int classValue = 0;
                String b = bonus.getText().toString();
                if (b.length() > 0 && !b.equals("-")) {
                    bonusVal = Integer.parseInt(bonus.getText().toString());
                }
                String c = classVal.getText().toString();
                if (c.length() > 0 && !c.equals("-")) {
                    classValue = Integer.parseInt(classVal.getText().toString());
                }
                total.setText("" + (classValue + bonusVal + m_modVal));
            }
        };
        classVal.addTextChangedListener(tw);
        bonus.addTextChangedListener(tw);

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
            mListener = (CombatSaveDialogFragment35.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public SavingThrow getSave() {
        int bonusVal = 0;
        int classValue = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(bonus.getText().toString());
        }
        String c = classVal.getText().toString();
        if (c.length() > 0 && !c.equals("-")) {
            classValue = Integer.parseInt(classVal.getText().toString());
        }
        SavingThrow st = new SavingThrow(m_mod, "3.5");
        st.setBonus(bonusVal);
        st.setClassBonus(classValue);
        st.setBase(m_modVal);
        return st;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(CombatSaveDialogFragment35 dialog);

        void onDialogNegativeClick(CombatSaveDialogFragment35 dialog);
    }
}
