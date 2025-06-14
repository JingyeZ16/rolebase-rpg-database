package model;

public class CharacterCurrency {
    private Currency currency;
    private Characters characters;
    private int totalAmount;
    private int weeklyAmount;
    public CharacterCurrency(Currency currency, Characters characters, int totalAmount, int weeklyAmount) {
        this.currency = currency;
        this.characters = characters;
        this.totalAmount = totalAmount;
        this.weeklyAmount = weeklyAmount;
    }

    public int getWeeklyAmount() {
        return weeklyAmount;
    }

    public void setWeeklyAmount(int weeklyAmount) {
        this.weeklyAmount = weeklyAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Characters getCharacter() {
        return characters;
    }

    public void setCharacter(Characters characters) {
        this.characters = characters;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
