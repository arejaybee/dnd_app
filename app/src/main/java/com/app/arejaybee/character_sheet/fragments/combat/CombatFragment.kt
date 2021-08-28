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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.ArmorClass
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
import com.app.arejaybee.character_sheet.data_objects.Weapon
import com.app.arejaybee.character_sheet.databinding.FragmentCombat5eBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.notes.CombatSavesAdapter
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
        val attackRecyclerView = view.findViewById<RecyclerView>(R.id.combat_attack_recycler)
        val savesRecyclerView = view.findViewById<RecyclerView>(R.id.combat_saves_recycler)
        attackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        savesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        activity?.let {
            attackRecyclerView.adapter = CombatWeaponAdapter(it.rob.weapons, it)
            savesRecyclerView.adapter = CombatSavesAdapter(it.rob.saves, it)
        }

        val proficiency = view.findViewById<TextView>(R.id.combat_proficiency)

        proficiency.addTextChangedListener {
            attackRecyclerView.adapter?.notifyDataSetChanged()
            savesRecyclerView.adapter?.notifyDataSetChanged()
        }

        view.findViewById<TextView>(R.id.combat_initiative_field).setOnClickListener {
            showInitiativeDialog()
        }
        view.findViewById<TextView>(R.id.combat_armor_field).setOnClickListener {
            showArmorDialog()
        }
        view.findViewById<ImageButton>(R.id.combat_saves_toggle_btn).setOnClickListener {
            it.rotation += 180
            it.rotation %= 360
            val enabled = if(it.rotation == 0F) View.GONE else View.VISIBLE
            view.findViewById<View>(R.id.combat_save_section).visibility = enabled
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
            val attackBonus = it.findViewById<EditText>(R.id.weapon_dialog_attack_bonus)
            val damageBonus = it.findViewById<EditText>(R.id.weapon_dialog_damage_bonus)
            val abilityType = it.findViewById<Spinner>(R.id.weapon_dialog_stat_type)
            val prof: CheckBox = it.findViewById(R.id.weapon_dialog_proficient)

            //Initialize spinners
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
                attackBonus.setText(weapon.intToHit.toString())
                damageBonus.setText(weapon.intDamage.toString())
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
                        weapon?.intToHit = Util.getNumberFromEditText(damageBonus)
                        weapon?.intDamage = Util.getNumberFromEditText(damageBonus)
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
            Util.buildDialogTypeSpinner(requireContext(), prof, R.array.proficiencies)

            //Set a default selection for spinners
            prof.setSelection(activity?.rob?.initiativeProficiency?.ordinal!!, true)

            bonus.text = activity?.rob?.initiativeBonus.toString()
            dex.text = activity?.rob?.dexMod.toString()

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setTitle(getText(R.string.initiative))
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
            val armor = it.findViewById<EditText>(R.id.armor_dialog_armor)
            val shield = it.findViewById<EditText>(R.id.armor_dialog_shield)
            val dex = it.findViewById<TextView>(R.id.armor_dialog_dex)
            val type = it.findViewById<Spinner>(R.id.armor_dialog_armor_type)
            val bonus = it.findViewById<EditText>(R.id.armor_dialog_bonus)

            //Initialize spinners
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

            bonus.setText(rob.armorClass.bonus.toString())
            dex.text = rob.armorClass.maxDexMod.toString()
            shield.setText(rob.armorClass.shield.toString())
            armor.setText(rob.armorClass.armor.toString())

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setTitle(getText(R.string.armor_class))
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        //Set the object to the values in the dialog
                        val ac = ArmorClass(dex.text.toString().toInt() * 2 + 10)
                        ac.type = ArmorClass.ArmorType.values()[type.selectedItemPosition]
                        ac.bonus = Util.getNumberFromEditText(bonus)
                        ac.shield = Util.getNumberFromEditText(shield)
                        ac.armor = Util.getNumberFromEditText(armor)
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