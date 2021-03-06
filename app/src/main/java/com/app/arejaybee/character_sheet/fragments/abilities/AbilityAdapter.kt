package com.app.arejaybee.character_sheet.fragments.abilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Ability

class AbilityAdapter(var dataSet: Array<ArrayList<Ability>?>, val activity: MainActivity) :
        RecyclerView.Adapter<AbilityAdapter.ViewHolder>() {
    companion object {
        var selectedAbility: Ability? = null

    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.ability_list_title)
        val recyclerView: RecyclerView = view.findViewById(R.id.ability_list_items_recycler)
        val dropdownBtn: ImageButton = view.findViewById(R.id.ability_list_dropdown_btn)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_ability_lists, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val abilityList = dataSet[position]
        viewHolder.textView.text = when(position) {
            0 -> activity.getString(R.string.feat)
            1 -> activity.getString(R.string.class_feature)
            2 -> activity.getString(R.string.racial_trait)
            else -> ""
        }
        abilityList?.let {
            viewHolder.recyclerView.layoutManager = LinearLayoutManager(activity)
            viewHolder.recyclerView.adapter = AbilityListAdapter(it, activity)
            viewHolder.dropdownBtn.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
        }

        viewHolder.dropdownBtn.setOnClickListener {
            it.rotation += 180
            it.rotation %= 360
            viewHolder.recyclerView.visibility = if(it.rotation == 0F) View.GONE else View.VISIBLE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}