package com.app.arejaybee.character_sheet.data_objects

import kotlinx.serialization.Serializable

@Serializable
class CompanionCharacter(var owner: PlayerCharacter, var index: Int) : PlayerCharacter(owner.edition) {
    override fun saveCharacter() {
        owner.let {
            it.companions[index] = this
            it.saveCharacter()
        }
    }
}