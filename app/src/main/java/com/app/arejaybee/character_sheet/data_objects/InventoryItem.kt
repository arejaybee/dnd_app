package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class InventoryItem  {
    var name: String = ""
    var description: String = ""
    var slot: SlotEnum = SlotEnum.None
    var weight = 0
    var cost: String = ""

    enum class SlotEnum {
        None, Belt, Body, Chest, Eyes, Feet, Hands, Head, Headband, Neck, Ring, Shield, Shoulders, Weapon
    }
}