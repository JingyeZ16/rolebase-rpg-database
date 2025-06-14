package model;

public class Player {
    private int playerID;
    private String fullName;
    private String Email;

    public Player(int playerID, String fullName, String Email) {
        this.playerID = playerID;
        this.fullName = fullName;
        this.Email = Email;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
