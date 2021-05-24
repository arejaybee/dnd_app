/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class Weapon {
    var name: String = ""
    var type: String = ""
    var toHit: String = "0"
    var damage: String = "0d0+0"
    var range: String = "0ft"
    var critical: String = "19-20 x2"
    var notes: String = ""
}