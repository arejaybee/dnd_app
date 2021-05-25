/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

import android.content.Context
import kotlinx.serialization.Contextual
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

@kotlinx.serialization.Serializable
open class PlayerCharacter(@Contextual val context: Context, val edition: EnumHelper.EDITION) : Serializable {
    val characterID = UUID.randomUUID().toString()
    var name = ""
    var race = ""
    var characterClass = ""
    var level = ""
    var alignment = ""
    var gender = ""
    var height = ""
    var weight = ""
    var size = ""
    var exp = ""
    var speed: String = ""
    var strScore: Int = 10
        set(strScore) {
            field = strScore
            updateSkills()
        }
    var dexScore: Int = 10
        set(dexScore) {
            field = dexScore
            updateSkills()
        }
    var conScore: Int = 10
        set(conScore) {
            field = conScore
            updateSkills()
        }
    var intScore: Int = 10
        set(intScore) {
            field = intScore
            updateSkills()
        }
    var wisScore: Int = 10
        set(wisScore) {
            field = wisScore
            updateSkills()
        }
    var charScore: Int = 10
        set(charScore) {
            field = charScore
            updateSkills()
        }
    var strScoreBonus: Int = 0
        set(strScoreBonus) {
            field = strScoreBonus
            updateSkills()
        }
    var dexScoreBonus: Int = 0
        set(dexScoreBonus) {
            field = dexScoreBonus
            updateSkills()
        }
    var conScoreBonus: Int = 0
        set(conScoreBonus) {
            field = conScoreBonus
            updateSkills()
        }
    var intScoreBonus: Int = 0
        set(intScoreBonus) {
            field = intScoreBonus
            updateSkills()
        }
    var wisScoreBonus: Int = 0
        set(wisScoreBonus) {
            field = wisScoreBonus
            updateSkills()
        }
    var charScoreBonus: Int = 0
        set(charScoreBonus) {
            field = charScoreBonus
            updateSkills()
        }
    val strMod: Int
        get() = floor((strScore + strScoreBonus - 10) / 2.0).toInt()
    val dexMod: Int
        get() = floor((dexScore + dexScoreBonus - 10) / 2.0).toInt()
    val conMod: Int
        get() = floor((conScore + conScoreBonus - 10) / 2.0).toInt()
    val intMod: Int
        get() = floor((intScore + intScoreBonus - 10) / 2.0).toInt()
    val wisMod: Int
        get() = floor((wisScore + wisScoreBonus - 10) / 2.0).toInt()
    val charMod: Int
        get() = floor((charScore + charScoreBonus - 10) / 2.0).toInt()
    val skills = ArrayList<Skill>()
    var saves = ArrayList<SavingThrow>()
    var conditionalSavingThrowMods = ""
    var armorClass = ArmorClass()
    var initiativeBonus = 0
    var attackBonus = 0
    var grappleBonus = 0
    var currentHp = 0
    var maxHp = 0
    var weapons = ArrayList<Weapon>()
    var rebukeTimes = 0
    var rebukeCheck = 0
    var rebukeDamage = "0d0 + 0"
    var spellLists = ArrayList<SpellList>()
    var currency = "0gp"
    var items = ArrayList<InventoryItem>()
    var companions = ArrayList<PlayerCharacter>()
    var abilities = arrayOfNulls<ArrayList<Ability>>(4)
    var notes = ArrayList<Note>()
    var proficiency = 0
    var spellAbility = ""

    init {
        weapons.add(Weapon())
        for (i in 0..9) {
            spellLists.add(SpellList(i))
        }
        for (i in abilities.indices) {
            abilities[i] = ArrayList()
        }
        notes = ArrayList()
        generateSkills(edition)
        generateSaves(edition)
    }

    fun generateSaves(edition: EnumHelper.EDITION) {
        when (edition) {
            EnumHelper.EDITION.FIFTH -> {
                saves.add(SavingThrow("STR", edition))
                saves.add(SavingThrow("DEX", edition))
                saves.add(SavingThrow("CON", edition))
                saves.add(SavingThrow("INT", edition))
                saves.add(SavingThrow("WIS", edition))
                saves.add(SavingThrow("CHA", edition))
            }
            EnumHelper.EDITION.THREE_FIVE -> {
                saves.add(SavingThrow("Fortify", edition))
                saves.add(SavingThrow("Reflex", edition))
                saves.add(SavingThrow("Will", edition))
            }
        }
    }

