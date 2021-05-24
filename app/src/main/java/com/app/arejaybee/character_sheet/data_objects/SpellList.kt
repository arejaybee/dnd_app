/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class SpellList(var level: Int) {
    var known = 0
    var daily = 0
    var used = 0
    var spellClass: String = ""
    var spells: ArrayList<Spell> = ArrayList()
    var isHidden = false
}