package model;

import java.util.Map;

public class EquipmentStatRow {
    String itemName;
    String slot;
    Map<String, Integer> statValues;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Map<String, Integer> getStatValues() {
        return statValues;
    }

    public void setStatValues(Map<String, Integer> statValues) {
        this.statValues = statValues;
    }
}
