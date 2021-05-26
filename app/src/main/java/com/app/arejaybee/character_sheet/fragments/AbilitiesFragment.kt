package com.app.arejaybee.character_sheet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.Ability
import com.app.arejaybee.character_sheet.recyclers.AbilityAdapter

class AbilitiesFragment : RobFragment() {
    companion object {
        const val TAG = "AbilitiesFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ability, container, false)
    }
    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitleText(R.string.app_name)
        val recyclerView = view.findViewById<RecyclerView>(R.id.ability_list_recycler)
        val abilities = activity?.rob?.abilities


        abilities?.let {
            val ability = Ability()
            ability.title = "WORKS"
            abilities[0].add(ability)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = AbilityAdapter(it, activity as MainActivity)
        }
    }

    private fun setupToolbar() {
        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.showMenuItem(R.id.toolbar_home_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)
    }
}