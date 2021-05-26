package com.app.arejaybee.character_sheet.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.icu.text.CaseMap
import android.view.Gravity
import android.widget.NumberPicker
import android.widget.TextView
import com.app.arejaybee.character_sheet.R

object NumberPickerDialog {
    fun build(context: Activity, title: String, textView: TextView, min: Int) : AlertDialog{
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
        val curValue = textView.text.toString().toInt()
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

        return dialog.create()
    }
}