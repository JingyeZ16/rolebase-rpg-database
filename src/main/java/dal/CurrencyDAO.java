package dal;

import model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyDAO {

    public static Currency create(
            Connection cxn,
            String currencyName,
            int cap,
            int weeklyCap
    ) throws SQLException {
        String insertCurrencySql = "INSERT INTO currency (CurrencyName, Cap, WeeklyCap) values (?,?,?) ";
        try (PreparedStatement ps = cxn.prepareStatement(insertCurrencySql)) {
            ps.setString(1, currencyName);
            ps.setInt(2, cap);
            ps.setInt(3, weeklyCap);
            ps.executeUpdate();
            return new Currency(
                    currencyName,
                    cap,
                    weeklyCap
            );
        }
    }

    public static Currency getCurrencyByName(
            Connection cxn,
            String currencyName
    ) throws SQLException {
        String selectCurrencySql = "SELECT * FROM currency WHERE CurrencyName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCurrencySql)) {
            ps.setString(1, currencyName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Currency(
                            currencyName,
                            rs.getInt("Cap"),
                            rs.getInt("WeeklyCap")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static Currency updateCurrencyCapByName(
            Connection cxn,
            int newCap,
            String currencyName
    ) throws SQLException {
        String updateCurrencyCapSql = "UPDATE currency SET Cap = ? WHERE CurrencyName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(updateCurrencyCapSql)) {
            Currency currency = getCurrencyByName(cxn, currencyName);
            ps.setInt(1, newCap);
            ps.setString(2, currencyName);
            ps.executeUpdate();

            return new Currency(
                    currencyName,
                    newCap,
                    currency.getWeeklyCap()
            );
        }
    }


}
