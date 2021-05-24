package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class Note implements Serializable {
    private String m_title;
    private String m_text;

    public Note() {
        setTitle("New Note");
        setText("");
    }

    public Note(String t, String txt) {
        setTitle(t);
        setText(txt);
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String t) {
        m_title = t;
    }

    public String getText() {
        return m_text;
    }

    public void setText(String t) {
        m_text = t;
    }
}
