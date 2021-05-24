package com.app.arejaybee.character_sheet.DataObjects;

import android.content.Context;

public class Companion extends PlayerCharacter {

    PlayerCharacter m_owner;
    int indexForOwner;

    public Companion(PlayerCharacter owner, int index, String edition, boolean generateSkills) {
        super(edition, generateSkills);
        setOwner(owner);
        indexForOwner = index;
    }

    @Override
    public void saveCharacter(Context con) {
        m_owner.setCompanionAt(indexForOwner, this);
        m_owner.saveCharacter(con);
    }

    public PlayerCharacter getOwner() {
        return m_owner;
    }

    public void setOwner(PlayerCharacter pc) {
        m_owner = pc;
    }

    public boolean hasOwner() {
        return getOwner() != null;
    }

}
