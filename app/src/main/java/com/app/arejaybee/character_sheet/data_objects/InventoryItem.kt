package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class InventoryItem  {
    var name: String = ""
    var description: String = ""
    var slot: SlotEnum
    var weight = 0
    lateinit var cost: String

    enum class SlotEnum {
        NONE, BELT, BODY, CHEST, EYES, FEET, HANDS, HEAD, HEADBAND, NECK, RING, SHIELD, SHOULDERS
    }

    init {
        slot = SlotEnum.NONE
    }
}