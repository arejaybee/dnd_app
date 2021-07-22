package com.app.arejaybee.character_sheet.fragments.spells

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.SpellList

class SpellListAdapter(var dataSet: ArrayList<SpellList>, val activity: MainActivity) :
        RecyclerView.Adapter<SpellListAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val spellName: TextView = view.findViewById(R.id.adapter_spell_list_name)
        val spellsCast: TextView = view.findViewById(R.id.adapter_spell_list_cast)
        val spellsDaily: TextView = view.findViewById(R.id.adapter_spell_list_daily)
        val grid: GridLayout = view.findViewById(R.id.adapter_spell_list_grid)
        val dropdownBtn: ImageButton = view.findViewById(R.id.adapter_spell_list_toggle)
        val recycler: RecyclerView = view.findViewById(R.id.spell_recycler)
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

        viewHolder.spellName.text = if(spellList.level == 0) activity.getString(R.string.cantrips) else "Level ${spellList.level}"
        viewHolder.spellsCast.text = spellList.used.toString()
        viewHolder.spellsDaily.text = spellList.daily.toString()

        viewHolder.recycler.layoutManager = LinearLayoutManager(activity)
        viewHolder.recycler.adapter = SpellAdapter(spellList.spells, activity)

        viewHolder.dropdownBtn.setOnClickListener {
            it.rotation += 180
            it.rotation %= 360
            val enabled = if(it.rotation == 0F) View.GONE else View.VISIBLE
            viewHolder.recycler.visibility = enabled
            viewHolder.grid.visibility = enabled
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}