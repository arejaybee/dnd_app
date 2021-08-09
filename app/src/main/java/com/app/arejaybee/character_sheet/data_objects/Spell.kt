/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class Spell(var level: Int) {

    companion object {
        enum class SPELL_SCHOOL {
            Abjuration,
            Conjuration,
            Divination,
            Enchantment,
            Evocation,
            Illusion,
            Necromancy,
            Transmutation,
            OTHER
        }
    }
    var name: String = ""
    var effect: String = ""
    var school: SPELL_SCHOOL = SPELL_SCHOOL.OTHER
    var castingTime: String = ""
    var range: String = ""
    var duration: String = ""
    var isSemantic: Boolean = false
    var isVerbal: Boolean = false
    var materialComponent: String = ""
    var notes: String = ""

}