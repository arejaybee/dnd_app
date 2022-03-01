package com.app.arejaybee.character_sheet.data_objects
import kotlinx.serialization.Transient

@kotlinx.serialization.Serializable
class CompanionCharacter(var index: Int, var companionEdition: EnumHelper.EDITION) : PlayerCharacter(companionEdition) {
    @Transient
    lateinit var owner: PlayerCharacter
    override fun saveCharacter() {
        if(this::owner.isInitialized) {
            owner.let {
                if (it.companions.size < index) {
                    it.companions[index] = this
                }
                it.saveCharacter()
            }
        }
    }
}