    fun generateSkills(edition: EnumHelper.EDITION) {
        when (edition) {
            EnumHelper.EDITION.FIFTH -> {
                addSkill(Skill("Athletics", "STR", this))
                addSkill(Skill("Acrobatic", "DEX", this))
                addSkill(Skill("Sleight of Hand", "DEX", this))
                addSkill(Skill("Stealth", "DEX", this))
                addSkill(Skill("Arcana", "INT", this))
                addSkill(Skill("History", "INT", this))
                addSkill(Skill("Investigation", "INT", this))
                addSkill(Skill("Nature", "INT", this))
                addSkill(Skill("Religion", "INT", this))
                addSkill(Skill("Animal Handling", "WIS", this))
                addSkill(Skill("Insignt", "WIS", this))
                addSkill(Skill("Medicine", "WIS", this))
                addSkill(Skill("Perception", "WIS", this))
                addSkill(Skill("Survival", "WIS", this))
                addSkill(Skill("Deception", "CHA", this))
                addSkill(Skill("Intimidation", "CHA", this))
                addSkill(Skill("Performance", "CHA", this))
                addSkill(Skill("Persuasion", "CHA", this))
            }
            EnumHelper.EDITION.THREE_FIVE -> {
                //populate the array list with default skills
                addSkill(Skill("Appraise", "INT", this))
                addSkill(Skill("Balance", "DEX", this))
                addSkill(Skill("Bluff", "CHA", this))
                addSkill(Skill("Climb", "STR", this))
                addSkill(Skill("Concentration", "CON", this))
                addSkill(Skill("Decipher Script", "INT", this))
                addSkill(Skill("Diplomacy", "CHA", this))
                addSkill(Skill("Disable Device", "INT", this))
                addSkill(Skill("Disguise", "INT", this))
                addSkill(Skill("Escape Artist", "DEX", this))
                addSkill(Skill("Forgery", "INT", this))
                addSkill(Skill("Gather Information", "CHA", this))
                addSkill(Skill("Handle Animal", "CHA", this))
                addSkill(Skill("Heal", "WIS", this))
                addSkill(Skill("Hide", "DEX", this))
                addSkill(Skill("Intimidate", "CHA", this))
                addSkill(Skill("Jump", "STR", this))
                addSkill(Skill("Language", "INT", this))
                addSkill(Skill("Listen", "WIS", this))
                addSkill(Skill("Move Silently", "DEX", this))
                addSkill(Skill("Open Lock", "DEX", this))
                addSkill(Skill("Ride", "DEX", this))
                addSkill(Skill("Search", "WIS", this))
                addSkill(Skill("Sense Motive", "WIS", this))
                addSkill(Skill("Sleight Of Hand", "DEX", this))
                addSkill(Skill("Spellcraft", "INT", this))
                addSkill(Skill("Spot", "WIS", this))
                addSkill(Skill("Survival", "WIS", this))
                addSkill(Skill("Swim", "STR", this))
                addSkill(Skill("Tumble", "DEX", this))
                addSkill(Skill("Use Magic Device", "CHA", this))
                addSkill(Skill("Use Rope", "DEX", this))
            }
        }
    }

    open fun saveCharacter() {
    }

    fun getAbilityScore(abilityName: String): Int {
        var `val` = 0
        when (abilityName) {
            "STR" -> `val` = strScore + strScoreBonus
            "DEX" -> `val` = dexScore + dexScoreBonus
            "CON" -> `val` = conScore + conScoreBonus
            "INT" -> `val` = intScore + intScoreBonus
            "WIS" -> `val` = wisScore + wisScoreBonus
            "CHA" -> `val` = charScore + charScoreBonus
        }
        return floor((`val` - 10) / 2.0).toInt()
    }

    protected fun updateSkills() {
        val skillsToRemove = ArrayList<Skill>()
        for (i in skills.indices) {
            if (skills[i].name == "") {
                skillsToRemove.add(skills[i])
            }
            skills[i].updatePlayer(this)
        }
        for (i in skillsToRemove.indices) {
            skills.removeAt(i)
        }
    }

    fun addSkill(s: Skill) {
        if (!skills.contains(s) && s.name != "") skills.add(s)
        updateSkills()
    }

    fun sortSkills() {
        skills.sortWith { s1, s2 -> s1.name.compareTo(s2.name) }
    }

    fun getSave(s: SavingThrow): SavingThrow {
        if (saves.size == 0) {
            generateSaves(edition)
        }
        return if (saves.indexOf(s) == -1) {
            saves.add(s)
            s
        } else {
            saves[saves.indexOf(s)]
        }
    }

    fun getSave(n: String): SavingThrow {
        return getSave(SavingThrow(n, edition))
    }

    val grapple: Int
        get() = grappleBonus + strMod + attackBonus

    val casterLevel: Int
        get() = try {
            level.toInt()
        } catch (e: Exception) {
            0
        }

    val inventoryWeight: Int
        get() {
            var w = 0
            for (i in items.indices) {
                w += items[i].weight
            }
            return w
        }

    fun addAbility(ability: Ability, index: Int) {
        if (index == -1) {
            if (ability.type == "Feat") {
                abilities[0]?.add(ability)
            } else if (ability.type == "Class Feature") {
                abilities[1]?.add(ability)
            } else if (ability.type == "Racial Trait") {
                abilities[2]?.add(ability)
            } else if (ability.type == "Language") {
                abilities[3]?.add(ability)
            }
        } else {
            if (ability.type == "Feat") {
                abilities[0]?.set(index, ability)
            } else if (ability.type == "Class Feature") {
                abilities[1]?.set(index, ability)
            } else if (ability.type == "Racial Trait") {
                abilities[2]?.set(index, ability)
            } else if (ability.type == "Language") {
                abilities[3]?.set(index, ability)
            }
        }
    }

    fun getProficiency(p: Int): Int {
        return when (p) {
            1 -> proficiency / 2
            2 -> proficiency
            3 -> proficiency * 2
            else -> 0
        }
    }

    val spellMod: Int
        get() = getModifier(spellAbility)

    fun getModifier(name: String): Int {
        return when (name) {
            "STR" -> strMod
            "DEX" -> dexMod
            "CON" -> conMod
            "INT" -> intMod
            "WIS" -> wisMod
            "CHA" -> charMod
            else -> 0
        }
    }

    fun updateSave(st: SavingThrow) {
        when (st.name) {
            "STR" -> saves[0] = st
            "DEX" -> saves[1] = st
            "CON" -> saves[2] = st
            "INT" -> saves[3] = st
            "WIS" -> saves[4] = st
            "CHA" -> saves[5] = st
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other is PlayerCharacter) {
            return other.characterID == this.characterID
        }
        return super.equals(other)
    }
}