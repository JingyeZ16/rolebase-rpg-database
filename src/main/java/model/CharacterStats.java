package model;

public class CharacterStats {
    private Statistics statistics;
    private Characters characters;
    private int value;

    public CharacterStats(Statistics statistics, Characters characters, int value) {
        this.statistics = statistics;
        this.characters = characters;
        this.value = value;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Characters getCharacter() {
        return characters;
    }

    public void setCharacter(Characters characters) {
        this.characters = characters;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
