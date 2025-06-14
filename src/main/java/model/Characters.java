package model;

public class Characters {
    private int CharacterID;
    private Player player;
    private String firstName;
    private String lastName;
    private Race race;
    private Clan clan;

    public Characters(int characterID, Player player, String firstName, String lastName, Race race, Clan clan) {
        CharacterID = characterID;
        this.player = player;
        this.firstName = firstName;
        this.lastName = lastName;
        this.race = race;
        this.clan = clan;
    }

    public int getCharacterID() {
        return CharacterID;
    }

    public void setCharacterID(int characterID) {
        CharacterID = characterID;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
}
