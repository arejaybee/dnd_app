/*
    Written by Robert Bradshaw
    on 1/1/2019
 */
package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class SpellList implements Serializable {

    public static final long serialVersionUID = 7L;
    private int m_known;
    private int m_daily;
    private int m_used;
    private int m_level;
    private String m_class;
    private ArrayList<Spell> m_spells;
    private boolean m_hidden;

    public SpellList() {
        setKnown(0);
        setUsed(0);
        setDaily(0);
        setLevel(0);
        setSpellClass("");
        setHidden(true);
        m_spells = new ArrayList<>();
    }

    public SpellList(int l) {
        setKnown(0);
        setUsed(0);
        setDaily(0);
        setHidden(true);
        setLevel(l);
        setSpellClass("");
        m_spells = new ArrayList<>();
    }

    public int getKnown() {
        return m_known;
    }

    public void setKnown(int m_known) {
        this.m_known = m_known;
    }

    public int getDaily() {
        return m_daily;
    }

    public void setDaily(int m_daily) {
        this.m_daily = m_daily;
    }

    public int getUsed() {
        return m_used;
    }

    public void setUsed(int u) {
        this.m_used = u;
    }

    public int getLevel() {
        return m_level;
    }

    public void setLevel(int m_level) {
        this.m_level = m_level;
    }

    public String getSpellClass() {
        return m_class;
    }

    public void setSpellClass(String m_class) {
        this.m_class = m_class;
    }

    public ArrayList<Spell> getSpells() {
        return m_spells;
    }

    public Spell getSpellAt(int i) {
        return m_spells.get(i);
    }

    public void setSpellAt(int i, Spell s) {
        m_spells.set(i, s);
    }

    public void addSpell(Spell s) {
        m_spells.add(s);
    }

    public void removeSpell(Spell s) {
        m_spells.remove(s);
    }

    public int getIndexOfSpell(Spell s) {
        return m_spells.indexOf(s);
    }

    public boolean isHidden() {
        return m_hidden;
    }

    public void setHidden(boolean b) {
        m_hidden = b;
    }
}
