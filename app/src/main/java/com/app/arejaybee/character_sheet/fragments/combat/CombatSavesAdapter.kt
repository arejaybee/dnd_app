package com.app.arejaybee.character_sheet.fragments.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
import com.app.arejaybee.character_sheet.data_objects.Note
import com.app.arejaybee.character_sheet.data_objects.SavingThrow
import com.app.arejaybee.character_sheet.data_objects.Weapon
import com.app.arejaybee.character_sheet.utils.Util

class CombatSavesAdapter(private val dataSet: ArrayList<SavingThrow>, val activity: MainActivity) :
        RecyclerView.Adapter<CombatSavesAdapter.ViewHolder>() {
    companion object {
        var selectedSave: SavingThrow? = null
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.adapter_save_name)
        val mod: TextView = view.findViewById(R.id.adapter_save_mod)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_combat_save, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val save = dataSet[position]
        val rob = activity.rob
        viewHolder.name.text = save.name
        viewHolder.mod.text = rob.getSaveMod(save.name).toString()

        viewHolder.view.setOnClickListener {
            val inflater = activity.layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_save, null)

            val bonus = dialogView.findViewById<EditText>(R.id.save_dialog_bonus)
            val proficiency = dialogView.findViewById<Spinner>(R.id.save_dialog_proficiency)

            Util.buildDialogTypeSpinner(activity, proficiency, R.array.proficiencies)

            bonus.setText(save.bonus.toString())
            val profIndex = save.proficiency.ordinal
            proficiency.setSelection(profIndex, true)
            proficiency.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    save.proficiency = EnumHelper.PROFICIENCY.values()[position]
                    rob.saves[rob.saves.indexOf(save)] = save //indexOf finds the skill by name, then we replace with the updated object
                }
            }

            bonus.addTextChangedListener {
                save.bonus = Util.getNumberFromEditText(bonus)
                rob.saves[rob.saves.indexOf(save)] = save //indexOf finds the skill by name, then we replace with the updated object
            }

            AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setTitle(save.name)
                    .setView(dialogView)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                        rob.saveCharacter()
                        notifyDataSetChanged()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                    }
                    .show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateSave(save: SavingThrow) {
        dataSet[dataSet.indexOf(save)] = save
    }
}