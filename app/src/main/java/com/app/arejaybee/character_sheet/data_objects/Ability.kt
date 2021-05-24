package com.app.arejaybee.character_sheet.data_objects

@kotlinx.serialization.Serializable
class Ability {
    var title: String
    lateinit var description: String
    var type: String

    constructor() {
        title = ""
        description = ""
        type = "Trait"
    }

    constructor(title: String, type: String) {
        this.title = title
        this.type = type
    }

    constructor(title: String, type: String, description: String) {
        this.title = title
        this.type = type
        this.description = description
    }
}