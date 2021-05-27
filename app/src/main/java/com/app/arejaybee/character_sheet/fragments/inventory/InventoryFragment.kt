package com.app.arejaybee.character_sheet.fragments.inventory

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.InventoryItem
import com.app.arejaybee.character_sheet.databinding.FragmentDescriptionBinding
import com.app.arejaybee.character_sheet.databinding.FragmentInventoryBinding
import com.app.arejaybee.character_sheet.databinding.FragmentInventoryBindingImpl
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.items.InventoryAdapter
import com.app.arejaybee.character_sheet.utils.Util
import kotlin.math.cos


class InventoryFragment : RobFragment() {
    companion object {
        const val TAG = "InventoryFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentInventoryBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_inventory, container, false)
        val view: View = binding.root
        binding.rob = activity?.rob
        binding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.inventory_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = InventoryAdapter(it.rob.items, it)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
        view?.findViewById<TextView>(R.id.inventory_weight)?.text = activity?.rob?.inventoryWeight.toString()
    }

    override fun onClickAdd() { showNoteDialog(false) }

    override fun onClickEdit() { showNoteDialog(true) }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.inventory_recycler)?.adapter as InventoryAdapter
        adapter.deleteInventory()
        adapter.notifyDataSetChanged()
        view?.findViewById<TextView>(R.id.inventory_weight)?.text = activity?.rob?.inventoryWeight.toString()
    }

    private fun setupToolbar() {
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)

        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.showMenuItem(R.id.toolbar_home_btn)
    }


    private fun showNoteDialog(isEdit: Boolean) {
        val item = InventoryAdapter.selectedInventory
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_inventory_item, null)
        dialogView?.let {
            val name = it.findViewById<EditText>(R.id.inventory_dialog_name)
            val description = it.findViewById<EditText>(R.id.inventory_dialog_description)
            val weight = it.findViewById<EditText>(R.id.inventory_dialog_weight)
            val cost = it.findViewById<EditText>(R.id.inventory_dialog_cost)
            val slot = it.findViewById<Spinner>(R.id.inventory_dialog_slot)
            Util.buildDialogTypeSpinner(requireContext(), slot, R.array.itemSlots)
            if(isEdit) {
                name.setText(item?.name)
                description.setText(item?.description)
                weight.setText(item?.weight.toString())
                cost.setText(item?.cost)
            }

            val index = if(item?.slot == null) 0 else item.slot.ordinal
            slot.setSelection(index, true)

            AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val item = if(isEdit) InventoryAdapter.selectedInventory else InventoryItem()
                        item?.name = name.text.toString()
                        item?.description = description.text.toString()
                        item?.weight = weight.text.toString().toInt()
                        item?.cost = cost.text.toString()
                        val slotValue = InventoryItem.SlotEnum.values().find { sVal -> sVal.name == slot.selectedItem.toString() }
                        item?.slot = slotValue ?: InventoryItem.SlotEnum.None
                        if(isEdit) {
                            item?.let{ editItem ->
                                val rob = activity?.rob
                                if(rob != null) {
                                    val aIndex = rob.items.indexOf(InventoryAdapter.selectedInventory)
                                    rob.items[aIndex] = editItem
                                }
                            }
                        }
                        else {
                            activity?.rob?.items?.add(item!!)
                        }
                        view?.findViewById<RecyclerView>(R.id.inventory_recycler)?.adapter?.notifyDataSetChanged()
                        activity?.rob?.saveCharacter()

                        view?.findViewById<TextView>(R.id.inventory_weight)?.text = activity?.rob?.inventoryWeight.toString()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        }
    }
}