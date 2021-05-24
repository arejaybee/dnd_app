package com.app.arejaybee.character_sheet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.fragments.SelectCharacterFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.activity_fragment_layout, SelectCharacterFragment())
                .commit()
    }


    fun setTitleText(title: Int) {
        setTitleText(getString(title))
    }

    fun setTitleText(title: String) {
        val toolbarText = findViewById<TextView>(R.id.toolbar_title)
        toolbarText.text = title
    }

    fun onClickMenu(view: View) {
        when(view.id) {
            R.id.toolbar_add_btn -> onClickAdd()
        }
    }

    fun showMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.VISIBLE
    }

    fun hideMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.GONE
    }

    fun onClickAdd() {
        Toast.makeText(this, "ADD!", Toast.LENGTH_LONG).show()
    }
}