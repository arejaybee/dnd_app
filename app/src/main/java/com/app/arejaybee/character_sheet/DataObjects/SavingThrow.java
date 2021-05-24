package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class SavingThrow implements Serializable {
    public static final long serialVersionUID = 5L;
    private int m_base;
    private int m_bonus;
    private int m_classBonus;
    private EnumHelper.PROFICIENCY m_proficiency;
    private String m_name;
    private String edition;

    public SavingThrow(String name, String edition) {
        m_name = name;
        setBase(0);
        setClassBonus(0);
        setBonus(0);
        m_proficiency = EnumHelper.PROFICIENCY.NONE;
        this.edition = edition;
    }

    public String getName() {
        return m_name;
    }

    public int getBase() {
        return m_base;
    }

    public void setBase(int m_base) {
        this.m_base = m_base;
    }

    public int getClassBonus() {
        return m_classBonus;
    }

    public void setClassBonus(int i) {
        this.m_classBonus = i;
    }

    public EnumHelper.PROFICIENCY getProficiency() {
        return m_proficiency == null ? EnumHelper.PROFICIENCY.NONE : m_proficiency;
    }

    public void setProficiency(EnumHelper.PROFICIENCY proficiency) {
        this.m_proficiency = proficiency;
    }

    public int getBonus() {
        return m_bonus;
    }

    public void setBonus(int m_bonus) {
        this.m_bonus = m_bonus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SavingThrow) {
            return ((SavingThrow) obj).getName().equalsIgnoreCase(getName());
        }
        return false;
    }

    public int getTotal() {
        switch (edition) {
            case ("5e"):
                int p = m_proficiency.ordinal();
                return m_base + m_bonus;
            case ("3.5"):
                return m_base + m_classBonus + m_bonus;
            default:
                return -99;
        }
    }
}
