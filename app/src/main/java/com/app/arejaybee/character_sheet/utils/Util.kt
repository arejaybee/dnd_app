package com.app.arejaybee.character_sheet.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import com.app.arejaybee.character_sheet.R

object Util {
    fun buildDialogTypeSpinner(context: Context, spinner: Spinner, arrayResource: Int) {
        ArrayAdapter.createFromResource(
                context,
                arrayResource,
                R.layout.centered_spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    fun addNumberSpinnerToView(context: Activity, title: String, textView: TextView, min: Int, onDimiss: DialogInterface.OnDismissListener? = null) {
        textView.setOnClickListener {
            val dialog = buildNumberSpinner(context, title, textView, min)
            if(onDimiss != null) {
                dialog.setOnDismissListener(onDimiss)
            }
            dialog.show()
        }
    }

    fun buildNumberSpinner(context: Activity, title: String, textView: TextView, min: Int, onDimiss: DialogInterface.OnDismissListener? = null) : AlertDialog {
        val dialog = AlertDialog.Builder(context)
        val inflater = context.layoutInflater
        val view = inflater.inflate(R.layout.dialog_number_picker, null)
        val tvTitle = TextView(context)
        tvTitle.text = title
        tvTitle.gravity = Gravity.CENTER_HORIZONTAL
        tvTitle.textSize = 20F
        dialog.setCustomTitle(tvTitle)
        val numberPicker = view.findViewById<NumberPicker>(R.id.dialog_number_picker)
        val max = 99
        val curValue = if(textView.text.toString().isEmpty()) 0 else textView.text.toString().toInt()
        numberPicker.maxValue = max - min
        numberPicker.minValue = 0
        numberPicker.value = curValue - min
        numberPicker.wrapSelectorWheel = false
        numberPicker.setFormatter {
            (it + min).toString()
        }

        dialog.setPositiveButton("Done") { d: DialogInterface, index: Int ->
            textView.text = (numberPicker.value + min).toString()
        }
        dialog.setView(view)

        if(onDimiss != null) {
            dialog.setOnDismissListener(onDimiss)
        }

        return dialog.create()
    }
}