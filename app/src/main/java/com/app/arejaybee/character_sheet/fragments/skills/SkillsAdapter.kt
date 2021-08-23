package com.app.arejaybee.character_sheet.fragments.skills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Skill

class SkillsAdapter(private val dataSet: ArrayList<Skill>, val activity: MainActivity) :
        RecyclerView.Adapter<SkillsAdapter.ViewHolder>() {
    var selectedIndex: Int = -1

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val skillName: TextView = view.findViewById(R.id.skill_item_name)
        val skillAbility: TextView = view.findViewById(R.id.skill_item_ability)
        val skillMod: TextView = view.findViewById(R.id.skill_item_mod)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_skills, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val skill = dataSet[position]
        if(skill.proficiency.ordinal > 0) {
            activity.runOnUiThread {
                viewHolder.view.setBackgroundResource(R.drawable.selection_background_enabled)
            }
        }
        viewHolder.view.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
            selectedIndex = if(focus) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                position
            } else {
                activity.hideMenuItem(R.id.toolbar_edit_btn)
                -1
            }
        }
        viewHolder.skillName.text = skill.name
        viewHolder.skillAbility.text = skill.ability
        viewHolder.skillMod.text = activity.rob.getSkillMod(skill).toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun getSkill() : Skill {
        return dataSet[selectedIndex]
    }
}