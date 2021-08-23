/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

import com.app.arejaybee.character_sheet.data_objects.EnumHelper.*
import kotlin.math.floor

@kotlinx.serialization.Serializable
class Skill(val skillName: String, var ability: String, private val edition: EDITION) {
    var rank = 0
    var bonus = 0
    var isClassSkill = false
    var name: String = skillName
    set (m_name) {
        var newName = m_name
        newName = if (newName.length > 1) {
            newName.substring(0, 1) + newName.substring(1)
        } else {
            newName
        }
        field = String.format("%-" + 30 + "." + 30 + "s", newName)
    }
    var proficiency: PROFICIENCY = PROFICIENCY.NONE

    fun Equals(s: Any?): Boolean {
        return if (s is Skill) {
            s.name == name
        } else false
    }

    val mod: Int
        get() {
            var rank = rank
            return when (edition) {
                EDITION.FIFTH -> rank + bonus
                EDITION.THREE_FIVE -> {
                    if (!isClassSkill) {
                        rank = floor(rank / 2.0).toInt()
                    }
                    rank + bonus
                }
                else -> rank
            }
        }
}