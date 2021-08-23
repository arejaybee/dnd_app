package com.app.arejaybee.character_sheet.data_objects

import com.app.arejaybee.character_sheet.data_objects.EnumHelper.PROFICIENCY

@kotlinx.serialization.Serializable
class SavingThrow(val name: String, private val edition: EnumHelper.EDITION) {
    var base = 0
    var bonus = 0
    var classBonus = 0
    var proficiency: PROFICIENCY = PROFICIENCY.NONE
    var ability = ""

    init {
        when(edition) {
            EnumHelper.EDITION.FIFTH -> ability = name
            else -> {
                ability = "" //Need to tie abilities to names
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is SavingThrow) {
            other.hashCode() == hashCode()
        } else false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    val total: Int
        get() = when (edition) {
            EnumHelper.EDITION.FIFTH -> {
                base + bonus
            }
            EnumHelper.EDITION.THREE_FIVE -> base + classBonus + bonus
            else -> base
        }
}