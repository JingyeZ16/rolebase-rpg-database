package model;

public class Item {
    private int itemID;
    private String itemName;
    private int level;
    private int maxStackSize;
    private int price;
    private int requiredLevel;

    public Item(int itemID, String itemName, int level, int maxStackSize, int price, int requiredLevel) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.level = level;
        this.maxStackSize = maxStackSize;
        this.price = price;
        this.requiredLevel = requiredLevel;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
}
