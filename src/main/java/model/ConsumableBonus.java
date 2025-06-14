package model;

public class ConsumableBonus {
    private Consumable consumable;
    private Statistics statistics;
    private int value;
    private int bonusCap;
    private float bonusPercentage;

    public ConsumableBonus(Consumable consumable, Statistics statistics, int value, int bonusCap, float bonusPercentage) {
        this.consumable = consumable;
        this.statistics = statistics;
        this.value = value;
        this.bonusCap = bonusCap;
        this.bonusPercentage = bonusPercentage;
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getBonusCap() {
        return bonusCap;
    }

    public void setBonusCap(int bonusCap) {
        this.bonusCap = bonusCap;
    }

    public float getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(float bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }
}
