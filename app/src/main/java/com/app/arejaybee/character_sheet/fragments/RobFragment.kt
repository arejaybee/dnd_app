package com.app.arejaybee.character_sheet.fragments

import androidx.fragment.app.Fragment
import com.app.arejaybee.character_sheet.activity.MainActivity

open class RobFragment : Fragment() {
   val activity
    get () = if(getActivity() == null) null else getActivity() as MainActivity
}