package dal;

import model.CharacterCurrency;
import model.Characters;
import model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterCurrencyDAO {

    public static CharacterCurrency create(
            Connection cxn,
            Currency currency,
            Characters characters,
            int totalAmount,
            int weeklyAmount
    ) throws SQLException {
        String insertCharacterCurrencySQL = "INSERT INTO CharacterCurrency (CurrencyName, CharacterID, TotalAmount, WeeklyAmount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertCharacterCurrencySQL)) {
            ps.setString(1, currency.getCurrencyName());
            ps.setInt(2, characters.getCharacterID());
            ps.setInt(3, totalAmount);
            ps.setInt(4, weeklyAmount);
            ps.executeUpdate();
            return new CharacterCurrency(
                    currency,
                    characters,
                    totalAmount,
                    weeklyAmount
            );
        }
    }

    public static CharacterCurrency getCharacterCurrencyByIdAndName(
            Connection cxn,
            Characters character,
            String currencyName
    ) throws SQLException {
        String getCharacterCurrencySQL = "SELECT * FROM CharacterCurrency WHERE CharacterID = ? AND CurrencyName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getCharacterCurrencySQL)) {
            ps.setInt(1, character.getCharacterID());
            ps.setString(2, currencyName);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Currency currency = CurrencyDAO.getCurrencyByName(cxn, currencyName);
                    return new CharacterCurrency(
                            currency,
                            character,
                            rs.getInt("TotalAmount"),
                            rs.getInt("WeeklyAmount")
                    );
                } else {
                    return null;
                }
            }
        }
    }


    public static List<CharacterCurrency> getCharacterCurrencies(
            Connection cxn,
            Characters character
    ) throws SQLException {

        String getCharacterCurrenciesSql = "SELECT * FROM CharacterCurrency WHERE CharacterID = ?";

        try (PreparedStatement ps = cxn.prepareStatement(getCharacterCurrenciesSql)) {
            ps.setInt(1, character.getCharacterID());
            try (ResultSet rs = ps.executeQuery()) {
                List<CharacterCurrency> currencies = new ArrayList<>();
                while (rs.next()) {
                    Currency currency = CurrencyDAO.getCurrencyByName(cxn, rs.getString("CurrencyName"));
                    currencies.add(
                            new CharacterCurrency(
                                    currency,
                                    character,
                                    rs.getInt("TotalAmount"),
                                    rs.getInt("WeeklyAmount")
                            )
                    );
                }
                return currencies;
            }
        }
    }
}
