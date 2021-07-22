package com.app.arejaybee.character_sheet.fragments.companions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.CompanionCharacter
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.fragments.description.DescriptionFragment
import com.app.arejaybee.character_sheet.fragments.select_character.CharacterSelectAdapter
import com.app.arejaybee.character_sheet.fragments.select_character.CompanionSelectAdapter

class CompanionFragment : RobFragment() {
    companion object {
        const val TAG = "CompanionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_companion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.companion_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = CompanionSelectAdapter(activity?.rob!!.companions, it)
            if(it.rob.companions.isEmpty()) {
                view.findViewById<TextView>(R.id.companion_empty).visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickAdd() {
        activity?.rob?.let {
            val companion = CompanionCharacter(it.companions.size, it.edition)
            companion.owner = it
            it.companions.add(companion)
            activity?.rob = companion
            companion.saveCharacter()
            activity?.navigateToFragment(DescriptionFragment.TAG)
        }
    }

    override fun onClickEdit() {
        val adapter = view?.findViewById<RecyclerView>(R.id.companion_recycler)?.adapter as CompanionSelectAdapter
        val companion = adapter.getCharacter()

        activity?.rob?.let {
            companion.owner = it
            it.companions.add(companion)
            activity?.rob = companion
            companion.saveCharacter()
            activity?.navigateToFragment(DescriptionFragment.TAG)
        }
    }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.companion_recycler)?.adapter as CompanionSelectAdapter
        adapter.deleteSelectedCompanion()
        adapter.notifyDataSetChanged()
    }

    private fun setupToolbar() {
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)

        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.showMenuItem(R.id.toolbar_home_btn)
    }
}