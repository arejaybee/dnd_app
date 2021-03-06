/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.data_objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.app.arejaybee.character_sheet.utils.SharedPreferenceUtil
import com.app.arejaybee.character_sheet.utils.Strings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

@kotlinx.serialization.Serializable
open class PlayerCharacter(open val edition: EnumHelper.EDITION) : BaseObservable() {

    companion object {
        fun loadCharacter(key: String): PlayerCharacter {
            return createFromJson(SharedPreferenceUtil.instance.getString(key))
        }
        fun createFromJson(json: String): PlayerCharacter {
            return try {
                Json.decodeFromString(json)
            } catch(e: Exception) {
                val newJson = json.replace("\"companions\":[","\"null\":[")
                val rob = createFromJson(newJson)
                rob.companions.clear()
                rob.saveCharacter()
                rob
            }
        }
        fun hasTriplet(nums: List<IntArray>, triplet: IntArray): Boolean {
            val triplets = nums.filter{num ->
                return num[0] == triplet[0] && num[1] == triplet[1] && num[2] == triplet[2]
            }
            return triplets.size > 0
        }
        fun getCharacterFromMinimalCharacter(mChar: MinimalPlayerCharacter) : PlayerCharacter {
            return loadCharacter(mChar.characterID)
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
    var speed: String = "0"
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var strScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var dexScore: Int = 10
        set(value) {
            field = value
            armorClass.dexScore = value + dexScoreBonus
            saveCharacter()
        }

    @Bindable
    var conScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var intScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var wisScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var charScore: Int = 10
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var strScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var dexScoreBonus: Int = 0
        set(value) {
            field = value
            armorClass.dexScore = value + dexScore
            saveCharacter()
        }

    @Bindable
    var conScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var intScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var wisScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var charScoreBonus: Int = 0
        set(value) {
            field = value
            saveCharacter()
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

    @Bindable
    var conditionalSavingThrowMods = ""
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var initiativeBonus = 0
        set(value) {
            field = value
            saveCharacter()
        }

    @Bindable
    var initiativeProficiency: EnumHelper.PROFICIENCY = EnumHelper.PROFICIENCY.NONE
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

    @Bindable
    var spellAttackBonus = 0
    set(value){
        field = value
        saveCharacter()
    }

    @Bindable
    var spellDCBonus = 0
    set(value){
        field = value
        saveCharacter()
    }

    val skills = ArrayList<Skill>()

    var items = ArrayList<InventoryItem>()
    var companions = ArrayList<CompanionCharacter>()
    var abilities = arrayOfNulls<ArrayList<Ability>>(3)
    var notes = ArrayList<Note>()
    var saves = ArrayList<SavingThrow>()
    var weapons = ArrayList<Weapon>()
    var armorClass = ArmorClass(dexScore+dexScoreBonus)

    init {
        if (spellLists.isEmpty()) {
            for (i in 0..9) {
                spellLists.add(SpellList(i))
            }
        }
        for (i in 0..2) {
            if (abilities[i].isNullOrEmpty()) {
                abilities[i] = ArrayList()
            }
        }
        if (notes.isNullOrEmpty()) {
            notes = ArrayList()
        }
        if (skills.isNullOrEmpty()) {
            generateSkills(edition)
        }
        if (saves.isNullOrEmpty()) {
            generateSaves(edition)
        }
        companions.map {
            it.owner = this
        }
    }

    fun toJson(): String {
        return Json.encodeToString(this)
    }

    open fun saveCharacter() {
        val json = toJson()
        val pref = SharedPreferenceUtil.instance
        pref.setString(characterID, json)

        val uuidList = pref.getUUIDList()
        if (!uuidList.contains(characterID)) {
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
                addSkill(Skill("Insight", "WIS", edition))
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

    fun getSkillMod(s: Skill): Int {
        val skill = skills[skills.indexOf(s)]
        val prof = calculateProficiency(skill.proficiency)
        return skill.mod + getAbilityMod(skill.ability) + prof
    }

    fun calculateProficiency(prof: EnumHelper.PROFICIENCY) : Int {
        return when(prof) {
            EnumHelper.PROFICIENCY.NONE -> 0
            EnumHelper.PROFICIENCY.NORMAL -> proficiency
            EnumHelper.PROFICIENCY.DOUBLE -> proficiency * 2
            EnumHelper.PROFICIENCY.HALF -> floor(proficiency/2.0).toInt()
        }
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

    fun getSaveMod(name: String): Int {
        val save = getSave(name)
        return save.total + calculateProficiency(save.proficiency) + getAbilityMod(save.ability)
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
            items.map {
                w+= it.weight
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
        if (other is PlayerCharacter) {
            return other.characterID == this.characterID
        }
        return super.equals(other)
    }
}