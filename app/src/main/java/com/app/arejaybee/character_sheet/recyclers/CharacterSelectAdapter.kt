package com.app.arejaybee.character_sheet.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.PlayerCharacter

class CharacterSelectAdapter(private val dataSet: Array<PlayerCharacter>, val activity: MainActivity) :
        RecyclerView.Adapter<CharacterSelectAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.adapter_character_select_text)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_character_select, viewGroup, false)
        view.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if(view.hasFocus()) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                activity.showMenuItem(R.id.toolbar_delete_btn)
                activity.showMenuItem(R.id.toolbar_email_btn)
            }
            else {
                activity.hideMenuItem(R.id.toolbar_edit_btn)
                activity.hideMenuItem(R.id.toolbar_delete_btn)
                activity.hideMenuItem(R.id.toolbar_email_btn)
            }
        }
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val text = dataSet[position].name + if(dataSet[position].level.isNotEmpty()) " - Lvl "+dataSet[position].level + " "+dataSet[position].characterClass else ""
        viewHolder.textView.text = text
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}