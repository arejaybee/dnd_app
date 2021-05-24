/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.DataObjects;

import android.content.Context;

import com.app.arejaybee.character_sheet.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class PlayerCharacter implements Serializable {

    public static final long serialVersionUID = 42L;
    public static String CHARACTER_PATH = "/characters";
    public static int RANDOM_NUMBER_ID = 100000;
    protected String m_characterID;
    protected String m_name;
    protected String m_race;
    protected String m_class;
    protected String m_level;
    protected String m_alignment;
    protected String m_gender;
    protected String m_height;
    protected String m_weight;
    protected String m_size;
    protected String m_exp;
    protected String m_speed;

    protected int m_strScore;
    protected int m_dexScore;
    protected int m_conScore;
    protected int m_intScore;
    protected int m_wisScore;
    protected int m_charScore;

    protected int m_strScoreBonus;
    protected int m_dexScoreBonus;
    protected int m_conScoreBonus;
    protected int m_intScoreBonus;
    protected int m_wisScoreBonus;
    protected int m_charScoreBonus;

    protected ArrayList<Skill> m_skills;
    protected ArrayList<SavingThrow> m_saves;
    protected String m_conditionalSavingThrowMods;
    protected ArmorClass m_armorClass;
    protected int m_initiativeBonus;
    protected int m_attackBonus;
    protected int m_grappleBonus;
    protected int m_currentHp;
    protected int m_maxHp;
    protected ArrayList<Weapon> m_weapons;

    protected ArrayList<Domain> m_domains;
    protected int m_rebukeTimes;
    protected int m_rebukeCheck;
    protected String m_rebukeDamage;
    protected int m_arcaneFailure;
    protected ArrayList<SpellList> m_spellsLists;

    protected String m_currency;
    protected ArrayList<InventoryItem> m_items;

    protected ArrayList<Companion> m_companions;

    protected ArrayList<Ability>[] m_abilities;

    protected ArrayList<Note> m_notes;
    protected String m_edition;
    protected int m_proficiency;
    protected String m_spellAbility;
    protected boolean m_isCompanion;

    public PlayerCharacter() {
    }

    public PlayerCharacter(String edition, boolean generateSkills) {
        m_characterID = "";
        m_name = "";
        m_race = "";
        m_class = "";
        m_level = "";
        m_alignment = "";
        m_gender = "";
        m_height = "";
        m_weight = "";
        m_size = "";
        m_exp = "";
        m_strScore = 10;
        m_dexScore = 10;
        m_conScore = 10;
        m_intScore = 10;
        m_wisScore = 10;
        m_charScore = 10;
        m_strScoreBonus = 0;
        m_dexScoreBonus = 0;
        m_conScoreBonus = 0;
        m_intScoreBonus = 0;
        m_wisScoreBonus = 0;
        m_charScoreBonus = 0;
        m_saves = new ArrayList<>(); //TODO - make this a map instead (Name -> SavingThrow)

        m_conditionalSavingThrowMods = "";
        m_armorClass = new ArmorClass();
        m_initiativeBonus = 0;
        m_attackBonus = 0;
        m_grappleBonus = 0;
        m_currentHp = 0;
        m_maxHp = 0;
        m_weapons = new ArrayList<>();
        m_weapons.add(new Weapon());
        m_domains = new ArrayList<>();
        m_domains.add(new Domain());
        m_domains.add(new Domain());
        m_rebukeTimes = 0;
        m_rebukeCheck = 0;
        m_rebukeDamage = "0d0 + 0";
        m_spellsLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            m_spellsLists.add(new SpellList(i));
        }
        m_currency = "0gp";
        m_items = new ArrayList<>();
        m_companions = new ArrayList<>();
        m_abilities = new ArrayList[4];
        for (int i = 0; i < m_abilities.length; i++) {
            m_abilities[i] = new ArrayList<>();
        }
        m_notes = new ArrayList<>();
        m_edition = edition;
        m_skills = new ArrayList<>();
        if (generateSkills) {
            generateSkills(edition);
        }
        generateSaves(edition);
        m_proficiency = 0;
        m_spellAbility = "";
        m_isCompanion = false;
    }

    public static PlayerCharacter loadCharacter(String characterID, Context con) {
        try {
            FileInputStream fin = new FileInputStream(new File(con.getExternalFilesDir(null) + "/characters/", characterID + ".char"));
            ObjectInputStream in = new ObjectInputStream(fin);
            return (PlayerCharacter) in.readObject();
        } catch (Exception e) {
            System.out.println("Error loading character: " + e.getMessage());
        }
        return null;
    }

    public static PlayerCharacter loadCharacter(File f) {
        try {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fin);
            return (PlayerCharacter) in.readObject();
        } catch (Exception e) {
            System.out.println("Error loading character: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void generateSaves(String edition) {
        switch (edition) {
            case ("5e"):
                m_saves.add(new SavingThrow("STR", edition));
                m_saves.add(new SavingThrow("DEX", edition));
                m_saves.add(new SavingThrow("CON", edition));
                m_saves.add(new SavingThrow("INT", edition));
                m_saves.add(new SavingThrow("WIS", edition));
                m_saves.add(new SavingThrow("CHA", edition));
                break;
            case ("3.5"):
            case ("Pathfinder"):
                m_saves.add(new SavingThrow("Fortify", edition));
                m_saves.add(new SavingThrow("Reflex", edition));
                m_saves.add(new SavingThrow("Will", edition));
                break;
        }

    }

    public void generateSkills(String edition) {
        switch (edition) {
            case ("5e"):
                addSkill(new Skill("Athletics", "STR", this));
                addSkill(new Skill("Acrobatic", "DEX", this));
                addSkill(new Skill("Sleight of Hand", "DEX", this));
                addSkill(new Skill("Stealth", "DEX", this));
                addSkill(new Skill("Arcana", "INT", this));
                addSkill(new Skill("History", "INT", this));
                addSkill(new Skill("Investigation", "INT", this));
                addSkill(new Skill("Nature", "INT", this));
                addSkill(new Skill("Religion", "INT", this));
                addSkill(new Skill("Animal Handling", "WIS", this));
                addSkill(new Skill("Insignt", "WIS", this));
                addSkill(new Skill("Medicine", "WIS", this));
                addSkill(new Skill("Perception", "WIS", this));
                addSkill(new Skill("Survival", "WIS", this));
                addSkill(new Skill("Deception", "CHA", this));
                addSkill(new Skill("Intimidation", "CHA", this));
                addSkill(new Skill("Performance", "CHA", this));
                addSkill(new Skill("Persuasion", "CHA", this));
                break;
            case ("Pathfinder"):
                addSkill(new Skill("Acrobatics", "DEX", this));
                addSkill(new Skill("Appraise", "INT", this));
                addSkill(new Skill("Bluff", "CHA", this));
                addSkill(new Skill("Climb", "STR", this));
                addSkill(new Skill("Craft", "INT", this));
                addSkill(new Skill("Diplomacy", "CHA", this));
                addSkill(new Skill("Disable Device", "DEX", this));
                addSkill(new Skill("Disguise", "CHA", this));
                addSkill(new Skill("Escape Artist", "DEX", this));
                addSkill(new Skill("Fly", "DEX", this));
                addSkill(new Skill("Handle Animal", "CHA", this));
                addSkill(new Skill("Heal", "WIS", this));
                addSkill(new Skill("Intimidate", "CHA", this));
                addSkill(new Skill("Knowledge (arcana)", "INT", this));
                addSkill(new Skill("Knowledge (dungeoneering)", "INT", this));
                addSkill(new Skill("Knowledge (engineering)", "INT", this));
                addSkill(new Skill("Knowledge (geography)", "INT", this));
                addSkill(new Skill("Knowledge (history)", "INT", this));
                addSkill(new Skill("Knowledge (local)", "INT", this));
                addSkill(new Skill("Knowledge (nature)", "INT", this));
                addSkill(new Skill("Knowledge (nobility)", "INT", this));
                addSkill(new Skill("Knowledge (planes)", "INT", this));
                addSkill(new Skill("Knowledge (religion)", "INT", this));
                addSkill(new Skill("Linguistics", "INT", this));
                addSkill(new Skill("Perception", "WIS", this));
                addSkill(new Skill("Perform", "CHA", this));
                addSkill(new Skill("Profession", "WIS", this));
                addSkill(new Skill("Ride", "DEX", this));
                addSkill(new Skill("Sense Motive", "WIS", this));
                addSkill(new Skill("Sleight of Hand", "DEX", this));
                addSkill(new Skill("Spellcraft", "INT", this));
                addSkill(new Skill("Stealth", "DEX", this));
                addSkill(new Skill("Survival", "WIS", this));
                addSkill(new Skill("Swim", "STR", this));
                addSkill(new Skill("Use Magic Device", "CHA", this));
                break;
            case ("3.5"):
                //populate the array list with default skills
                addSkill(new Skill("Appraise", "INT", this));
                addSkill(new Skill("Balance", "DEX", this));
                addSkill(new Skill("Bluff", "CHA", this));
                addSkill(new Skill("Climb", "STR", this));
                addSkill(new Skill("Concentration", "CON", this));
                addSkill(new Skill("Decipher Script", "INT", this));
                addSkill(new Skill("Diplomacy", "CHA", this));
                addSkill(new Skill("Disable Device", "INT", this));
                addSkill(new Skill("Disguise", "INT", this));
                addSkill(new Skill("Escape Artist", "DEX", this));
                addSkill(new Skill("Forgery", "INT", this));

                addSkill(new Skill("Gather Information", "CHA", this));
                addSkill(new Skill("Handle Animal", "CHA", this));
                addSkill(new Skill("Heal", "WIS", this));
                addSkill(new Skill("Hide", "DEX", this));
                addSkill(new Skill("Intimidate", "CHA", this));
                addSkill(new Skill("Jump", "STR", this));
                addSkill(new Skill("Language", "INT", this));
                addSkill(new Skill("Listen", "WIS", this));
                addSkill(new Skill("Move Silently", "DEX", this));
                addSkill(new Skill("Open Lock", "DEX", this));

                addSkill(new Skill("Ride", "DEX", this));
                addSkill(new Skill("Search", "WIS", this));
                addSkill(new Skill("Sense Motive", "WIS", this));
                addSkill(new Skill("Sleight Of Hand", "DEX", this));
                addSkill(new Skill("Spellcraft", "INT", this));
                addSkill(new Skill("Spot", "WIS", this));
                addSkill(new Skill("Survival", "WIS", this));
                addSkill(new Skill("Swim", "STR", this));
                addSkill(new Skill("Tumble", "DEX", this));
                addSkill(new Skill("Use Magic Device", "CHA", this));
                addSkill(new Skill("Use Rope", "DEX", this));
                break;
            default:
                break;
        }
    }

    public void saveCharacter(Context con) {
        if (getCharacterID().equals("")) {
            while (generateCharacterID(con)) ;
        }
        //save the character
        try {
            File f = new File(getFilePath(con));
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(this);
            out.flush();
        } catch (Exception e) {
            System.out.println("Error saving character: " + e.getMessage());
        }
    }

    public String getEmailableFileName() {
        return getName() + "-" + getRace() + "-" + getCharacterClass();
    }

    public String getFilePath(Context con) {
        return con.getExternalFilesDir(null) + "/characters/" + this.getCharacterID() + ".char";
    }

    public int getAbilityScore(String abilityName) {
        int val = 0;
        switch (abilityName) {
            case ("STR"):
                val = getStrScore() + getStrScoreBonus();
                break;
            case ("DEX"):
                val = getDexScore() + getDexScoreBonus();
                break;
            case ("CON"):
                val = getConScore() + getConScoreBonus();
                break;
            case ("INT"):
                val = getIntScore() + getIntScoreBonus();
                break;
            case ("WIS"):
                val = getWisScore() + getWisScoreBonus();
                break;
            case ("CHA"):
                val = getCharScore() + getCharScoreBonus();
                break;
        }
        return Utility.roundDown((val - 10) / 2.0);
    }

    protected void updateSkills() {
        ArrayList<Skill> skillsToRemove = new ArrayList<>();
        for (int i = 0; i < m_skills.size(); i++) {
            if (m_skills.get(i).getName().equals("")) {
                skillsToRemove.add(m_skills.get(i));
            }
            m_skills.get(i).updatePlayer(this);
        }
        for (int i = 0; i < skillsToRemove.size(); i++) {
            removeSkill(skillsToRemove.get(i));
        }
    }

    public String getCharacterID() {
        return m_characterID;
    }

    public void setCharacterID(String m_characterID) {
        this.m_characterID = m_characterID;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String m_name) {
        this.m_name = m_name;

    }

    public boolean generateCharacterID(Context con) {
        try {
            setCharacterID(UUID.randomUUID().toString());
            return new File(con.getExternalFilesDir(null) + PlayerCharacter.CHARACTER_PATH + "/" + getCharacterID() + ".char").exists();

        } catch (Exception e) {
            System.out.println("Failed to update ID...");
            return true;
        }
    }

    public String getRace() {
        return m_race;
    }

    public void setRace(String m_race) {
        this.m_race = m_race;
    }

    public String getCharacterClass() {
        return m_class;
    }

    public void setCharacterClass(String m_class) {
        this.m_class = m_class;
    }

    public String getLevel() {
        return m_level;
    }

    public void setLevel(String m_level) {
        this.m_level = m_level;
    }

    public String getAlignment() {
        return m_alignment;
    }

    public void setAlignment(String m_alignment) {
        this.m_alignment = m_alignment;
    }

    public String getGender() {
        return m_gender;
    }

    public void setGender(String m_gender) {
        this.m_gender = m_gender;
    }

    public String getHeight() {
        return m_height;
    }

    public void setHeight(String m_height) {
        this.m_height = m_height;
    }

    public String getWeight() {
        return m_weight;
    }

    public void setWeight(String m_weight) {
        this.m_weight = m_weight;
    }

    public String getSize() {
        return m_size;
    }

    public void setSize(String m_size) {
        this.m_size = m_size;
    }

    public String getExp() {
        return m_exp;
    }

    public void setExp(String m_exp) {
        this.m_exp = m_exp;
    }

    public String getSpeed() {
        return m_speed == null ? "0" : m_speed;
    }

    public void setSpeed(String s) {
        m_speed = s;
    }

    public int getStrScore() {
        return m_strScore;
    }

    public void setStrScore(int m_strScore) {
        this.m_strScore = m_strScore;
        updateSkills();
    }

    public int getDexScore() {
        return m_dexScore;
    }

    public void setDexScore(int m_dexScore) {
        this.m_dexScore = m_dexScore;
        updateSkills();
    }

    public int getConScore() {
        return m_conScore;
    }

    public void setConScore(int m_conScore) {
        this.m_conScore = m_conScore;
        updateSkills();
    }

    public int getIntScore() {
        return m_intScore;
    }

    public void setIntScore(int m_intScore) {
        this.m_intScore = m_intScore;
        updateSkills();
    }

    public int getWisScore() {
        return m_wisScore;
    }

    public void setWisScore(int m_wisScore) {
        this.m_wisScore = m_wisScore;
        updateSkills();
    }

    public int getCharScore() {
        return m_charScore;
    }

    public void setCharScore(int m_charScore) {
        this.m_charScore = m_charScore;
        updateSkills();
    }

    public int getStrScoreBonus() {
        return m_strScoreBonus;
    }

    public void setStrScoreBonus(int m_strScoreBonus) {
        this.m_strScoreBonus = m_strScoreBonus;
        updateSkills();
    }

    public int getDexScoreBonus() {
        return m_dexScoreBonus;
    }

    public void setDexScoreBonus(int m_dexScoreBonus) {
        this.m_dexScoreBonus = m_dexScoreBonus;
        updateSkills();
    }

    public int getConScoreBonus() {
        return m_conScoreBonus;
    }

    public void setConScoreBonus(int m_conScoreBonus) {
        this.m_conScoreBonus = m_conScoreBonus;
        updateSkills();
    }

    public int getIntScoreBonus() {
        return m_intScoreBonus;
    }

    public void setIntScoreBonus(int m_intScoreBonus) {
        this.m_intScoreBonus = m_intScoreBonus;
        updateSkills();
    }

    public int getWisScoreBonus() {
        return m_wisScoreBonus;
    }

    public void setWisScoreBonus(int m_wisScoreBonus) {
        this.m_wisScoreBonus = m_wisScoreBonus;
        updateSkills();
    }

    public int getCharScoreBonus() {
        return m_charScoreBonus;
    }

    public void setCharScoreBonus(int m_charScoreBonus) {
        this.m_charScoreBonus = m_charScoreBonus;
        updateSkills();
    }

    public int getStrMod() {
        return Utility.roundDown((getStrScore() + getStrScoreBonus() - 10) / 2.0);
    }

    public int getDexMod() {
        return Utility.roundDown((getDexScore() + getDexScoreBonus() - 10) / 2.0);
    }

    public int getConMod() {
        return Utility.roundDown((getConScore() + getConScoreBonus() - 10) / 2.0);
    }

    public int getIntMod() {
        return Utility.roundDown((getIntScore() + getIntScoreBonus() - 10) / 2.0);
    }

    public int getWisMod() {
        return Utility.roundDown((getWisScore() + getWisScoreBonus() - 10) / 2.0);
    }

    public int getCharMod() {
        return Utility.roundDown((getCharScore() + getCharScoreBonus() - 10) / 2.0);
    }

    public ArrayList<Skill> getSkills() {
        sortSkills();
        return m_skills;
    }

    public Skill getSkillAt(int index) {
        return m_skills.get(index);
    }

    public void addSkill(Skill s) {
        if (!m_skills.contains(s) && !s.getName().equals(""))
            m_skills.add(s);
        updateSkills();
    }

    public void updateSkill(int index, Skill s) {
        m_skills.set(index, s);
    }

    public void removeSkill(Skill s) {
        m_skills.remove(s);
    }

    public void removeSkill(int index) {
        m_skills.remove(index);
    }

    public void sortSkills() {
        Collections.sort(m_skills, new Comparator<Skill>() {
            public int compare(Skill s1, Skill s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
    }

    public SavingThrow getSave(SavingThrow s) {
        if (m_saves == null || m_saves.size() == 0) {
            generateSaves(m_edition);
        }
        if (m_saves.indexOf(s) == -1) {
            m_saves.add(s);
            return s;
        } else {
            return m_saves.get(m_saves.indexOf(s));
        }
    }

    public SavingThrow getSave(String n) {
        return getSave(new SavingThrow(n, m_edition));
    }

    public void setSaves(ArrayList<SavingThrow> s) {
        m_saves = s;
    }


    public String getConditionalSavingThrowMods() {
        return m_conditionalSavingThrowMods;
    }

    public void setConditionalSavingThrowMods(String m_conditionalSavingThrowMods) {
        this.m_conditionalSavingThrowMods = m_conditionalSavingThrowMods;
    }

    public ArmorClass getArmorClass() {
        return m_armorClass;
    }

    public void setArmorClass(ArmorClass m_armorClass) {
        this.m_armorClass = m_armorClass;
    }

    public int getInitiativeBonus() {
        return m_initiativeBonus;
    }

    public void setInitiativeBonus(int m_initiativeBonus) {
        this.m_initiativeBonus = m_initiativeBonus;
    }

    public int getAttackBonus() {
        return m_attackBonus;
    }

    public void setAttackBonus(int m_attackBonus) {
        this.m_attackBonus = m_attackBonus;
    }

    public int getGrapple() {
        return m_grappleBonus + getStrMod() + m_attackBonus;
    }


    public int getGrappleBonus() {
        return m_grappleBonus;
    }

    public void setGrappleBonus(int g) {
        m_grappleBonus = g;
    }

    public int getCurrentHp() {
        return m_currentHp;
    }

    public void setCurrentHp(int m_currentHp) {
        this.m_currentHp = m_currentHp;
    }

    public int getMaxHp() {
        return m_maxHp;
    }

    public void setMaxHp(int m_maxHp) {
        this.m_maxHp = m_maxHp;
    }

    public ArrayList<Weapon> getWeapons() {
        return m_weapons;
    }

    public Weapon getWeaponAt(int index) {
        return m_weapons.get(index);
    }

    public void setWeaponAt(int index, Weapon w) {
        m_weapons.set(index, w);
    }

    public void addWeapon(Weapon w) {
        m_weapons.add(w);
    }

    public void removeWeaponAt(int index) {
        m_weapons.remove(index);
    }

    public void removeWeapon(Weapon w) {
        m_weapons.remove(w);
    }

    public int getCasterLevel() {
        try {
            return Integer.parseInt(getLevel());
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<Domain> getDomains() {
        return m_domains;
    }

    public Domain getDomainAt(int index) {
        return m_domains.get(index);
    }

    public void addDomain(Domain d) {
        m_domains.add(d);
    }

    public void removeDomain(Domain d) {
        m_domains.remove(d);
    }

    public int getRebukeTimes() {
        return m_rebukeTimes;
    }

    public void setRebukeTimes(int m_rebukeTimes) {
        this.m_rebukeTimes = m_rebukeTimes;
    }

    public int getRebukeCheck() {
        return m_rebukeCheck;
    }

    public void setRebukeCheck(int m_rebukeCheck) {
        this.m_rebukeCheck = m_rebukeCheck;
    }

    public String getRebukeDamage() {
        return m_rebukeDamage;
    }

    public void setRebukeDamage(String m_rebukeDamage) {
        this.m_rebukeDamage = m_rebukeDamage;
    }

    public int getArcaneFailure() {
        return m_arcaneFailure;
    }

    public void setArcaneFailure(int m_arcaneFailure) {
        this.m_arcaneFailure = m_arcaneFailure;
    }

    public ArrayList<SpellList> getSpellsLists() {
        return m_spellsLists;
    }

    public void removeSpellListAt(int index) {
        m_spellsLists.remove(index);
    }

    public SpellList getSpellListAt(int index) {
        return m_spellsLists.get(index);
    }

    public void addSpellList(SpellList s) {
        m_spellsLists.add(s);
    }

    public void setSpellListAt(int index, SpellList sl) {
        m_spellsLists.set(index, sl);
    }

    public void removeSpellList(SpellList s) {
        m_spellsLists.remove(s);
    }

    public String getCurrency() {
        return m_currency;
    }

    public void setCurrency(String m_currency) {

        this.m_currency = m_currency;
    }

    public ArrayList<InventoryItem> getItems() {
        return m_items;
    }

    public InventoryItem getItemAt(int index) {
        return m_items.get(index);
    }

    public int getInventoryWeight() {
        int w = 0;
        for (int i = 0; i < m_items.size(); i++) {
            w += m_items.get(i).getWeight();
        }
        return w;
    }

    public void addItem(InventoryItem i) {
        m_items.add(i);
    }

    public void setItemAt(int index, InventoryItem s) {
        m_items.set(index, s);
    }

    public void removeFromInventory(int i) {
        m_items.remove(i);
    }

    public ArrayList<Companion> getCompanions() {
        return m_companions;
    }

    public Companion getCompanionAt(int index) {
        return m_companions.get(index);
    }

    public void addCompanion(Companion c) {
        m_companions.add(c);
    }

    public void removeCompanion(Companion c) {
        m_companions.remove(c);
    }

    public void setCompanionAt(int index, Companion c) {
        m_companions.set(index, c);
    }

    public ArrayList[] getAbilities() {
        return m_abilities;
    }

    public void addAbility(Ability ability, int index) {
        if (index == -1) {
            if (ability.getType().equalsIgnoreCase("feat")) {
                m_abilities[0].add(ability);

            } else if (ability.getType().equalsIgnoreCase("class feature")) {
                m_abilities[1].add(ability);
            } else if (ability.getType().equalsIgnoreCase("racial trait")) {
                m_abilities[2].add(ability);
            } else if (ability.getType().equalsIgnoreCase("language")) {
                m_abilities[3].add(ability);
            }
        } else {
            if (ability.getType().equalsIgnoreCase("feat")) {
                m_abilities[0].set(index, ability);

            } else if (ability.getType().equalsIgnoreCase("class feature")) {
                m_abilities[1].set(index, ability);
            } else if (ability.getType().equalsIgnoreCase("racial trait")) {
                m_abilities[2].set(index, ability);
            } else if (ability.getType().equalsIgnoreCase("language")) {
                m_abilities[3].set(index, ability);
            }
        }
    }

    public Ability getAbility(int typeIndex, int index) {
        return m_abilities[typeIndex].get(index);
    }

    public void removeAbility(int typeIndex, int index) {
        m_abilities[typeIndex].remove(index);
    }

    public String getEdition() {
        return m_edition;
    }

    public void setEdition(String edition) {
        m_edition = edition;
    }

    public Note getNoteAt(int index) {
        return m_notes.get(index);
    }

    public ArrayList<Note> getNotes() {
        return m_notes;
    }

    public void addNote(Note n) {
        m_notes.add(n);
    }

    public void removeNote(Note n) {
        m_notes.remove(n);
    }

    public void removeNoteAt(int index) {
        m_notes.remove(index);
    }

    public void updateNote(int index, Note n) {
        m_notes.set(index, n);
    }

    public int getProficiency() {
        return m_proficiency;
    }

    public void setProficiency(int p) {
        m_proficiency = p;
    }

    public int getProficiency(int p) {
        switch (p) {
            case 1:
                return getProficiency() / 2;
            case 2:
                return getProficiency();
            case 3:
                return getProficiency() * 2;
            default:
                return 0;
        }
    }

    public int getSpellMod() {
        return getModifier(m_spellAbility);
    }

    public String getSpellAbility() {
        return m_spellAbility;
    }

    public void setSpellAbility(String a) {
        m_spellAbility = a;
    }

    public int getModifier(String name) {
        switch (name) {
            case "STR":
                return getStrMod();
            case "DEX":
                return getDexMod();
            case "CON":
                return getConMod();
            case "INT":
                return getIntMod();
            case "WIS":
                return getWisMod();
            case "CHA":
                return getCharMod();
            default:
                return 0;
        }
    }

    public void updateSave(SavingThrow st) {
        switch (st.getName()) {

            case "STR":
                m_saves.set(0, st);
                break;
            case "DEX":
                m_saves.set(1, st);
                break;
            case "CON":
                m_saves.set(2, st);
                break;
            case "INT":
                m_saves.set(3, st);
                break;
            case "WIS":
                m_saves.set(4, st);
                break;
            case "CHA":
                m_saves.set(5, st);
                break;
        }
    }

    public boolean isCompanion() {
        return m_isCompanion;
    }

    public void setIsCompanion(boolean isCompanion) {
        m_isCompanion = isCompanion;
    }
}
