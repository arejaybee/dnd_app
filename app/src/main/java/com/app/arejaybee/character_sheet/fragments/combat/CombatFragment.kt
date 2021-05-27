package com.app.arejaybee.character_sheet.fragments.combat

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.Weapon
import com.app.arejaybee.character_sheet.databinding.FragmentCombat5eBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.notes.CombatWeaponAdapter
import com.app.arejaybee.character_sheet.utils.Util

class CombatFragment : RobFragment() {
    companion object {
        const val TAG = "CombatFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentCombat5eBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_combat5e, container, false)
        val view: View = binding.root
        binding.rob = activity?.rob
        binding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.combat_attack_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = CombatWeaponAdapter(it.rob.weapons, it)
        }

        val proficiency = view.findViewById<TextView>(R.id.combat_proficiency)
        val curHp = view.findViewById<TextView>(R.id.combat_current_health)
        val maxHp = view.findViewById<TextView>(R.id.combat_max_health)

        Util.addNumberSpinnerToView(requireActivity(), "Proficiency", proficiency, 0)
        Util.addNumberSpinnerToView(requireActivity(), "Current Health", curHp, -99)
        Util.addNumberSpinnerToView(requireActivity(), "Max Health", maxHp, 0)
    }

    override fun onClickAdd() { showWeaponDialog(false) }

    override fun onClickEdit() { showWeaponDialog(true) }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    private fun showWeaponDialog(isEdit: Boolean) {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_weapon, null)
        dialogView?.let {
            val name = it.findViewById<EditText>(R.id.weapon_dialog_title_edittext)
            val notes = it.findViewById<EditText>(R.id.weapon_dialog_description_edittext)
            if(isEdit) {
                name.setText(CombatWeaponAdapter.selectedWeapon?.name)
                notes.setText(CombatWeaponAdapter.selectedWeapon?.notes)
            }
            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val ability = if(isEdit) CombatWeaponAdapter.selectedWeapon else Weapon()
                        ability?.name = name.text.toString()
                        ability?.notes = notes.text.toString()
                        if(isEdit) {
                            ability?.let{ editAbility ->
                                val rob = activity?.rob
                                if(rob != null) {
                                    val aIndex = rob.weapons.indexOf(CombatWeaponAdapter.selectedWeapon)
                                    rob.weapons[aIndex] = editAbility
                                }
                            }
                        }
                        else {
                            activity?.rob?.weapons?.add(ability!!)
                        }
                        view?.findViewById<RecyclerView>(R.id.ability_list_recycler)?.adapter?.notifyDataSetChanged()
                        activity?.rob?.saveCharacter()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }
    }

    private fun setupToolbar() {
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)

        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.showMenuItem(R.id.toolbar_home_btn)
    }
}