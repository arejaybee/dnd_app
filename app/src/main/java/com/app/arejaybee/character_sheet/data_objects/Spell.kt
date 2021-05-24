/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class Spell(var level: Int) {
    var name: String = ""
    var description: String = ""
    var prep = 0
}