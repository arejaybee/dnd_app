package com.app.arejaybee.character_sheet.fragments.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
        val name: TextView = view.findViewById(R.id.adapter_inventory_item_name)
        val description: TextView = view.findViewById(R.id.adapter_inventory_item_description)
        val weight: TextView = view.findViewById(R.id.adapter_inventory_weight)
        val cost: TextView = view.findViewById(R.id.adapter_inventory_cost)
        val slot: TextView = view.findViewById(R.id.adapter_inventory_slot)
        val dropdownBtn: ImageButton = view.findViewById(R.id.adapter_inventory_item_dropdown_btn)
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
        viewHolder.name.text = item.name
        viewHolder.description.text = item.description
        val weightString = "Weight: " + item.weight
        val costString = "Cost: " + item.cost
        val slotString = "Slot: " + item.slot.name
        viewHolder.weight.text = weightString
        viewHolder.cost.text = costString
        viewHolder.slot.text = slotString

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

        viewHolder.dropdownBtn.setOnClickListener {
            it.rotation += 180
            it.rotation %= 360
            val enabled = if(it.rotation == 0F) View.GONE else View.VISIBLE
            viewHolder.description.visibility = enabled
            viewHolder.weight.visibility = enabled
            viewHolder.cost.visibility = enabled
            viewHolder.slot.visibility = enabled
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun deleteInventory() {
        activity.rob.items.remove(selectedInventory)
        activity.rob.saveCharacter()
    }
}