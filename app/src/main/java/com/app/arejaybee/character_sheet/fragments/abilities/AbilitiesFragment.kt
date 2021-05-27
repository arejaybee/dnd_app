package com.app.arejaybee.character_sheet.fragments.abilities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Ability
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.utils.Util

class AbilitiesFragment : RobFragment() {
    companion object {
        const val TAG = "AbilitiesFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ability, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.ability_list_recycler)
        val abilities = activity?.rob?.abilities


        abilities?.let {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = AbilityAdapter(it, activity as MainActivity)
        }
    }

    override fun onClickAdd() { showAbilityDialog(false) }

    override fun onClickEdit() { showAbilityDialog(true) }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.ability_list_recycler)?.adapter
        AbilityAdapter.selectedAbility?.let {
            activity?.rob?.abilities?.let { array ->
                array.map { list ->
                    list?.remove(it)
                }
                (adapter as AbilityAdapter).dataSet = array
            }
            adapter?.notifyDataSetChanged()
            activity?.rob?.saveCharacter()
        }
    }

    private fun showAbilityDialog(isEdit: Boolean) {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_ability, null)
        dialogView?.let {
            val title = it.findViewById<EditText>(R.id.ability_dialog_title_edittext)
            val description = it.findViewById<EditText>(R.id.ability_dialog_description_edittext)
            val type = it.findViewById<Spinner>(R.id.ability_dialog_type_spinner)
            Util.buildDialogTypeSpinner(requireContext(), type, R.array.abilityTypes)
            if(isEdit) {
                title.setText(AbilityAdapter.selectedAbility?.title)
                description.setText(AbilityAdapter.selectedAbility?.description)
                val selectionArray = resources.getStringArray(R.array.abilityTypes)
                val index = if(selectionArray.toList().indexOf(AbilityAdapter.selectedAbility?.type) < 0) 0 else selectionArray.toList().indexOf(AbilityAdapter.selectedAbility?.type)
                type.setSelection(index, true)
            }
            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val ability = if(isEdit) AbilityAdapter.selectedAbility else Ability()
                        ability?.title = title.text.toString()
                        ability?.description = description.text.toString()
                        val abilityType = type.selectedItem.toString()
                        ability?.type = abilityType
                        val index = when(abilityType) {
                            getString(R.string.feat) -> 0
                            getString(R.string.class_feature) -> 1
                            getString(R.string.racial_trait) -> 2
                            else -> -1
                        }
                        if(isEdit) {
                            ability?.let{ editAbility ->
                                val rob = activity?.rob
                                if(rob != null) {
                                    if (rob.abilities[index]?.contains(AbilityAdapter.selectedAbility) == true) {
                                        val aIndex = rob.abilities[index]?.indexOf(AbilityAdapter.selectedAbility)!!
                                        rob.abilities[index]?.set(aIndex, editAbility)
                                    }
                                    else {
                                        rob.abilities.map { list ->
                                            if(list?.contains(AbilityAdapter.selectedAbility) == true) {
                                                list?.remove(AbilityAdapter.selectedAbility)
                                            }
                                        }
                                        rob.abilities[index]?.add(editAbility)
                                    }
                                }
                            }
                        }
                        else {
                            activity?.rob?.abilities?.get(index)?.add(ability!!)
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
        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.showMenuItem(R.id.toolbar_home_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)
    }
}