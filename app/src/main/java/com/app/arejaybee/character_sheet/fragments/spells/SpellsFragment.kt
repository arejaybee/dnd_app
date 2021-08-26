package com.app.arejaybee.character_sheet.fragments.spells

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.Spell
import com.app.arejaybee.character_sheet.databinding.DialogSpellAttackBinding
import com.app.arejaybee.character_sheet.databinding.DialogSpellDcBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.utils.Util

class SpellsFragment : RobFragment() {
    companion object {
        const val TAG = "SpellsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_spells, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.spell_list_recycler)
        val spellRecycler = view.findViewById<RecyclerView>(R.id.spell_recycler)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
        spellRecycler.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = SpellListAdapter(it.rob.spellLists, it, view)
        }

        val rob = activity?.rob

        val ability = view.findViewById<Spinner>(R.id.spell_ability_spinner)
        val spellAttack = view.findViewById<TextView>(R.id.spet_spellAttack)
        val spellDC = view.findViewById<TextView>(R.id.spet_spellDC)

        Util.buildDialogTypeSpinner(requireContext(), ability, R.array.abilities)

        val selectionArray = resources.getStringArray(R.array.abilities)
        val index = if(selectionArray.toList().indexOf(activity?.rob?.spellAbility) < 0) 0
                    else selectionArray.toList().indexOf(activity?.rob?.spellAbility)
        ability.setSelection(index, true)
        ability.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                rob?.spellAbility = selectionArray[position]
                rob?.saveCharacter()
                spellAttack.text = calculateSpellAttack()
                spellDC.text = calculateSpellDC()
            }
        }

        view.findViewById<View>(R.id.spet_spellAttack).setOnClickListener {
            showSpellAttackDialog()
        }
        view.findViewById<View>(R.id.spet_spellDC).setOnClickListener {
            showSpellDCDialog()
        }

        view.findViewById<TextView>(R.id.spet_spellAttack).text = calculateSpellAttack()
        view.findViewById<TextView>(R.id.spet_spellDC).text = calculateSpellDC()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickAdd() { showSpellDialog(false) }

    override fun onClickEdit() { showSpellDialog(true) }

    private fun showSpellDialog(isEdit: Boolean) {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_spell, null)
        dialogView?.let {
            val effect = it.findViewById<EditText>(R.id.spell_dialog_effect)
            val name = it.findViewById<EditText>(R.id.spell_dialog_name)
            val level = it.findViewById<Spinner>(R.id.spell_dialog_level)

            Util.buildDialogTypeSpinner(requireContext(), level, R.array.spell_levels)

            if(isEdit) {
                SpellAdapter.selectedSpell?.let { selected ->
                    val index = if (selected.level < 0) 0 else selected.level
                    level.setSelection(index, true)
                    effect.setText(selected.effect)
                    name.setText(selected.name)
                }
            }

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val spell = if(isEdit) SpellAdapter.selectedSpell else Spell(0)
                        val spellType = level.selectedItem.toString()
                        spell?.level = if(spellType == getString(R.string.cantrips)) 0
                                        else spellType.replace(getString(R.string.spell_level),"").trim().toInt()
                        val index = spell?.level

                        spell?.name = name.text.toString()
                        spell?.effect = effect.text.toString()

                        if(isEdit && index != null) {
                            spell.let{ editSpell ->
                                val rob = activity?.rob
                                if(rob != null) {
                                    if (rob.spellLists[index].spells.contains(SpellAdapter.selectedSpell)) {
                                        val aIndex = rob.spellLists[index].spells.indexOf(SpellAdapter.selectedSpell)
                                        rob.spellLists[index].spells[aIndex] = editSpell
                                    }
                                    else {
                                        rob.spellLists.map { list ->
                                            if(list.spells.contains(SpellAdapter.selectedSpell)) {
                                                list.spells.remove(SpellAdapter.selectedSpell)
                                            }
                                        }
                                        rob.spellLists[index].spells.add(editSpell)
                                    }
                                }
                            }
                        }
                        else {
                            index?.let { activity?.rob?.spellLists?.get(index)?.spells?.add(spell) }
                        }
                        view?.findViewById<RecyclerView>(R.id.spell_list_recycler)?.adapter?.notifyDataSetChanged()
                        activity?.rob?.saveCharacter()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }
    }

    private fun showSpellAttackDialog() {
        val binding: DialogSpellAttackBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.dialog_spell_attack, null, false)
        val dialogView: View = binding.root
        binding.rob = activity?.rob

        dialogView?.findViewById<EditText>(R.id.dialog_spell_attack_bonus).doOnTextChanged { text, start, count, after ->
            if (text.isNullOrEmpty()) {
                activity?.rob?.spellAttackBonus = 0
            }
            else {
                activity?.rob?.spellAttackBonus = text.toString().toInt()
            }
        }

        dialogView?.let {

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setTitle(getString(R.string.spell_attack))
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        this.view?.findViewById<TextView>(R.id.spet_spellAttack)?.text = calculateSpellAttack()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative){ dialog: DialogInterface, i:Int ->
                        dialog.dismiss()
                    }
                    .show()

        }
    }

    private fun showSpellDCDialog() {
        val binding: DialogSpellDcBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.dialog_spell_dc, null, false)
        val dialogView: View = binding.root
        binding.rob = activity?.rob

        dialogView?.findViewById<EditText>(R.id.dialog_spell_dc_bonus).doOnTextChanged { text, start, count, after ->
            if (text.isNullOrEmpty()) {
                activity?.rob?.spellDCBonus = 0
            }
            else {
                activity?.rob?.spellDCBonus = text.toString().toInt()
            }
        }

        dialogView?.let {

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setTitle(getString(R.string.spell_dc))
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        this.view?.findViewById<TextView>(R.id.spet_spellDC)?.text = calculateSpellDC()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative){ dialog: DialogInterface, i:Int ->
                        dialog.dismiss()
                    }
                    .show()

        }
    }

    private fun calculateSpellAttack(): String {
        val rob = activity?.rob
        rob?.let {
            return (rob.getModifier(rob.spellAbility) + rob.proficiency + rob.spellAttackBonus).toString()
        }
        return "0"
    }

    private fun calculateSpellDC(): String {
        val rob = activity?.rob
        rob?.let {
            return (8 + rob.getModifier(rob.spellAbility) + rob.proficiency + rob.spellDCBonus).toString()
        }
        return "0"
    }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.spell_list_recycler)?.adapter
        SpellAdapter.selectedSpell?.let {
            activity?.rob?.spellLists?.let { spellLists ->
                spellLists.map { list ->
                    list.spells.remove(it)
                }
                (adapter as SpellListAdapter).dataSet = spellLists
            }
            adapter?.notifyDataSetChanged()
            activity?.rob?.saveCharacter()
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