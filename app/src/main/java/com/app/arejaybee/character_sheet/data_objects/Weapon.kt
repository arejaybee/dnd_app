/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

import java.lang.NumberFormatException
import java.net.NoRouteToHostException

@kotlinx.serialization.Serializable
class Weapon {
    var name: String = ""
    var type: String = ""
    var toHit: String = ""
    var intToHit: Int = 0
    var damageBonus: String = ""
    var intDamage: Int = 0
    var damage: String = "0d0"
    var range: String = "0ft"
    var notes: String = ""
    var abilityType: String = ""
    var isProficient: Boolean = false

    init {
        if(toHit.isNotEmpty()) {
            intToHit = try {
                toHit.toInt()
            } catch (err: NumberFormatException) {
                0
            }
            toHit = ""
        }

        if(damageBonus.isNotEmpty()) {
            intDamage = try {
                damageBonus.toInt()
            } catch (err: NumberFormatException) {
                0
            }
            damageBonus = ""
        }
    }
}