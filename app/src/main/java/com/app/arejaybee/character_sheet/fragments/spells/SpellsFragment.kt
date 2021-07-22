package com.app.arejaybee.character_sheet.fragments.spells

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.Spell
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.abilities.AbilityAdapter
import com.app.arejaybee.character_sheet.fragments.notes.CombatWeaponAdapter
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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = SpellListAdapter(it.rob.spellLists, it)
        }
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
            val title = it.findViewById<EditText>(R.id.spell_dialog_title_edittext)
            val description = it.findViewById<EditText>(R.id.spell_dialog_description_edittext)
            val type = it.findViewById<Spinner>(R.id.spell_dialog_type_spinner)
            Util.buildDialogTypeSpinner(requireContext(), type, R.array.spellLevels)

            if(isEdit && SpellAdapter.selectedSpell != null) {
                title.setText(SpellAdapter.selectedSpell?.name)
                description.setText(SpellAdapter.selectedSpell?.description)
                val index = if(SpellAdapter.selectedSpell!!.level < 0) 0 else SpellAdapter.selectedSpell!!.level
                type.setSelection(index, true)
            }

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val spell = if(isEdit) SpellAdapter.selectedSpell else Spell(0)
                        spell?.name = title.text.toString()
                        spell?.description = description.text.toString()
                        val spellType = type.selectedItem.toString()
                        spell?.level = if(spellType == getString(R.string.cantrips)) 0
                                        else spellType.replace(getString(R.string.spell_level),"").trim().toInt()
                        val index = spell?.level

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