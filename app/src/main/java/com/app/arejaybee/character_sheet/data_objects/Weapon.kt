/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class Weapon {
    var name: String = ""
    var type: String = ""
    var toHit: String = ""
    var damageBonus: String = ""
    var damage: String = "0d0"
    var range: String = "0ft"
    var notes: String = ""
    var abilityType: String = ""
    var isProficient: Boolean = false
}