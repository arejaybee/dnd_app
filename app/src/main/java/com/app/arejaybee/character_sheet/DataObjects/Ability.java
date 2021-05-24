package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class Ability implements Serializable {
    private String m_title;
    private String m_description;
    private String m_type;

    public Ability() {
        setTitle("");
        setDescription("");
        setType("Trait");
    }

    public Ability(String title, String type) {
        setTitle(title);
        setType(type);
    }

    public Ability(String title, String type, String description) {
        setTitle(title);
        setType(type);
        setDescription(description);
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String title) {
        m_title = title;
    }

    public String getType() {
        return m_type;
    }

    public void setType(String type) {
        m_type = type;
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }


}
