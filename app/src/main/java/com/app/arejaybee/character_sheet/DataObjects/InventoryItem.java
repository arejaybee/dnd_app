package com.app.arejaybee.character_sheet.DataObjects;

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String m_name;
    private String m_description;
    private SlotEnum m_slot;
    private int m_weight;
    private String m_cost;

    public InventoryItem() {
        setName("");
        setDescription("");
        setSlot(SlotEnum.NONE);
    }

    public String getName() {
        return m_name;
    }

    public void setName(String Name) {
        m_name = Name;
    }

    public String getSlotEnum() {
        return m_slot.name();
    }

    public int getSlotIndex() {
        return m_slot.ordinal();
    }

    public SlotEnum getSlot() {
        return m_slot;
    }

    public void setSlot(SlotEnum slot) {
        m_slot = slot;
    }

    public String getDescription() {

        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public int getWeight() {
        return m_weight;
    }

    public void setWeight(int weight) {
        m_weight = weight;
    }

    public String getCost() {
        return m_cost;
    }

    public void setCost(String cost) {
        m_cost = cost;
    }

    public enum SlotEnum {NONE, BELT, BODY, CHEST, EYES, FEET, HANDS, HEAD, HEADBAND, NECK, RING, SHIELD, SHOULDERS}

}