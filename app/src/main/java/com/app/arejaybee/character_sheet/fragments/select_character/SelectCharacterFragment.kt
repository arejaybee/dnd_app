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
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitleText(R.string.app_name)
        val recyclerView = view.findViewById<RecyclerView>(R.id.character_select_recycler)
        val players = getPlayerList()
        toggleVisibility(players.isNotEmpty())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = CharacterSelectAdapter(players, it)
        }
    }

    override fun onClickAdd() {
        val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
        //Create a character here
        AlertDialog.Builder(requireContext())
                .setTitle(R.string.select_character_alert_title)
                .setMessage(R.string.select_character_alert_message)
                .setPositiveButton(R.string.select_character_alert_positive){ dialog: DialogInterface, i: Int ->
                    val rob = PlayerCharacter(EnumHelper.EDITION.FIFTH)
                }
                .setNegativeButton(R.string.select_character_alert_negative) { dialog: DialogInterface, index: Int ->
                    activity?.rob = PlayerCharacter(EnumHelper.EDITION.FIFTH)
                    activity?.navigateToFragment(DescriptionFragment.TAG)
                }
                .create()
                .show()
    }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
        (adapter as CharacterSelectAdapter).deleteSelectedPlayer()
        adapter.notifyDataSetChanged()
        toggleVisibility(adapter.itemCount > 0)
    }

    override fun onClickEdit() {
        val adapter = view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.adapter
        activity?.rob = (adapter as CharacterSelectAdapter).getCharacter()
        activity?.navigateToFragment(DescriptionFragment.TAG)
    }

    fun getPlayerList() : MutableList<PlayerCharacter> {
        val pref = SharedPreferenceUtil.instance
        val idList = pref.getUUIDList()
        val players = mutableListOf<PlayerCharacter>()
        idList.map {
            if(it.isNotEmpty()) {
                players.add(PlayerCharacter.loadCharacter(it))
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
}