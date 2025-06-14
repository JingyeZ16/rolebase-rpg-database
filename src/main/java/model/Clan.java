package model;

public class Clan {
    private Race race;
    private String clan;

    public Clan(Race race, String clan) {
        this.race = race;
        this.clan = clan;
    }
    public Race getRace() {
        return race;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
