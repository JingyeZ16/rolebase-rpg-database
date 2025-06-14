package model;

public class CharacterJob {
    private Job job;
    private Characters characters;
    private int level;
    private int xp;
    private boolean lock;

    public CharacterJob(Job job, Characters characters, int level, int xp, boolean lock) {
        this.job = job;
        this.characters = characters;
        this.level = level;
        this.xp = xp;
        this.lock = lock;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Characters getCharacter() {
        return characters;
    }

    public void setCharacter(Characters characters) {
        this.characters = characters;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
