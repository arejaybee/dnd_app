package com.app.arejaybee.character_sheet.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.arejaybee.character_sheet.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickMenu(view: View) {
        when(view.id) {
            R.id.toolbar_add_btn -> onClickAdd()
        }
    }

    fun onClickAdd() {
        Toast.makeText(this, "ADD!", Toast.LENGTH_LONG).show()
    }
}