/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

import kotlin.math.floor

@kotlinx.serialization.Serializable
class ArmorClass(var dexScore: Int) {
    var armor = 0
    var shield = 0
    var bonus = 0
    var type = ArmorType.None

    val maxDexMod
    get() = when(type) {
        ArmorType.Medium -> {
            val dexMod = floor((dexScore - 10) / 2.0).toInt()
            if(dexMod > 2) 2 else dexMod
        }
        ArmorType.Heavy -> 0
        else -> floor((dexScore - 10) / 2.0).toInt()
    }
     enum class ArmorType {None, Light, Medium, Heavy}

    val mod: Int
        get() = armor + shield + bonus + maxDexMod
}