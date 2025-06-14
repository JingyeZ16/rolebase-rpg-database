package model;

public class Currency {
    private String currencyName;
    private int cap;
    private int weeklyCap;

    public Currency(String currencyName, int cap, int weeklyCap) {
        this.currencyName = currencyName;
        this.cap = cap;
        this.weeklyCap = weeklyCap;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getWeeklyCap() {
        return weeklyCap;
    }

    public void setWeeklyCap(int weeklyCap) {
        this.weeklyCap = weeklyCap;
    }
}
