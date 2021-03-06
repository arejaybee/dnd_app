package com.app.arejaybee.character_sheet.fragments.select_character

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.activity.MainActivity
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
import com.app.arejaybee.character_sheet.data_objects.MinimalPlayerCharacter
import com.app.arejaybee.character_sheet.data_objects.PlayerCharacter
import com.app.arejaybee.character_sheet.fragments.description.DescriptionFragment
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil

class SelectCharacterFragment : RobFragment() {
    companion object {
        const val TAG = "SelectCharacterFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_select, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.hideNavigation()
        createPlayerList()
    }

    override fun onClickAdd() {
        //val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
        //Create a character here
        activity?.rob = PlayerCharacter(EnumHelper.EDITION.FIFTH)
        activity?.navigateToFragment(DescriptionFragment.TAG)
    }

    override fun onClickEmail() {
        val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
        activity?.rob = (adapter as CharacterSelectAdapter).getCharacter()
        super.onClickEmail()
    }

    override fun onClickDelete() {
        AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitle(R.string.select_character_delete_title)
                .setMessage(R.string.select_character_delete_message)
                .setPositiveButton(R.string.select_character_delete_positive){ dialog, _ ->
                    val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
                    (adapter as CharacterSelectAdapter).deleteSelectedPlayer()
                    adapter.notifyDataSetChanged()
                    toggleVisibility(adapter.itemCount > 0)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.select_character_delete_negative){ dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()

    }

    override fun onClickEdit() {
        val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
        activity?.rob = (adapter as CharacterSelectAdapter).getCharacter()
        applyCharacterUpdates()
        activity?.navigateToFragment(DescriptionFragment.TAG)
    }

    private fun createPlayerList() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.character_select_recycler)
        val players = getPlayerList()
        toggleVisibility(players.isNotEmpty())
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView?.adapter = CharacterSelectAdapter(players, it)
        }
    }

    private fun getPlayerList() : ArrayList<MinimalPlayerCharacter> {
        val pref = SharedPreferenceUtil.instance
        val idList = pref.getUUIDList()
        val players = arrayListOf<MinimalPlayerCharacter>()
        idList.map {
            if(it.isNotEmpty()) {
                players.add(MinimalPlayerCharacter.loadCharacter(it))
            }
        }
        return players
    }

    private fun toggleVisibility(hasPlayers: Boolean) {
        view?.findViewById<TextView>(R.id.character_select_empty_text)?.visibility = if(hasPlayers) View.GONE else View.VISIBLE
        view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.visibility = if(hasPlayers) View.VISIBLE else View.GONE
    }

    private fun setupToolbar() {
        activity?.showMenuItem(R.id.toolbar_add_btn)
        activity?.hideMenuItem(R.id.toolbar_delete_btn)
        activity?.hideMenuItem(R.id.toolbar_edit_btn)
        activity?.hideMenuItem(R.id.toolbar_email_btn)
        activity?.hideMenuItem(R.id.toolbar_home_btn)
        activity?.hideMenuItem(R.id.toolbar_save_btn)
    }

    /**
     * This will be used to apply updates that are needed from version to version
     */
    private fun applyCharacterUpdates() {
        activity?.rob?.let {
            it.skills.map { skill ->
                if(skill.name == "Insignt") {
                    skill.name = "Insight"
                }
            }
        }
    }
}