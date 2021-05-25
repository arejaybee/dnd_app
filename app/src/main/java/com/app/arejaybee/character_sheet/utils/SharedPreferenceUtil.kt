package com.app.arejaybee.character_sheet.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.util.prefs.Preferences

class SharedPreferenceUtil {
    var activity: Activity? = null
        set(value) {
        field = value
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    }
    var sharedPref: SharedPreferences? = null

    companion object {
        lateinit var instance: SharedPreferenceUtil
        fun setInstance(activity: Activity) {
            instance = SharedPreferenceUtil()
            instance.activity = activity
        }
    }

    fun setString(key: String, value: String) {
        with (sharedPref!!.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String {
        val ret = sharedPref?.getString(key, "")
        ret?.let {
            return it
        }
        return ""
    }

    fun removeString(key: String?) {
        key?.let {
            sharedPref?.edit()?.remove(it)?.apply()
        }
    }

    fun getUUIDList(): MutableList<String> {
        val list = sharedPref?.getString(Strings.UUID_LIST_KEY, "")
        list?.let {
            return list.split(",").toMutableList()
        }
        return mutableListOf()
    }
}