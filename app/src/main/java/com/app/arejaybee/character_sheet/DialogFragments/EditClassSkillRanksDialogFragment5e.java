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
import com.app.arejaybee.character_sheet.DataObjects.Skill;
import com.app.arejaybee.character_sheet.R;

public class EditClassSkillRanksDialogFragment5e extends DialogFragment {

    Skill m_skill;
    int m_index;
    int m_ability;
    int m_proficiency; //The value for the proficiency score
    int m_prof; //The enum for proficiency buttons
    TextView skillMod;
    TextView abilityMod;
    TextView proficiency;
    EditText bonus;

    RadioButton none;
    RadioButton half;
    RadioButton normal;
    RadioButton doubleProf;

    // Use this instance of the interface to deliver action events
    EditClassSkillRanksDialogFragment5e.NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = (inflater.inflate(R.layout.fragment_edit_skill_rank5e, null));
        view.findViewById(R.id.fesrbtn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(EditClassSkillRanksDialogFragment5e.this);
            }
        });
        view.findViewById(R.id.fesrbtn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(EditClassSkillRanksDialogFragment5e.this);
            }
        });

        Bundle b = getArguments();
        m_skill = (Skill) b.get("skill");
        m_index = b.getInt("index");
        m_ability = b.getInt("ability");
        m_proficiency = b.getInt("proficiency");
        m_prof = b.getInt("prof");
        if (m_skill != null) {
            skillMod = (TextView) view.findViewById(R.id.fesret_skillMod);
            abilityMod = (TextView) view.findViewById(R.id.fesret_abilityMod);
            proficiency = (TextView) view.findViewById(R.id.fesret_proficiencyMod);
            bonus = (EditText) view.findViewById(R.id.fesret_bonus);

            skillMod.setText(m_skill.getMod() + "");
            abilityMod.setText(m_ability + "");
            bonus.setText(m_skill.getBonus() + "");
            proficiency.setText(m_skill.getRank() + "");

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
                    skillMod.setText("" + (rankVal + bonusVal + m_ability));
                }
            };
            bonus.addTextChangedListener(tw);

            none = view.findViewById(R.id.fesr5rb_none);
            half = view.findViewById(R.id.fesr5rb_half);
            normal = view.findViewById(R.id.fesr5rb_normal);
            doubleProf = view.findViewById(R.id.fesr5rb_double);
            View.OnClickListener ocl = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    none.setChecked(false);
                    half.setChecked(false);
                    doubleProf.setChecked(false);
                    normal.setChecked(false);
                    if (none.equals(v)) {
                        onClickNone(v);
                        m_prof = 0;
                        none.setChecked(true);
                    } else if (normal.equals(v)) {
                        onClickNormal(v);
                        m_prof = 2;
                        normal.setChecked(true);
                    } else if (half.equals(v)) {
                        onClickHalf(v);
                        m_prof = 1;
                        half.setChecked(true);
                    } else {
                        onClickDouble(v);
                        m_prof = 3;
                        doubleProf.setChecked(true);
                    }
                }
            };
            none.setOnClickListener(ocl);
            half.setOnClickListener(ocl);
            normal.setOnClickListener(ocl);
            doubleProf.setOnClickListener(ocl);
            switch (m_prof) {
                case 1:
                    half.setChecked(true);
                    break;
                case 2:
                    normal.setChecked(true);
                    break;
                case 3:
                    doubleProf.setChecked(true);
                    break;
                default:
                    none.setChecked(true);
                    break;
            }
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
            mListener = (EditClassSkillRanksDialogFragment5e.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public Skill getSkill() {
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
        m_skill.setRank(rankVal); //rank is literally the same as proficiency as far as we care
        m_skill.setBonus(bonusVal);
        m_skill.setProficiency(EnumHelper.PROFICIENCY.values()[m_prof]);
        return m_skill;
    }

    public int getSkillIndex() {
        return m_index;
    }

    //buttons
    public void onClickNone(View v) {
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        proficiency.setText("0");
        skillMod.setText("" + (0 + bonusVal + m_ability));

    }

    public void onClickHalf(View v) {
        int rankVal = m_proficiency / 2;
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        proficiency.setText("" + rankVal);
        skillMod.setText("" + (rankVal + bonusVal + m_ability));
    }

    public void onClickNormal(View v) {
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        proficiency.setText("" + m_proficiency);
        skillMod.setText("" + (m_proficiency + bonusVal + m_ability));
    }

    public void onClickDouble(View v) {
        int bonusVal = 0;
        String b = bonus.getText().toString();
        if (b.length() > 0 && !b.equals("-")) {
            bonusVal = Integer.parseInt(b);
        }
        int rankVal = 2 * m_proficiency;
        proficiency.setText("" + rankVal);
        skillMod.setText("" + (rankVal + bonusVal + m_ability));
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(EditClassSkillRanksDialogFragment5e dialog);

        void onDialogNegativeClick(EditClassSkillRanksDialogFragment5e dialog);
    }
}
