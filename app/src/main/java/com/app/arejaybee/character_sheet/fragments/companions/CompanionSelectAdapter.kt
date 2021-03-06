package com.app.arejaybee.character_sheet.fragments.select_character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.CompanionCharacter
import com.app.arejaybee.character_sheet.data_objects.PlayerCharacter
import com.app.arejaybee.character_sheet.fragments.companions.CompanionFragment
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil
import com.app.arejaybee.character_sheet.utils.Strings

class CompanionSelectAdapter(private val dataSet: ArrayList<CompanionCharacter>, val activity: MainActivity) :
        RecyclerView.Adapter<CompanionSelectAdapter.ViewHolder>() {
    var selectedIndex: Int = -1

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.adapter_character_select_text)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_character_select, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val rob = dataSet[position]
        val text = rob.name + if(rob.level.isNotEmpty()) " - Lvl "+rob.level + " "+rob.characterClass else ""
        viewHolder.textView.text = text
        viewHolder.view.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
            selectedIndex = if(focus) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                activity.showMenuItem(R.id.toolbar_delete_btn)
                position
            } else {
                activity.hideMenuItem(R.id.toolbar_edit_btn)
                activity.hideMenuItem(R.id.toolbar_delete_btn)
                -1
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun getCharacter() : CompanionCharacter {
        return dataSet[selectedIndex]
    }
    fun deleteSelectedCompanion() {
        val companion = dataSet[selectedIndex] as CompanionCharacter
        companion.owner.companions.remove(companion)
        companion.owner.saveCharacter()
    }
}