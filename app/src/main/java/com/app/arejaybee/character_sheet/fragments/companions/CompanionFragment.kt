package com.app.arejaybee.character_sheet.fragments.companions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.CompanionCharacter
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.description.DescriptionFragment

class CompanionFragment : RobFragment() {
    companion object {
        const val TAG = "CompanionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_companion, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickAdd() {
        activity?.rob?.let {
            val companion = CompanionCharacter(it, it.companions.size)
            it.companions.add(companion)
            activity?.rob = companion
            companion.saveCharacter()
            activity?.navigateToFragment(DescriptionFragment.TAG)
        }
    }

    override fun onClickEdit() {

    }

    override fun onClickDelete() {
        
    }

    private fun setupToolbar() {
        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)

        activity?.showMenuItem(R.id.toolbar_home_btn)
    }
}