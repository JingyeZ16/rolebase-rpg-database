package model;

public class Weapon extends Item {
    private Job job;
    private int damage;

    public Weapon(int itemID, String itemName, int level, int maxStackSize, int price, int requiredLevel, Job job, int damage) {
        super(itemID, itemName, level, maxStackSize, price, requiredLevel);
        this.job = job;
        this.damage = damage;

    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
