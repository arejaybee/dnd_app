package com.app.arejaybee.character_sheet.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.CompanionCharacter
import com.app.arejaybee.character_sheet.data_objects.PlayerCharacter
import com.app.arejaybee.character_sheet.data_objects.Spell
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.abilities.AbilitiesFragment
import com.app.arejaybee.character_sheet.fragments.combat.CombatFragment
import com.app.arejaybee.character_sheet.fragments.companions.CompanionFragment
import com.app.arejaybee.character_sheet.fragments.description.DescriptionFragment
import com.app.arejaybee.character_sheet.fragments.inventory.InventoryFragment
import com.app.arejaybee.character_sheet.fragments.notes.NoteFragment
import com.app.arejaybee.character_sheet.fragments.select_character.SelectCharacterFragment
import com.app.arejaybee.character_sheet.fragments.skills.SkillsFragment
import com.app.arejaybee.character_sheet.fragments.spells.SpellsFragment
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var rob: PlayerCharacter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceUtil.setInstance(this)
        setContentView(R.layout.activity_main)

        val data = intent.extras?.get("characterJson")
        data?.let {
            try {
                rob = PlayerCharacter.createFromJson(it.toString())
                navigateToFragment(DescriptionFragment.TAG)
            } catch(err: Exception) {
                navigateToFragment(SelectCharacterFragment.TAG)
                Toast.makeText(this, "Failed to load the character",Toast.LENGTH_LONG).show()
            }
        } ?: navigateToFragment(SelectCharacterFragment.TAG)
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
        if (currentFragment is RobFragment) {
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
        when (view.id) {
            R.id.navbtn_description -> navigateToFragment(DescriptionFragment.TAG)
            R.id.navbtn_abilities -> navigateToFragment(AbilitiesFragment.TAG)
            R.id.navbtn_companion -> navigateToFragment(CompanionFragment.TAG)
            R.id.navbtn_skills -> navigateToFragment(SkillsFragment.TAG)
            R.id.navbtn_notes -> navigateToFragment(NoteFragment.TAG)
            R.id.navbtn_inventory -> navigateToFragment(InventoryFragment.TAG)
            R.id.navbtn_combat -> navigateToFragment(CombatFragment.TAG)
            R.id.navbtn_spells -> navigateToFragment(SpellsFragment.TAG)
            else -> Toast.makeText(this, "TODO!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment is RobFragment && currentFragment !is SelectCharacterFragment) {
            for (i in 0..supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
            if (rob is CompanionCharacter) {
                rob = (rob as CompanionCharacter).owner
                navigateToFragment(CompanionFragment.TAG, true)
            } else {
                navigateToFragment(SelectCharacterFragment.TAG)
            }
        }
    }

    fun showMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.VISIBLE
    }

    fun hideMenuItem(id: Int) {
        findViewById<View>(id).visibility = View.GONE
    }

    fun navigateToFragment(tag: String, forceNavigate: Boolean = false) {
        val fragment = when (tag) {
            SelectCharacterFragment.TAG -> SelectCharacterFragment()
            DescriptionFragment.TAG -> DescriptionFragment()
            AbilitiesFragment.TAG -> AbilitiesFragment()
            CompanionFragment.TAG -> CompanionFragment()
            SkillsFragment.TAG -> SkillsFragment()
            NoteFragment.TAG -> NoteFragment()
            InventoryFragment.TAG -> InventoryFragment()
            CombatFragment.TAG -> CombatFragment()
            SpellsFragment.TAG -> SpellsFragment()
            else -> RobFragment()
        }
        if (fragment.javaClass != getCurrentFragment()?.javaClass) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_layout, fragment, tag)
                    .addToBackStack(tag)
                    .commit()
        } else if (forceNavigate) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_layout, DescriptionFragment(), tag)
                    .commit()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_layout, fragment, tag)
                    .addToBackStack(tag)
                    .commit()
        }
        updateToolbarTitle()
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
        when (tag) {
            DescriptionFragment.TAG -> findViewById<View>(R.id.navbtn_description).isSelected = true
            AbilitiesFragment.TAG -> findViewById<View>(R.id.navbtn_abilities).isSelected = true
            CompanionFragment.TAG -> findViewById<View>(R.id.navbtn_companion).isSelected = true
            SkillsFragment.TAG -> findViewById<View>(R.id.navbtn_skills).isSelected = true
            NoteFragment.TAG -> findViewById<View>(R.id.navbtn_notes).isSelected = true
            InventoryFragment.TAG -> findViewById<View>(R.id.navbtn_inventory).isSelected = true
            CombatFragment.TAG -> findViewById<View>(R.id.navbtn_combat).isSelected = true
            SpellsFragment.TAG -> findViewById<View>(R.id.navbtn_spells).isSelected = true
        }
    }

    fun hideNavigation() {
        findViewById<View>(R.id.activity_navigation).visibility = View.GONE
    }

    private fun getCurrentFragment(): Fragment? {
        val index = supportFragmentManager.backStackEntryCount - 1
        if(index < 0) {
            return null
        }
        val backStackEntry = supportFragmentManager.getBackStackEntryAt(index)
        return supportFragmentManager.findFragmentByTag(backStackEntry.name)
    }

    fun onClickStatField(view: View) {
        val curFrag = getCurrentFragment()
        if (curFrag is DescriptionFragment) {
            curFrag.onClickStatField(view)
        }
    }

    private fun updateToolbarTitle() {
        if (this::rob.isInitialized && rob is CompanionCharacter) {
            setTitleText("Companion of " + (rob as CompanionCharacter).owner.name)
        }
        else {
            setTitleText(R.string.app_name)
        }
    }
}