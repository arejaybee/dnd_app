package com.app.arejaybee.character_sheet.fragments.abilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Ability

class AbilityListAdapter(private val dataSet: ArrayList<Ability>, val activity: MainActivity) :
        RecyclerView.Adapter<AbilityListAdapter.ViewHolder>() {
    var selectedIndex: Int = -1

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.ability_list_item_name)
        val descriptionView: TextView = view.findViewById(R.id.ability_list_item_description)
        val dropdownBtn: ImageButton = view.findViewById(R.id.ability_list_item_dropdown_btn)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_ability_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ability = dataSet[position]
        viewHolder.nameView.text = ability.title
        viewHolder.descriptionView.text = ability.description
        viewHolder.view.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
            selectedIndex = if (focus) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                activity.showMenuItem(R.id.toolbar_delete_btn)
                AbilityAdapter.selectedAbility = ability
                position
            } else {
                activity.hideMenuItem(R.id.toolbar_edit_btn)
                activity.hideMenuItem(R.id.toolbar_delete_btn)
                AbilityAdapter.selectedAbility = null
                -1
            }
        }
        viewHolder.dropdownBtn.visibility = if(ability.description.isEmpty()) View.GONE else View.VISIBLE

        viewHolder.dropdownBtn.setOnClickListener {
            it.rotation += 180
            it.rotation %= 360
            viewHolder.descriptionView.visibility = if(it.rotation == 0F) View.GONE else View.VISIBLE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}