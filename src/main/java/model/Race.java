package model;

public enum Race {
    ELF("Elf"),
    ORC("Orc"),
    HUMAN("Human"),
    DWARF("Dwarf"),
    GOBLIN("Goblin"),
    HALFLING("Halfling");

    private final String name;

    Race(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}