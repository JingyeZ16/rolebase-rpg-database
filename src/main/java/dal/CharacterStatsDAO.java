package dal;

import model.CharacterStats;
import model.Characters;
import model.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterStatsDAO {

    public static CharacterStats create(
            Connection cxn,
            Statistics statistics,
            Characters characters,
            int value
    ) throws SQLException {
        String insertCharacterStatsSQL = "INSERT INTO CharacterStats (StatsName, CharacterID, Value) VALUES (?,?,?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertCharacterStatsSQL)) {
            ps.setString(1, statistics.getName());
            ps.setInt(2, characters.getCharacterID());
            ps.setInt(3, value);
            ps.executeUpdate();
            return new CharacterStats(statistics, characters, value);
        }
    }

    public static CharacterStats getCharacterStatsByIDAndStatsName(
            Connection cxn,
            Characters character,
            Statistics statistics
    ) throws SQLException {
        String selectCharacterStatsSql = "SELECT * FROM CharacterStats WHERE CharacterID = ? AND StatsName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCharacterStatsSql)) {
            ps.setInt(1, character.getCharacterID());
            ps.setString(2, statistics.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    return new CharacterStats(
                            statistics,
                            character,
                            rs.getInt("Value")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static List<CharacterStats> getCharacterStatsById(
            Connection cxn,
            Characters character
    ) throws SQLException {
        String selectCharacterStatsSql = "SELECT * FROM CharacterStats WHERE CharacterID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCharacterStatsSql)) {
            ps.setInt(1, character.getCharacterID());
            try (ResultSet rs = ps.executeQuery()) {
                List<CharacterStats> characterStatsList = new ArrayList<>();
                while (rs.next()) {
                    characterStatsList.add(
                            new CharacterStats(
                                    Statistics.valueOf(rs.getString("StatsName").toUpperCase()),
                                    character,
                                    rs.getInt("Value")
                            )
                    );
                }
                return characterStatsList;
            }
        }
    }

    public static void updateStat(Connection cxn, CharacterStats cs) throws SQLException {
        String updateStatsSQL = "UPDATE CharacterStats SET Value = ? WHERE CharacterID = ? AND StatsName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(updateStatsSQL)) {
            ps.setInt(1, cs.getValue());
            ps.setInt(2, cs.getCharacter().getCharacterID());
            ps.setString(3, cs.getStatistics().getName());
            ps.executeUpdate();
        }
    }
}
