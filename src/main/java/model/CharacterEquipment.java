package model;

public class CharacterEquipment {
    private Characters characters;
    private Slot slot;
    private Item item;

    public CharacterEquipment(Characters characters, Slot slot, Item item) {
        this.characters = characters;
        this.slot = slot;
        this.item = item;
    }
    public Characters getCharacter() {
        return characters;
    }

    public void setCharacter(Characters characters) {
        this.characters = characters;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


}
