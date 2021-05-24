/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class Spell implements Serializable {

    public static final long serialVersionUID = 7L;

    private String m_name;
    private String m_description;
    private int m_level;
    private int m_prep;

    public Spell(int level) {
        setName("");
        setDescription("");
        setLevel(level);
        setPrep(0);
    }

    public String getName() {
        return m_name;
    }

    public void setName(String n) {
        m_name = n;
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String d) {
        m_description = d;
    }

    public int getLevel() {
        return m_level;
    }

    public void setLevel(int i) {
        m_level = i;
    }

    public int getPrep() {
        return m_prep;
    }

    public void setPrep(int i) {
        m_prep = i;
    }
}
