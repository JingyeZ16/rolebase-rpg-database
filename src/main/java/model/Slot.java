package model;

public enum Slot {
    HELMET("Helmet"),
    ARMOR("Armor"),
    BOOTS("Boots"),
    GLOVES("Gloves"),
    WEAPON("Weapon");

    private final String name;

    Slot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}