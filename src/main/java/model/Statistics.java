package model;

public enum Statistics {
    STRENGTH("Strength"),
    DEXTERITY("Dexterity"),
    VITALITY("Vitality"),
    INTELLIGENCE("Intelligence"),
    HP("Hp"),
    MP("Mp"),
    CRITICALRATE("CriticalRate"),
    EVASIONRATE("EvasionRate");

    private final String name;

    Statistics(String name) {
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