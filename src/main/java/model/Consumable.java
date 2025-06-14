package model;

public class Consumable extends Item {
    private String description;

    public Consumable(int itemID, String itemName, int level, int maxStackSize, int price, int requiredLevel, String description) {
        super(itemID, itemName, level, maxStackSize, price, requiredLevel);
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
