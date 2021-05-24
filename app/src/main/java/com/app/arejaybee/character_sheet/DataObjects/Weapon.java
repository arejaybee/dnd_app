/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class Weapon implements Serializable {

    public static final long serialVersionUID = 8L;
    private String m_name;
    private String m_type;
    private String m_toHit;
    private String m_damage;
    private String m_range;
    private String m_critical;
    private String m_notes;

    public Weapon() {
        setName("");
        setType("");
        setToHit("0");
        setDamage("0d0+0");
        setRange("0ft");
        setCritical("19-20 x2");
        setNotes("");
    }

    public String getName() {
        return m_name;
    }

    public void setName(String m_name) {
        this.m_name = m_name;
    }

    public String getType() {
        return m_type;
    }

    public void setType(String m_type) {
        this.m_type = m_type;
    }

    public String getToHit() {
        return m_toHit;
    }

    public void setToHit(String m_toHit) {
        this.m_toHit = m_toHit;
    }

    public String getDamage() {
        return m_damage;
    }

    public void setDamage(String m_damage) {
        this.m_damage = m_damage;
    }

    public String getRange() {
        return m_range;
    }

    public void setRange(String m_range) {
        this.m_range = m_range;
    }

    public String getCritical() {
        return m_critical;
    }

    public void setCritical(String m_critical) {
        this.m_critical = m_critical;
    }

    public String getNotes() {
        return m_notes;
    }

    public void setNotes(String m_note) {
        this.m_notes = m_note;
    }
}
