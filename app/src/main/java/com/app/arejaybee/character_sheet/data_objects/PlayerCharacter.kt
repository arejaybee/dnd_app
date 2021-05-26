/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil
import com.app.arejaybee.character_sheet.utils.Strings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor
import androidx.databinding.library.baseAdapters.BR

@kotlinx.serialization.Serializable
open class PlayerCharacter(val edition: EnumHelper.EDITION) : BaseObservable() {

    companion object {
        fun loadCharacter(key:String) : PlayerCharacter {
            val json = SharedPreferenceUtil.instance.getString(key)
            return Json.decodeFromString(json)
        }
    }

    val characterID = UUID.randomUUID().toString()
    @Bindable
    var name = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var race = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var characterClass = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var level = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var alignment = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var gender = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var height = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var weight = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var size = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var exp = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var speed: String = ""
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var strScore: Int = 10
    set(value) {
        field = value
        saveCharacter()
        notifyPropertyChanged(BR.strScore)
    }
    @Bindable
    var dexScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.dexScore)
        }
    @Bindable
    var conScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.conScore)
        }
    @Bindable
    var intScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.intScore)
        }
    @Bindable
    var wisScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.wisScore)
        }
    @Bindable
    var charScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.charScore)
        }
    @Bindable
    var strScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.strScoreBonus)
        }
    @Bindable
    var dexScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.dexScoreBonus)
        }
    @Bindable
    var conScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.conScoreBonus)
        }
    @Bindable
    var intScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.intScoreBonus)
        }
    @Bindable
    var wisScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.wisScoreBonus)
        }
    @Bindable
    var charScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
            notifyPropertyChanged(BR.charScoreBonus)
        }

    val strMod: Int
        @Bindable(value = ["strScore", "strScoreBonus"])
        get() = floor((strScore + strScoreBonus - 10) / 2.0).toInt()
    val dexMod: Int
        @Bindable(value = ["dexScore", "dexScoreBonus"])
        get() = floor((dexScore + dexScoreBonus - 10) / 2.0).toInt()
    val conMod: Int
        @Bindable(value = ["conScore", "conScoreBonus"])
        get() = floor((conScore + conScoreBonus - 10) / 2.0).toInt()
    val intMod: Int
        @Bindable(value = ["intScore", "intScoreBonus"])
        get() = floor((intScore + intScoreBonus - 10) / 2.0).toInt()
    val wisMod: Int
        @Bindable(value = ["wisScore", "wisScoreBonus"])
        get() = floor((wisScore + wisScoreBonus - 10) / 2.0).toInt()
    val charMod: Int
        @Bindable(value = ["charScore", "charScoreBonus"])
        get() = floor((charScore + charScoreBonus - 10) / 2.0).toInt()
    val skills = ArrayList<Skill>()
    var saves = ArrayList<SavingThrow>()
    @Bindable
    var conditionalSavingThrowMods = ""
        set(value) {
            field = value
            saveCharacter()
        }
    var armorClass = ArmorClass()
    @Bindable
    var initiativeBonus = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var attackBonus = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var grappleBonus = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var currentHp = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var maxHp = 0
        set(value) {
            field = value
            saveCharacter()
        }
    var weapons = ArrayList<Weapon>()
    @Bindable
    var rebukeTimes = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var rebukeCheck = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var rebukeDamage = "0d0 + 0"
        set(value) {
            field = value
            saveCharacter()
        }
    var spellLists = ArrayList<SpellList>()
    @Bindable
    var currency = "0gp"
        set(value) {
            field = value
            saveCharacter()
        }
    var items = ArrayList<InventoryItem>()
    var companions = ArrayList<PlayerCharacter>()
    var abilities = arrayOfNulls<ArrayList<Ability>>(3)
    var notes = ArrayList<Note>()
    @Bindable
    var proficiency = 0
        set(value) {
            field = value
            saveCharacter()
        }
    @Bindable
    var spellAbility = ""
        set(value) {
            field = value
            saveCharacter()
        }

    init {
        weapons.add(Weapon())
        for (i in 0..9) {
            spellLists.add(SpellList(i))
        }
        for (i in 0..2) {
            abilities[i] = ArrayList()
        }
        notes = ArrayList()
        generateSkills(edition)
        generateSaves(edition)
    }

    private fun toJson() : String {
        return Json.encodeToString(this)
    }

    open fun saveCharacter() {
        val json = toJson()
        val pref = SharedPreferenceUtil.instance
        pref.setString(characterID, json)

        val uuidList = pref.getUUIDList()
        if(!uuidList.contains(characterID)) {
            uuidList.add(characterID)
            SharedPreferenceUtil.instance.setString(Strings.UUID_LIST_KEY, uuidList.joinToString(","))
        }
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
                addSkill(Skill("Athletics", "STR", edition))
                addSkill(Skill("Acrobatic", "DEX", edition))
                addSkill(Skill("Sleight of Hand", "DEX", edition))
                addSkill(Skill("Stealth", "DEX", edition))
                addSkill(Skill("Arcana", "INT", edition))
                addSkill(Skill("History", "INT", edition))
                addSkill(Skill("Investigation", "INT", edition))
                addSkill(Skill("Nature", "INT", edition))
                addSkill(Skill("Religion", "INT", edition))
                addSkill(Skill("Animal Handling", "WIS", edition))
                addSkill(Skill("Insignt", "WIS", edition))
                addSkill(Skill("Medicine", "WIS", edition))
                addSkill(Skill("Perception", "WIS", edition))
                addSkill(Skill("Survival", "WIS", edition))
                addSkill(Skill("Deception", "CHA", edition))
                addSkill(Skill("Intimidation", "CHA", edition))
                addSkill(Skill("Performance", "CHA", edition))
                addSkill(Skill("Persuasion", "CHA", edition))
            }
            EnumHelper.EDITION.THREE_FIVE -> {
                //populate the array list with default skills
                addSkill(Skill("Appraise", "INT", edition))
                addSkill(Skill("Balance", "DEX", edition))
                addSkill(Skill("Bluff", "CHA", edition))
                addSkill(Skill("Climb", "STR", edition))
                addSkill(Skill("Concentration", "CON", edition))
                addSkill(Skill("Decipher Script", "INT", edition))
                addSkill(Skill("Diplomacy", "CHA", edition))
                addSkill(Skill("Disable Device", "INT", edition))
                addSkill(Skill("Disguise", "INT", edition))
                addSkill(Skill("Escape Artist", "DEX", edition))
                addSkill(Skill("Forgery", "INT", edition))
                addSkill(Skill("Gather Information", "CHA", edition))
                addSkill(Skill("Handle Animal", "CHA", edition))
                addSkill(Skill("Heal", "WIS", edition))
                addSkill(Skill("Hide", "DEX", edition))
                addSkill(Skill("Intimidate", "CHA", edition))
                addSkill(Skill("Jump", "STR", edition))
                addSkill(Skill("Language", "INT", edition))
                addSkill(Skill("Listen", "WIS", edition))
                addSkill(Skill("Move Silently", "DEX", edition))
                addSkill(Skill("Open Lock", "DEX", edition))
                addSkill(Skill("Ride", "DEX", edition))
                addSkill(Skill("Search", "WIS", edition))
                addSkill(Skill("Sense Motive", "WIS", edition))
                addSkill(Skill("Sleight Of Hand", "DEX", edition))
                addSkill(Skill("Spellcraft", "INT", edition))
                addSkill(Skill("Spot", "WIS", edition))
                addSkill(Skill("Survival", "WIS", edition))
                addSkill(Skill("Swim", "STR", edition))
                addSkill(Skill("Tumble", "DEX", edition))
                addSkill(Skill("Use Magic Device", "CHA", edition))
                addSkill(Skill("Use Rope", "DEX", edition))
            }
        }
    }

    fun getAbilityMod(abilityName: String): Int {
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
        }
        for (i in skillsToRemove.indices) {
            skills.removeAt(i)
        }
    }

    fun addSkill(s: Skill) {
        if (!skills.contains(s) && s.name != "") skills.add(s)
        updateSkills()
    }

    fun getSkillMod(s: String) : Int{
        val skill = skills.find { it.name == s }
        skill?.let{
            it.mod + getAbilityMod(it.ability)
        }
        return 0
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