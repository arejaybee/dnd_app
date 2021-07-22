package com.app.arejaybee.character_sheet.fragments.spells

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Spell
import com.app.arejaybee.character_sheet.data_objects.SpellList

class SpellAdapter(private val dataSet: ArrayList<Spell>, val activity: MainActivity) :
        RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    companion object {
        var selectedSpell: Spell? = null
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val spellName: TextView = view.findViewById(R.id.adapter_spell_name)
        val spellDescription: TextView = view.findViewById(R.id.adapter_spell_description)
        val spellToggle: ImageButton = view.findViewById(R.id.adapter_spell_dropdown_btn)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_spell, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val spellList = dataSet[position]
        viewHolder.view.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
            selectedSpell = if(focus) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                activity.showMenuItem(R.id.toolbar_delete_btn)
                dataSet[position]
            } else {
                activity.hideMenuItem(R.id.toolbar_edit_btn)
                activity.hideMenuItem(R.id.toolbar_delete_btn)
                null
            }
        }
        viewHolder.spellName.text = spellList.name
        viewHolder.spellDescription.text = spellList.description
        viewHolder.spellDescription.visibility = View.GONE

        viewHolder.spellToggle.setOnClickListener {
            it.rotation += 180
            it.rotation %= 360
            val enabled = if(it.rotation == 0F) View.GONE else View.VISIBLE
            viewHolder.spellDescription.visibility = enabled
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}