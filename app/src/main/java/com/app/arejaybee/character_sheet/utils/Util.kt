package com.app.arejaybee.character_sheet.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.widget.*
import com.app.arejaybee.character_sheet.R
import java.lang.NumberFormatException

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

    fun getNumberFromEditText(edit: EditText) : Int{
        return try {
            edit.text.toString().toInt()
        } catch(err: NumberFormatException) {
            0
        }
    }
 }