package com.app.arejaybee.character_sheet.data_objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
class MinimalPlayerCharacter : BaseObservable() {
    companion object {
        fun loadCharacter(key: String): MinimalPlayerCharacter {
            val me = createFromJson(SharedPreferenceUtil.instance.getString(key))
            me.characterID = key
            return me
        }
        private fun createFromJson(json: String): MinimalPlayerCharacter {
            val format = Json { ignoreUnknownKeys = true }
            return format.decodeFromString(json)
        }
    }

    @Bindable
    var name = ""

    @Bindable
    var characterClass = ""

    @Bindable
    var level = ""

    var characterID = ""
}