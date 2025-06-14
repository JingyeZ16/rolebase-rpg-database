package model;

public enum Job {
    WARRIOR("Warrior"),
    MAGE("Mage"),
    THIEF("Thief"),
    HUNTER("Hunter"),
    NECROMANCER("Necromancer");

    private final String name;

    Job(String name) {
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