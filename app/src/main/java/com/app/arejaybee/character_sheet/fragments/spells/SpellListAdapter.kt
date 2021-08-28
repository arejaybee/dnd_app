package com.app.arejaybee.character_sheet.fragments.spells

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.SpellList
import com.app.arejaybee.character_sheet.utils.Util
import org.w3c.dom.Text

class SpellListAdapter(var dataSet: ArrayList<SpellList>, val activity: MainActivity, val parent: View) :
        RecyclerView.Adapter<SpellListAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    companion object {
        var selectedButton: Button? = null
        var usedWatcher: TextWatcher? = null
        var dailyWatcher: TextWatcher? = null
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
        val spellList = dataSet[viewHolder.absoluteAdapterPosition]
        viewHolder.spellListBtn.text = if(spellList.level == 0) activity.getString(R.string.cantrips) else "Level ${spellList.level}"

        val casts = parent.findViewById<EditText>(R.id.spell_used)
        val daily = parent.findViewById<EditText>(R.id.spell_daily)

        viewHolder.spellListBtn.setOnClickListener { l ->
            selectedButton?.isSelected = false

            viewHolder.spellListBtn.isSelected = true
            selectedButton = viewHolder.spellListBtn

            parent.findViewById<RecyclerView>(R.id.spell_recycler).swapAdapter(SpellAdapter(spellList.spells, activity), true)

            usedWatcher?.let {
                casts.removeTextChangedListener(it)
            }
            dailyWatcher?.let {
                daily.removeTextChangedListener(it)
            }

            casts.setText(spellList.used.toString())
            daily.setText(spellList.daily.toString())

            usedWatcher = object:TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    activity.rob.spellLists[viewHolder.absoluteAdapterPosition].used =
                        Util.getNumberFromEditText(casts)
                    activity.rob.saveCharacter()
                }

            }
            dailyWatcher = object:TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    activity.rob.spellLists[viewHolder.absoluteAdapterPosition].daily =
                        Util.getNumberFromEditText(daily)
                    activity.rob.saveCharacter()
                }
            }
            casts.addTextChangedListener(usedWatcher)
            daily.addTextChangedListener(dailyWatcher)
        }

        if(selectedButton != null && viewHolder.spellListBtn.text == selectedButton?.text) {
            viewHolder.spellListBtn.performClick()
        }

        else if(spellList.level == 0 && selectedButton == null) {
            viewHolder.spellListBtn.performClick()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}