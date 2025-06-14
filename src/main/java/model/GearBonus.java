package model;

public class GearBonus {
    private Item item;
    private Statistics statistics;
    private int value;

    public GearBonus(Item item, Statistics statistics, int value) {
        this.item = item;
        this.statistics = statistics;
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
}
