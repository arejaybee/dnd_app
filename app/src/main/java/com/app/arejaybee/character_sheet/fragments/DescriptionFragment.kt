package com.app.arejaybee.character_sheet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.databinding.FragmentDescriptionBinding


class DescriptionFragment : RobFragment() {
    companion object {
        const val TAG = "DescriptionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_description, container, false)

        val binding: FragmentDescriptionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_description, container, false)
        val view: View = binding.root
        //here data must be an instance of the class MarsDataProvider
        //here data must be an instance of the class MarsDataProvider
        binding.rob = activity?.rob
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickHome() {
        super.onClickHome()
        activity?.onBackPressed()
    }

    private fun setupToolbar() {
        activity?.hideMenuItem(R.id.toolbar_add_btn)
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)

        activity?.showMenuItem(R.id.toolbar_home_btn)
    }
}