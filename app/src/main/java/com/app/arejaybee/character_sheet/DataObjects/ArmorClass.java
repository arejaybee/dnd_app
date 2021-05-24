/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class ArmorClass implements Serializable {

    public static final long serialVersionUID = 4L;
    private int m_armor;
    private int m_shield;
    private int m_size;
    private int m_natural;
    private int m_deflection;
    private int m_misc;

    public ArmorClass() {
        setArmor(0);
        setShield(0);
        setMisc(0);
    }

    ArmorClass(int armor, int shield, int size, int natural, int deflection, int misc) {
        setArmor(armor);
        setShield(shield);
        setMisc(misc);
    }

    public int getMod() {
        return getArmor() + getShield() + getMisc();
    }

    public int getArmor() {
        return m_armor;
    }

    public void setArmor(int m_armor) {
        this.m_armor = m_armor;
    }

    public int getShield() {
        return m_shield;
    }

    public void setShield(int m_shield) {
        this.m_shield = m_shield;
    }

    public int getMisc() {
        return m_misc;
    }

    public void setMisc(int m_misc) {
        this.m_misc = m_misc;
    }
}
