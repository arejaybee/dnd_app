package com.app.arejaybee.character_sheet.fragments.combat

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.Weapon
import com.app.arejaybee.character_sheet.databinding.FragmentCombat5eBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.notes.CombatWeaponAdapter
import com.app.arejaybee.character_sheet.utils.Util
import kotlin.math.abs

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

        Util.addNumberSpinnerToView(requireActivity(), getString(R.string.proficiency), proficiency, 0)
        proficiency.addTextChangedListener {
            view.findViewById<RecyclerView>(R.id.combat_attack_recycler)?.adapter?.notifyDataSetChanged()
        }
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

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.combat_attack_recycler)?.adapter as CombatWeaponAdapter
        adapter.deleteWeapon()
        adapter.notifyDataSetChanged()
    }

    private fun showWeaponDialog(isEdit: Boolean) {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_weapon, null)
        dialogView?.let {
            val name = it.findViewById<EditText>(R.id.weapon_dialog_title_edittext)
            val notes = it.findViewById<EditText>(R.id.weapon_dialog_description_edittext)
            val type = it.findViewById<EditText>(R.id.weapon_dialog_type)
            val damageDice = it.findViewById<EditText>(R.id.weapon_dialog_damage_roll)
            val range = it.findViewById<EditText>(R.id.weapon_dialog_attack_range)
            val attackBonus = it.findViewById<TextView>(R.id.weapon_dialog_attack_bonus)
            val damageBonus = it.findViewById<TextView>(R.id.weapon_dialog_damage_bonus)
            val abilityType = it.findViewById<Spinner>(R.id.weapon_dialog_stat_type)
            val prof: CheckBox = it.findViewById(R.id.weapon_dialog_proficient)

            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.attack_bonus), attackBonus, -99)
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.damage_bonus), damageBonus, -99)

            Util.buildDialogTypeSpinner(requireContext(), abilityType, R.array.abilities)

            val selectionArray = resources.getStringArray(R.array.abilities)
            val index = if(selectionArray.toList().indexOf(CombatWeaponAdapter.selectedWeapon?.abilityType) < 0) 0
            else selectionArray.toList().indexOf(CombatWeaponAdapter.selectedWeapon?.abilityType)
            abilityType.setSelection(index, true)
            if(isEdit) {
                val weapon = CombatWeaponAdapter.selectedWeapon!!
                name.setText(weapon.name)
                notes.setText(weapon.notes)
                type.setText(weapon.type)
                damageDice.setText(weapon.damage)
                range.setText(weapon.range)
                attackBonus.text = weapon.toHit
                damageBonus.text = weapon.damageBonus
                prof.isChecked = weapon.isProficient
            }
            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val weapon = if(isEdit) CombatWeaponAdapter.selectedWeapon else Weapon()
                        weapon?.name = name.text.toString()
                        weapon?.notes = notes.text.toString()
                        weapon?.type = type.text.toString()
                        weapon?.damage = damageDice.text.toString()
                        weapon?.range = range.text.toString()
                        weapon?.toHit = attackBonus.text.toString()
                        weapon?.damageBonus = damageBonus.text.toString()
                        weapon?.abilityType = abilityType.selectedItem.toString()
                        weapon?.isProficient = prof.isChecked
                        if(isEdit) {
                            weapon?.let{ editWeapon ->
                                val rob = activity?.rob
                                if(rob != null) {
                                    val aIndex = rob.weapons.indexOf(CombatWeaponAdapter.selectedWeapon)
                                    rob.weapons[aIndex] = editWeapon
                                }
                            }
                        }
                        else {
                            activity?.rob?.weapons?.add(weapon!!)
                        }
                        //val adapter = view?.findViewById<RecyclerView>(R.id.combat_attack_recycler)?.adapter as CombatWeaponAdapter
                        activity?.rob?.saveCharacter()
                        //adapter.updateWeapon(weapon!!)
                        view?.findViewById<RecyclerView>(R.id.combat_attack_recycler)?.adapter?.notifyDataSetChanged()
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