/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.DataObjects;

import com.app.arejaybee.character_sheet.Utility;

import java.io.Serializable;

public class Skill implements Serializable {

    public static final long serialVersionUID = 6L;
    private int m_rank;
    private int m_bonus;
    private String m_name;
    private String m_ability;
    private boolean m_isClassSkill;
    private PlayerCharacter rob;
    private EnumHelper.PROFICIENCY m_proficiency;

    public Skill(PlayerCharacter p) {
        setRank(0);
        setBonus(0);
        setName("");
        setAbility("");
        setProficiency(EnumHelper.PROFICIENCY.NONE);
        rob = p;
        setIsClassSkill(false);

    }

    public Skill(String n, String a, PlayerCharacter p) {
        setName(n);
        setAbility(a);
        setRank(0);
        setBonus(0);
        setIsClassSkill(false);
        setProficiency(EnumHelper.PROFICIENCY.NONE);
        rob = p;
    }

    public boolean Equals(Object s) {
        if (s instanceof Skill) {
            return ((Skill) s).getName().equals(getName()) && ((Skill) s).getAbility().equals(getAbility());
        }
        return false;
    }

    public void updatePlayer(PlayerCharacter c) {
        rob = c;
    }

    public int getMod() {
        int rank = getRank();
        switch (rob.getEdition()) {
            case ("5e"):
                return rank + getBonus() + rob.getAbilityScore(m_ability);
            case ("3.5"):
                if (!isClassSkill()) {
                    rank = Utility.roundDown(rank / 2.0);
                }
                return rank + getBonus() + rob.getAbilityScore(m_ability);
            case ("Pathfinder"):
                if (isClassSkill() && rank > 0) {
                    rank += 3;
                }
                return rank + getBonus() + rob.getAbilityScore(m_ability);
            default:
                return -99;
        }
    }

    public int getBonus() {
        return m_bonus;
    }

    public void setBonus(int m_bonus) {
        this.m_bonus = m_bonus;
    }

    public int getRank() {
        return m_rank;
    }

    public void setRank(int m_rank) {
        this.m_rank = m_rank;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String m_name) {
        if (m_name.length() > 1) {
            m_name = m_name.substring(0, 1).toUpperCase() + m_name.substring(1);
        } else {
            m_name = m_name.toUpperCase();
        }
        this.m_name = String.format("%-" + 30 + "." + 30 + "s", m_name);
    }

    public EnumHelper.PROFICIENCY getProficiency() {
        return m_proficiency;
    }

    public void setProficiency(EnumHelper.PROFICIENCY proficiency) {
        this.m_proficiency = proficiency;
    }

    public String getAbility() {
        return m_ability;
    }

    public void setAbility(String m_ability) {
        this.m_ability = m_ability;
    }

    public int getAbilityScore() {
        return rob.getAbilityScore(m_ability);
    }

    public boolean isClassSkill() {
        return m_isClassSkill;
    }

    public void setIsClassSkill(boolean m_isClassSkill) {
        this.m_isClassSkill = m_isClassSkill;
    }

}
