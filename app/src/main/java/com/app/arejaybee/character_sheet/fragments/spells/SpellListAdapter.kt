package com.app.arejaybee.character_sheet.fragments.spells

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.SpellList

class SpellListAdapter(var dataSet: ArrayList<SpellList>, val activity: MainActivity, val parent: View) :
        RecyclerView.Adapter<SpellListAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    companion object {
        var selectedButton: Button? = null
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val spellListBtn: Button = view.findViewById(R.id.adapter_spell_list_button)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_spell_list, viewGroup, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val spellList = dataSet[position]

        viewHolder.spellListBtn.text = if(spellList.level == 0) activity.getString(R.string.cantrips) else "Level ${spellList.level}"

        viewHolder.spellListBtn.setOnClickListener { l ->
            selectedButton?.isSelected = false
            viewHolder.spellListBtn.isSelected = true
            selectedButton = viewHolder.spellListBtn
            val spellList = dataSet[position]

            parent.findViewById<RecyclerView>(R.id.spell_recycler).swapAdapter(SpellAdapter(spellList.spells, activity), true)
            parent.findViewById<EditText>(R.id.adapter_spell_list_cast).setText(spellList.used.toString())
            parent.findViewById<EditText>(R.id.adapter_spell_list_daily).setText(spellList.daily.toString())
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}