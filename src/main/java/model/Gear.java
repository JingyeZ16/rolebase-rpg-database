package model;


public class Gear extends Item {
    private Slot slot;

    public Gear(int itemID, String itemName, int level, int maxStackSize, int price, int requiredLevel, Slot slot) {
        super(itemID, itemName, level, maxStackSize, price, requiredLevel);
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
