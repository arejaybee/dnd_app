package com.app.arejaybee.character_sheet.fragments.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.data_objects.Note
import com.app.arejaybee.character_sheet.fragments.RobFragment


class NoteFragment : RobFragment() {
    companion object {
        const val TAG = "NoteFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.notes_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        activity?.let {
            recyclerView.adapter = NotesAdapter(it.rob.notes, it)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
    }

    override fun onClickAdd() { showNoteDialog(false) }

    override fun onClickEdit() { showNoteDialog(true) }

    override fun onClickDelete() {
        val adapter = view?.findViewById<RecyclerView>(R.id.notes_recycler)?.adapter as NotesAdapter
        adapter.deleteNote()
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


    private fun showNoteDialog(isEdit: Boolean) {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_note, null)
        dialogView?.let {
            val title = it.findViewById<EditText>(R.id.note_dialog_title_edittext)
            val description = it.findViewById<EditText>(R.id.note_dialog_description_edittext)
            if(isEdit) {
                title.setText(NotesAdapter.selectedNote?.title)
                description.setText(NotesAdapter.selectedNote?.text)
            }
            val d = AlertDialog.Builder(requireContext())
                    .setView(it)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_creation_positive) { dialog: DialogInterface, i: Int ->
                        val note = if(isEdit) NotesAdapter.selectedNote else Note()
                        note?.title = title.text.toString()
                        note?.text = description.text.toString()
                        if(isEdit) {
                            note?.let{ editNote ->
                                val rob = activity?.rob
                                if(rob != null) {
                                    val aIndex = rob.notes.indexOf(NotesAdapter.selectedNote)
                                    rob.notes[aIndex] = editNote
                                }
                            }
                        }
                        else {
                            activity?.rob?.notes?.add(note!!)
                        }
                        view?.findViewById<RecyclerView>(R.id.notes_recycler)?.adapter?.notifyDataSetChanged()
                        activity?.rob?.saveCharacter()
                    }
                    .setNegativeButton(R.string.dialog_creation_negative) { dialog: DialogInterface, index: Int ->
                        dialog.dismiss()
                    }
                    .create()

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(d.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            d.show()
            d.window?.attributes = lp
        }
    }
}