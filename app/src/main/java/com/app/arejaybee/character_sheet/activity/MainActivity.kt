package com.app.arejaybee.character_sheet.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.PlayerCharacter
import com.app.arejaybee.character_sheet.fragments.AbilitiesFragment
import com.app.arejaybee.character_sheet.fragments.DescriptionFragment
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.SelectCharacterFragment
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil

class MainActivity : AppCompatActivity() {
    lateinit var rob: PlayerCharacter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceUtil.setInstance(this)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_layout, SelectCharacterFragment(), SelectCharacterFragment.TAG)
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
        val currentFragment = getCurrentFragment()
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

    fun onClickNavigation(view: View) {
        when(view.id) {
            R.id.navbtn_description -> navigateToFragment(DescriptionFragment.TAG)
            R.id.navbtn_abilities -> navigateToFragment(AbilitiesFragment.TAG)
            else -> Toast.makeText(this, "TODO!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if(currentFragment is RobFragment && currentFragment !is SelectCharacterFragment) {
            for(i in 0..supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
            navigateToFragment(SelectCharacterFragment.TAG)
        }
    }

    fun showMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.VISIBLE
    }

    fun hideMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.GONE
    }

    fun navigateToFragment(tag: String) {
        val fragment = when(tag) {
            SelectCharacterFragment.TAG -> SelectCharacterFragment()
            DescriptionFragment.TAG -> DescriptionFragment()
            AbilitiesFragment.TAG -> AbilitiesFragment()
            else -> RobFragment()
        }
        if(fragment.javaClass != getCurrentFragment()?.javaClass) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_layout, fragment, tag)
                    .addToBackStack(tag)
                    .commit()
        }
    }

    fun showNavigation(tag: String) {
        findViewById<View>(R.id.activity_navigation).visibility = View.VISIBLE
        findViewById<View>(R.id.navbtn_abilities).isSelected = false
        findViewById<View>(R.id.navbtn_combat).isSelected = false
        findViewById<View>(R.id.navbtn_companion).isSelected = false
        findViewById<View>(R.id.navbtn_description).isSelected = false
        findViewById<View>(R.id.navbtn_inventory).isSelected = false
        findViewById<View>(R.id.navbtn_notes).isSelected = false
        findViewById<View>(R.id.navbtn_skills).isSelected = false
        findViewById<View>(R.id.navbtn_spells).isSelected = false
        when(tag) {
            DescriptionFragment.TAG -> findViewById<View>(R.id.navbtn_description).isSelected = true
            AbilitiesFragment.TAG -> findViewById<View>(R.id.navbtn_abilities).isSelected = true
        }
    }

    fun hideNavigation() {
        findViewById<View>(R.id.activity_navigation).visibility = View.GONE
    }

    private fun getCurrentFragment() : Fragment?{
        val index = supportFragmentManager.backStackEntryCount - 1
        val backStackEntry = supportFragmentManager.getBackStackEntryAt(index)
        return supportFragmentManager.findFragmentByTag(backStackEntry.name)
    }

    fun onClickStatField(view: View) {
        val curFrag = getCurrentFragment()
        if(curFrag is DescriptionFragment) {
            curFrag.onClickStatField(view)
        }
    }
}