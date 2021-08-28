package com.app.arejaybee.character_sheet.fragments.description

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.app.arejaybee.character_sheet.R
import com.app.arejaybee.character_sheet.databinding.FragmentDescriptionBinding
import com.app.arejaybee.character_sheet.fragments.RobFragment
import com.app.arejaybee.character_sheet.utils.Util
import org.w3c.dom.Text


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
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val str = view.findViewById<TextView>(R.id.dtv_strMod)
                val dex = view.findViewById<TextView>(R.id.dtv_dexMod)
                val con = view.findViewById<TextView>(R.id.dtv_conMod)
                val int = view.findViewById<TextView>(R.id.dtv_intMod)
                val wis = view.findViewById<TextView>(R.id.dtv_wisMod)
                val cha = view.findViewById<TextView>(R.id.dtv_charMod)

                str.text = activity?.rob?.strMod.toString()
                dex.text = activity?.rob?.dexMod.toString()
                con.text = activity?.rob?.conMod.toString()
                int.text = activity?.rob?.intMod.toString()
                wis.text = activity?.rob?.wisMod.toString()
                cha.text = activity?.rob?.charMod.toString()
            }
        }
        view.findViewById<EditText>(R.id.det_strScore).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_strBonus).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_dexScore).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_dexBonus).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_conScore).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_conBonus).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_intScore).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_intBonus).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_wisScore).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_wisBonus).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_charScore).addTextChangedListener(watcher)
        view.findViewById<EditText>(R.id.det_charBonus).addTextChangedListener(watcher)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        activity?.showNavigation(TAG)
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