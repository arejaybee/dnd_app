/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class ArmorClass {
    var armor = 0
    var shield = 0
    var misc = 0

    init {
        armor = 0
        shield = 0
        misc = 0
    }

    val mod: Int
        get() = armor + shield + misc
}