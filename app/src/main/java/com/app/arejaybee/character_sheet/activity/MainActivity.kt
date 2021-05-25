package com.app.arejaybee.character_sheet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.SelectCharacterFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.activity_fragment_layout, SelectCharacterFragment(), SelectCharacterFragment.TAG)
                .addToBackStack(SelectCharacterFragment.TAG)
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
        val index = supportFragmentManager.backStackEntryCount - 1
        val backStackEntry = supportFragmentManager.getBackStackEntryAt(index)
        val currentFragment = supportFragmentManager.findFragmentByTag(backStackEntry.name)
        if(currentFragment is RobFragment) {
            when (view.id) {
                R.id.toolbar_add_btn -> currentFragment.onClickAdd()
                R.id.toolbar_edit_btn -> currentFragment.onClickEdit()
                R.id.toolbar_delete_btn -> currentFragment.onClickDelete()
                R.id.toolbar_email_btn -> currentFragment.onClickEmail()
                R.id.toolbar_menu_btn -> currentFragment.onClickMenu()
                R.id.toolbar_save_btn -> currentFragment.onClickSave()
                R.id.toolbar_home_btn -> currentFragment.onClickHome()
            }
        }
    }

    fun showMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.VISIBLE
    }

    fun hideMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.GONE
    }
}