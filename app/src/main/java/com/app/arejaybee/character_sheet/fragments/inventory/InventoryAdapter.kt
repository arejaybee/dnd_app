package com.app.arejaybee.character_sheet.fragments.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.InventoryItem

class InventoryAdapter(private val dataSet: ArrayList<InventoryItem>, val activity: MainActivity) :
        RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    companion object {
        var selectedInventory: InventoryItem? = null
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.adapter_inventory_item_name)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_inventory, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.textView.text = item.name
        viewHolder.view.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
            selectedInventory = if(focus) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                activity.showMenuItem(R.id.toolbar_delete_btn)
                item
            } else {
                activity.hideMenuItem(R.id.toolbar_edit_btn)
                activity.hideMenuItem(R.id.toolbar_delete_btn)
                null
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun deleteInventory() {
        activity.rob.items.remove(selectedInventory)
        activity.rob.saveCharacter()
    }
}