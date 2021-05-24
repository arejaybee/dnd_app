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
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.EnumHelper;
import com.app.arejaybee.character_sheet.DataObjects.SavingThrow;
import com.app.arejaybee.character_sheet.R;

public class CombatSaveDialogFragment5e extends DialogFragment {

    String m_mod;
    String m_total;
    int m_modVal;
    int m_bonus;
    int m_proficiency;
    int m_prof;
    TextView total;
    TextView mod;
    TextView modVal;
    TextView proficiency;
    EditText bonus;
    RadioButton none;
    RadioButton half;
    RadioButton normal;
    RadioButton profDouble;
    // Use this instance of the interface to deliver action events
    CombatSaveDialogFragment5e.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_combat_save5e, null));
        view.findViewById(R.id.fesrbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(CombatSaveDialogFragment5e.this);
            }
        });
        view.findViewById(R.id.fesrbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(CombatSaveDialogFragment5e.this);
            }
        });

        Bundle b = getArguments();
        m_mod = b.getString("mod");
        m_modVal = b.getInt("modVal");
        m_bonus = b.getInt("bonus");
        m_proficiency = b.getInt("proficiency");
        m_prof = b.getInt("prof");
        proficiency = view.findViewById(R.id.fcstv_proficiencyMod);
        mod = view.findViewById(R.id.fcstv_mod);
        modVal = view.findViewById(R.id.fcstv_modVal);
        bonus = view.findViewById(R.id.fcset_bonus);
        none = view.findViewById(R.id.fcsrb_none);
        half = view.findViewById(R.id.fcsrb_half);
        normal = view.findViewById(R.id.fcsrb_normal);
        profDouble = view.findViewById(R.id.fcsrb_double);
        total = view.findViewById(R.id.fcstv_total);
        switch (m_prof) {
            case 1:
                half.setChecked(true);
                break;
            case 2:
                normal.setChecked(true);
                break;
            case 3:
                profDouble.setChecked(true);
                break;
            default:
                none.setChecked(true);
                break;
        }

        mod.setText(m_mod);
        modVal.setText("" + m_modVal);
        bonus.setText("" + m_bonus);
        proficiency.setText("" + (m_prof == 0 ? 0 : m_prof == 1 ? m_proficiency / 2 : m_prof == 2 ? m_proficiency : m_proficiency * 2));
        total.setText("" + (m_modVal + (m_prof == 0 ? 0 : m_prof == 1 ? m_proficiency / 2 : m_prof == 2 ? m_proficiency : m_proficiency * 2) + m_bonus));

        //radio button listener
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                none.setChecked(false);
                half.setChecked(false);
                profDouble.setChecked(false);
                normal.setChecked(false);
                if (none.equals(v)) {
                    onClickNone(v);
                    none.setChecked(true);
                } else if (normal.equals(v)) {
                    onClickNormal(v);
                    normal.setChecked(true);
                } else if (half.equals(v)) {
                    onClickHalf(v);
                    half.setChecked(true);
                } else {
                    onClickDouble(v);
                    profDouble.setChecked(true);
                }
            }
        };

        none.setOnClickListener(ocl);
        half.setOnClickListener(ocl);
        normal.setOnClickListener(ocl);
        profDouble.setOnClickListener(ocl);
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String r = proficiency.getText().toString();
                int rankVal = 0;
                int bonusVal = 0;
                if (r.length() > 0) {
                    rankVal = Integer.parseInt(proficiency.getText().toString());
                }
                String b = bonus.getText().toString();
                if (b.length() > 0 && !b.equals("-")) {
                    bonusVal = Integer.parseInt(bonus.getText().toString());
                }
                total.setText("" + (rankVal + bonusVal + m_modVal));
            }
        };
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
            mListener = (CombatSaveDialogFragment5e.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public SavingThrow getSave() {
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(bonus.getText().toString());
        }
        SavingThrow st = new SavingThrow(m_mod, "5e");
        st.setBonus(bonusVal);
        st.setBase(m_modVal);
        st.setProficiency(EnumHelper.PROFICIENCY.values()[m_prof]);
        return st;
    }

    //buttons
    public void onClickNone(View v) {
        int bonusVal = 0;
        m_prof = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        proficiency.setText("0");
        total.setText("" + (0 + bonusVal + m_modVal));

    }

    public void onClickHalf(View v) {
        m_prof = 1;
        int rankVal = m_proficiency / 2;
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        proficiency.setText("" + rankVal);
        total.setText("" + (rankVal + bonusVal + m_modVal));
    }

    public void onClickNormal(View v) {
        m_prof = 2;
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        proficiency.setText("" + m_proficiency);
        total.setText("" + (m_proficiency + bonusVal + m_modVal));
    }

    public void onClickDouble(View v) {
        m_prof = 3;
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        int rankVal = 2 * m_proficiency;
        proficiency.setText("" + rankVal);
        total.setText("" + (rankVal + bonusVal + m_modVal));
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(CombatSaveDialogFragment5e dialog);

        void onDialogNegativeClick(CombatSaveDialogFragment5e dialog);
    }
}
