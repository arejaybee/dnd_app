package com.app.arejaybee.character_sheet.data_objects

import kotlinx.serialization.Serializable

@Serializable
class Companion(var owner: PlayerCharacter, var index: Int) : PlayerCharacter(owner.edition) {
    override fun saveCharacter() {
        owner.let {
            it.companions[index] = this
            it.saveCharacter()
        }
    }
}