package com.app.arejaybee.character_sheet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.EnumHelper
import com.app.arejaybee.character_sheet.data_objects.PlayerCharacter
import com.app.arejaybee.character_sheet.recyclers.CharacterSelectAdapter

class SelectCharacterFragment : RobFragment() {
    companion object {
        const val TAG = "SelectCharacterFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitleText(R.string.app_name)

        val recyclerView = view.findViewById<RecyclerView>(R.id.character_select_recycler)
        val players = mutableListOf<PlayerCharacter>()
        toggleVisibility(players.isNotEmpty())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = CharacterSelectAdapter(players.toTypedArray(), it)

        }
    }

    private fun toggleVisibility(hasPlayers: Boolean) {
        view?.findViewById<TextView>(R.id.character_select_empty_text)?.visibility = if(hasPlayers) View.GONE else View.VISIBLE
        view?.findViewById<RecyclerView>(R.id.character_select_recycler)?.visibility = if(hasPlayers) View.VISIBLE else View.GONE
    }

    override fun onClickAdd() {
        Toast.makeText(requireContext(), "ADDED!", Toast.LENGTH_LONG).show()
    }
}