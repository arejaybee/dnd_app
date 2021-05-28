package com.app.arejaybee.character_sheet.fragments.combat

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.ArmorClass
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
import com.app.arejaybee.character_sheet.data_objects.Weapon
import com.app.arejaybee.character_sheet.databinding.FragmentCombat5eBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.notes.CombatWeaponAdapter
import com.app.arejaybee.character_sheet.utils.Util
import kotlin.math.abs
import kotlin.math.floor

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

        view.findViewById<TextView>(R.id.combat_initiative_field).setOnClickListener {
            showInitiativeDialog()
        }
        view.findViewById<TextView>(R.id.combat_armor_field).setOnClickListener {
            showArmorDialog()
        }
    }

    override fun onClickAdd() { showWeaponDialog(false) }

    override fun onClickEdit() { showWeaponDialog(true) }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.let {
            it.showNavigation(TAG)
            updateArmorField()
            updateInitiativeField()
        }
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
            //Grab items from the view
            val name = it.findViewById<EditText>(R.id.weapon_dialog_title_edittext)
            val notes = it.findViewById<EditText>(R.id.weapon_dialog_description_edittext)
            val type = it.findViewById<EditText>(R.id.weapon_dialog_type)
            val damageDice = it.findViewById<EditText>(R.id.weapon_dialog_damage_roll)
            val range = it.findViewById<EditText>(R.id.weapon_dialog_attack_range)
            val attackBonus = it.findViewById<TextView>(R.id.weapon_dialog_attack_bonus)
            val damageBonus = it.findViewById<TextView>(R.id.weapon_dialog_damage_bonus)
            val abilityType = it.findViewById<Spinner>(R.id.weapon_dialog_stat_type)
            val prof: CheckBox = it.findViewById(R.id.weapon_dialog_proficient)

            //Initialize spinners
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.attack_bonus), attackBonus, -99)
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.damage_bonus), damageBonus, -99)
            Util.buildDialogTypeSpinner(requireContext(), abilityType, R.array.abilities)

            //Set a default selection for spinners
            val selectionArray = resources.getStringArray(R.array.abilities)
            val index = if(selectionArray.toList().indexOf(CombatWeaponAdapter.selectedWeapon?.abilityType) < 0) 0
            else selectionArray.toList().indexOf(CombatWeaponAdapter.selectedWeapon?.abilityType)
            abilityType.setSelection(index, true)

            //create the object if isEdit
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
                        //Set the object to the values in the dialog
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

                        //update the object on Rob
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

                        //save rob, update the recycler
                        activity?.rob?.saveCharacter()
                        view?.findViewById<RecyclerView>(R.id.combat_attack_recycler)?.adapter?.notifyDataSetChanged()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }
    }

    private fun showInitiativeDialog() {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_initiative, null)
        dialogView?.let {
            //Grab items from the view
            val bonus = it.findViewById<TextView>(R.id.initiative_dialog_bonus)
            val dex = it.findViewById<TextView>(R.id.initiative_dialog_dex)
            val prof = it.findViewById<Spinner>(R.id.initiative_dialog_proficiency)

            //Initialize spinners
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.bonus), bonus, -99)
            Util.buildDialogTypeSpinner(requireContext(), prof, R.array.proficiencies)

            //Set a default selection for spinners
            prof.setSelection(activity?.rob?.initiativeProficiency?.ordinal!!, true)

            bonus.text = activity?.rob?.initiativeBonus.toString()
            dex.text = activity?.rob?.dexMod.toString()

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setTitle("Initiative")
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        //Set the object to the values in the dialog
                        val rob = activity?.rob
                        rob?.initiativeBonus = bonus.text.toString().toInt()
                        rob?.initiativeProficiency = EnumHelper.PROFICIENCY.values()[prof.selectedItemPosition]

                        //save rob, update the recycler
                        activity?.rob?.saveCharacter()
                        updateInitiativeField()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, i: Int ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }
    }

    private fun showArmorDialog() {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_armor, null)
        val rob = activity?.rob!!
        dialogView?.let {
            //Grab items from the view
            val armor = it.findViewById<TextView>(R.id.armor_dialog_armor)
            val shield = it.findViewById<TextView>(R.id.armor_dialog_shield)
            val dex = it.findViewById<TextView>(R.id.armor_dialog_dex)
            val type = it.findViewById<Spinner>(R.id.armor_dialog_armor_type)
            val bonus = it.findViewById<TextView>(R.id.armor_dialog_bonus)

            //Initialize spinners
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.bonus), bonus, -99)
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.armor_bonus), armor, -99)
            Util.addNumberSpinnerToView(requireActivity(), getString(R.string.shield_bonus), shield, -99)
            Util.buildDialogTypeSpinner(requireContext(), type, R.array.armor_types)
            type?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val ac = rob.armorClass
                    ac.type = ArmorClass.ArmorType.values()[position]
                    dex.text = ac.maxDexMod.toString()
                }
            }

            //Set a default selection for spinners
            type.setSelection(rob.armorClass.type.ordinal, true)

            bonus.text = rob.armorClass.bonus.toString()
            dex.text = rob.armorClass.maxDexMod.toString()
            shield.text = rob.armorClass.shield.toString()
            armor.text = rob.armorClass.armor.toString()

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setTitle("Armor Class")
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        //Set the object to the values in the dialog
                        val ac = ArmorClass(dex.text.toString().toInt() * 2 + 10)
                        ac.type = ArmorClass.ArmorType.values()[type.selectedItemPosition]
                        ac.bonus = bonus.text.toString().toInt()
                        ac.shield = shield.text.toString().toInt()
                        ac.armor = armor.text.toString().toInt()
                        activity?.rob?.armorClass = ac
                        val x = activity?.rob!!

                        //save rob, update the recycler
                        activity?.rob?.saveCharacter()
                        updateArmorField()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, i: Int ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }
    }

    private fun updateInitiativeField() {
        val rob = activity?.rob!!
        val profValue = when(rob.initiativeProficiency) {
            EnumHelper.PROFICIENCY.HALF -> floor(rob.proficiency/2.0).toInt()
            EnumHelper.PROFICIENCY.NORMAL -> rob.proficiency
            EnumHelper.PROFICIENCY.DOUBLE -> rob.proficiency * 2
            else -> 0
        }
        val init = rob.dexMod + rob.initiativeBonus + profValue
        view?.findViewById<TextView>(R.id.combat_initiative_field)?.text = init.toString()
    }
    private fun updateArmorField() {
        val rob = activity?.rob!!
        view?.findViewById<TextView>(R.id.combat_armor_field)?.text = rob.armorClass.mod.toString()
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