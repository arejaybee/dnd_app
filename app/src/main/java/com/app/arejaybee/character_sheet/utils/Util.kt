package com.app.arejaybee.character_sheet.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
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
}