package model;

public class CharacterInventory {
    private int slotNumber;
    private Characters characters;
    private Item item;
    private int quantity;

    public CharacterInventory(int slotNumber, Characters characters, Item item, int quantity) {
        this.slotNumber = slotNumber;
        this.characters = characters;
        this.item = item;
        this.quantity = quantity;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Characters getCharacter() {
        return characters;
    }

    public void setCharacter(Characters characters) {
        this.characters = characters;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
