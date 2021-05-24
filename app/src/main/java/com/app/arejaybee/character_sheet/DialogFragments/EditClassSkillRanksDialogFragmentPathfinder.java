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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.app.arejaybee.character_sheet.DataObjects.Skill;
import com.app.arejaybee.character_sheet.R;

public class EditClassSkillRanksDialogFragmentPathfinder extends DialogFragment {

    Skill m_skill;
    int m_index;
    int m_ability;
    boolean m_classSkill;
    TextView skillMod;
    TextView abilityMod;
    EditText rank;
    EditText bonus;
    CheckBox classSkill;

    // Use this instance of the interface to deliver action events
    EditClassSkillRanksDialogFragmentPathfinder.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_edit_skill_rank, null));
        view.findViewById(R.id.fesrbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(EditClassSkillRanksDialogFragmentPathfinder.this);
            }
        });
        view.findViewById(R.id.fesrbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(EditClassSkillRanksDialogFragmentPathfinder.this);
            }
        });

        Bundle b = getArguments();
        m_skill = (Skill) b.get("skill");
        m_index = b.getInt("index");
        m_ability = b.getInt("ability");
        if (m_skill != null) {
            skillMod = (TextView) view.findViewById(R.id.fesret_skillMod);
            abilityMod = (TextView) view.findViewById(R.id.fesret_abilityMod);
            rank = (EditText) view.findViewById(R.id.fesret_ranks);
            bonus = (EditText) view.findViewById(R.id.fesret_bonus);

            skillMod.setText(m_skill.getMod() + "");
            abilityMod.setText(m_ability + "");
            rank.setText(m_skill.getRank() + "");
            bonus.setText(m_skill.getBonus() + "");

            TextWatcher tw = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String r = rank.getText().toString();
                    int rankVal = 0;
                    int bonusVal = 0;
                    if (r.length() > 0) {
                        rankVal = Integer.parseInt(rank.getText().toString());
                    }
                    String b = bonus.getText().toString();
                    if (b.length() > 0 && !b.equals("-")) {
                        bonusVal = Integer.parseInt(bonus.getText().toString());
                    }
                    if (!m_skill.isClassSkill()) {
                        rankVal /= 2;
                    }
                    skillMod.setText("" + (rankVal + bonusVal + m_ability));
                }
            };

            rank.addTextChangedListener(tw);
            bonus.addTextChangedListener(tw);
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
            mListener = (EditClassSkillRanksDialogFragmentPathfinder.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public Skill getSkill() {
        String r = rank.getText().toString();
        int rankVal = 0;
        int bonusVal = 0;
        if (r.length() > 0) {
            rankVal = Integer.parseInt(rank.getText().toString());
        }
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(bonus.getText().toString());
        }
        m_skill.setRank(rankVal);
        m_skill.setBonus(bonusVal);
        return m_skill;
    }

    public int getSkillIndex() {
        return m_index;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(EditClassSkillRanksDialogFragmentPathfinder dialog);

        void onDialogNegativeClick(EditClassSkillRanksDialogFragmentPathfinder dialog);
    }
}
