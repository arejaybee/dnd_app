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

import com.app.arejaybee.character_sheet.DataObjects.ArmorClass;
import com.app.arejaybee.character_sheet.R;

public class ArmorDialogFragment5e extends DialogFragment {

    int m_armor;
    int m_shield;
    int m_dex;
    int m_bonus;
    TextView dex;
    EditText armor;
    EditText shield;
    EditText bonus;

    // Use this instance of the interface to deliver action events
    ArmorDialogFragment5e.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_armor5e, null));
        view.findViewById(R.id.fesrbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(ArmorDialogFragment5e.this);
            }
        });
        view.findViewById(R.id.fesrbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(ArmorDialogFragment5e.this);
            }
        });

        Bundle b = getArguments();
        m_dex = b.getInt("dex");
        m_bonus = b.getInt("bonus");
        m_shield = b.getInt("shield");
        m_armor = b.getInt("armor");
        dex = view.findViewById(R.id.fitv_dex);
        bonus = view.findViewById(R.id.fiet_bonus);
        shield = view.findViewById(R.id.fiet_shield);
        armor = view.findViewById(R.id.fiet_armor);

        ((TextView) view.findViewById(R.id.fitv_total)).setText("" + (m_dex + m_bonus + m_armor + m_shield));
        dex.setText("" + m_dex);
        bonus.setText("" + m_bonus);
        shield.setText("" + m_shield);
        armor.setText("" + m_armor);

        TextWatcher tv = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String b = bonus.getText().toString();
                String sh = shield.getText().toString();
                String a = armor.getText().toString();
                if (b.length() > 0 && !b.equalsIgnoreCase("-")) {
                    m_bonus = Integer.parseInt(bonus.getText().toString());
                    ((TextView) view.findViewById(R.id.fitv_total)).setText("" + (m_dex + m_bonus + m_armor + m_shield));
                }
                if (sh.length() > 0 && !sh.equalsIgnoreCase("-")) {
                    m_shield = Integer.parseInt(shield.getText().toString());
                    ((TextView) view.findViewById(R.id.fitv_total)).setText("" + (m_dex + m_bonus + m_armor + m_shield));
                }
                if (a.length() > 0 && !a.equalsIgnoreCase("-")) {
                    m_armor = Integer.parseInt(armor.getText().toString());
                    ((TextView) view.findViewById(R.id.fitv_total)).setText("" + (m_dex + m_bonus + m_armor + m_shield));
                }
            }
        };
        armor.addTextChangedListener(tv);
        shield.addTextChangedListener(tv);
        bonus.addTextChangedListener(tv);
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
            mListener = (ArmorDialogFragment5e.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public ArmorClass getArmor() {
        ArmorClass ac = new ArmorClass();
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(bonus.getText().toString());
        }
        int shieldVal = 0;
        String sh = shield.getText().toString();
        if (sh.length() > 0 && !sh.equals("-")) {
            shieldVal = Integer.parseInt(shield.getText().toString());
        }
        int armorVal = 0;
        String a = armor.getText().toString();
        if (a.length() > 0 && !a.equals("-")) {
            armorVal = Integer.parseInt(armor.getText().toString());
        }
        ac.setArmor(armorVal);
        ac.setShield(shieldVal);
        ac.setMisc(bonusVal);
        return ac;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(ArmorDialogFragment5e dialog);

        void onDialogNegativeClick(ArmorDialogFragment5e dialog);
    }
}
