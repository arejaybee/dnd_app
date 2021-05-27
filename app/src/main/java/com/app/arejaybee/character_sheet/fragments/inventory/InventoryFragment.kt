package com.app.arejaybee.character_sheet.fragments.inventory

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
        /*val recyclerView = view.findViewById<RecyclerView>(R.id.inventory_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = InventoryAdapter(it.rob.items, it)
        }*/
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.inventory_recycler)?.adapter as InventoryAdapter
        adapter.deleteInventory()
        adapter.notifyDataSetChanged()
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