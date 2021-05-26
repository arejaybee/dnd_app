package com.app.arejaybee.character_sheet.fragments.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.CompanionCharacter
import com.app.arejaybee.character_sheet.databinding.FragmentDescriptionBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.utils.NumberPickerDialog


class DescriptionFragment : RobFragment() {
    companion object {
        const val TAG = "DescriptionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentDescriptionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_description, container, false)
        val view: View = binding.root
        binding.rob = activity?.rob
        binding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    fun onClickStatField(view: View) {
        val tv = view as TextView
        val title = when(tv.id) {
            R.id.det_strScore -> "STR Score"
            R.id.det_dexScore -> "DEX Score"
            R.id.det_conScore -> "CON Score"
            R.id.det_intScore -> "INT Score"
            R.id.det_wisScore -> "WIS Score"
            R.id.det_charScore -> "CHA Score"
            R.id.det_strBonus -> "STR Bonus"
            R.id.det_dexBonus -> "DEX Bonus"
            R.id.det_conBonus -> "CON Bonus"
            R.id.det_intBonus -> "INT Bonus"
            R.id.det_wisBonus -> "WIS Bonus"
            R.id.det_charBonus -> "CHA Bonus"
            else -> ""
        }
        val min = if(title.contains("Score")) 0 else -99
        NumberPickerDialog.build(requireActivity(), title, tv, min).show()
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