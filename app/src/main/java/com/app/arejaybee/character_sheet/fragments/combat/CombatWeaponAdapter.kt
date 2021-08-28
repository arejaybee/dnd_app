package com.app.arejaybee.character_sheet.fragments.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Note
import com.app.arejaybee.character_sheet.data_objects.Weapon
import com.app.arejaybee.character_sheet.utils.Util

class CombatWeaponAdapter(private val dataSet: ArrayList<Weapon>, val activity: MainActivity) :
        RecyclerView.Adapter<CombatWeaponAdapter.ViewHolder>() {
    companion object {
        var selectedWeapon: Weapon? = null
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.adapter_weapon_name)
        val notes: TextView = view.findViewById(R.id.adapter_weapon_notes)
        val attack: TextView = view.findViewById(R.id.adapter_weapon_attack)
        val damage: TextView = view.findViewById(R.id.adapter_weapon_damage)
        val dropdownBtn: ImageButton = view.findViewById(R.id.adapter_weapon_dropdown_btn)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_combat_weapon, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val weapon = dataSet[position]
        viewHolder.name.text = weapon.name
        viewHolder.notes.text = weapon.notes

        val toHit = weapon.intToHit + activity.rob.getAbilityMod(weapon.abilityType) + if (weapon.isProficient) activity.rob.proficiency else 0
        viewHolder.attack.text = if (toHit >= 0) "+$toHit" else "-${toHit*-1}"

        val d = weapon.intDamage + activity.rob.getAbilityMod(weapon.abilityType)
        val damageRoll = weapon.damage + (if (d >= 0) "+$d" else " - ${d*-1}")
        viewHolder.damage.text = damageRoll

        viewHolder.view.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
            selectedWeapon = if (focus) {
                activity.showMenuItem(R.id.toolbar_edit_btn)
                activity.showMenuItem(R.id.toolbar_delete_btn)
                weapon
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
            viewHolder.notes.visibility = enabled
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateWeapon(weapon: Weapon) {
        dataSet[dataSet.indexOf(weapon)] = weapon
    }
    fun deleteWeapon() {
        activity.rob.weapons.remove(selectedWeapon)
        activity.rob.saveCharacter()
    }
}