package com.app.arejaybee.character_sheet.fragments

import androidx.fragment.app.Fragment
import com.app.arejaybee.character_sheet.activity.MainActivity

open class RobFragment : Fragment() {
  val activity
        get() = if (getActivity() == null) null else getActivity() as MainActivity

    companion object {
        const val TAG = "RobFragment"
    }

    open fun onClickAdd() {

    }

    open fun onClickEdit() {

    }

    open fun onClickDelete() {

    }

    open fun onClickEmail() {

    }

    open fun onClickSave() {

    }

    open fun onClickMenu() {

    }

    open fun onClickHome() {

    }
}