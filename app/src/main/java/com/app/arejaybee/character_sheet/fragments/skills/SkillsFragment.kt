package com.app.arejaybee.character_sheet.fragments.skills

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.utils.Util

class SkillsFragment : RobFragment() {
    companion object {
        const val TAG = "SkillsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_skills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.skills_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            it.rob.sortSkills()
            recyclerView.adapter = SkillsAdapter(it.rob.skills, it)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickEdit() {
        val adapter = view?.findViewById<RecyclerView>(R.id.skills_recycler)?.adapter as SkillsAdapter
        val skill = adapter.getSkill()

        val inflater = activity?.layoutInflater
        val rob = activity?.rob!!
        val dialogView = inflater?.inflate(R.layout.dialog_skill_edit, null)
        dialogView?.let { it ->
            val oBonus = skill.bonus
            val oProf = skill.proficiency
            val oAbility = skill.ability
            val bonus = it.findViewById<EditText>(R.id.skill_dialog_bonus)
            val ability = it.findViewById<Spinner>(R.id.skill_dialog_ability)
            val proficiency = it.findViewById<Spinner>(R.id.skill_dialog_proficiency)
            val total = it.findViewById<TextView>(R.id.skill_dialog_mod)
            total.text = rob.getSkillMod(skill).toString()
            bonus.setText(skill.bonus.toString())
            Util.buildDialogTypeSpinner(requireContext(), ability, R.array.abilities)
            Util.buildDialogTypeSpinner(requireContext(), proficiency, R.array.proficiencies)

            val selectionArray = resources.getStringArray(R.array.abilities)
            val index = if(selectionArray.toList().indexOf(skill.ability) < 0) 0 else selectionArray.toList().indexOf(skill.ability)
            ability.setSelection(index, true)
            ability.onItemSelectedListener = object: OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    skill.ability = selectionArray[position]
                    rob.skills[rob.skills.indexOf(skill)] = skill //indexOf finds the skill by name, then we replace with the updated object

                    total.text = rob.getSkillMod(skill).toString()
                    rob.saveCharacter()
                }
            }

            val profIndex = skill.proficiency.ordinal
            proficiency.setSelection(profIndex, true)
            proficiency.onItemSelectedListener = object: OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    skill.proficiency = EnumHelper.PROFICIENCY.values()[position]
                    rob.skills[rob.skills.indexOf(skill)] = skill //indexOf finds the skill by name, then we replace with the updated object

                    total.text = rob.getSkillMod(skill).toString()
                    rob.saveCharacter()
                }
            }

            bonus.addTextChangedListener {
                skill.bonus = Util.getNumberFromEditText(bonus)
                rob.skills[rob.skills.indexOf(skill)] = skill //indexOf finds the skill by name, then we replace with the updated object

                total.text = rob.getSkillMod(skill).toString()
                rob.saveCharacter()
            }

            AlertDialog.Builder(requireContext())
                    .setCancelable(false)
                    .setTitle(skill.name)
                    .setView(it)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        skill.bonus = oBonus
                        skill.proficiency = oProf
                        skill.ability = oAbility

                        rob.skills[rob.skills.indexOf(skill)] = skill
                        rob.saveCharacter()
                        dialog.dismiss()
                        adapter.notifyDataSetChanged()
                    }
                    .show()
        }
    }

    private fun setupToolbar() {
        activity?.hideMenuItem(R.id.toolbar_add_btn)
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)

        activity?.showMenuItem(R.id.toolbar_home_btn)
    }
}