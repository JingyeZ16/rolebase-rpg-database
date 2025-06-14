package dal;

import model.Consumable;
import model.ConsumableBonus;
import model.Item;
import model.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumableBonusDAO {
    public static ConsumableBonus create(
            Connection cxn,
            Consumable consumable,
            Statistics statistics,
            int value,
            int bounsCap,
            float bonusPercentage
    ) throws SQLException {
        String insertConsumableBonusSql = "insert into ConsumableBonus (ItemID, StatsName, Value, BonusCap, BonusPercentage) values (?,?,?,?,?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertConsumableBonusSql)) {
            ps.setInt(1, consumable.getItemID());
            ps.setString(2, statistics.getName());
            ps.setInt(3, value);
            ps.setInt(4, bounsCap);
            ps.setFloat(5, bonusPercentage);
            ps.executeUpdate();
            return new ConsumableBonus(
                    consumable,
                    statistics,
                    value,
                    bounsCap,
                    bonusPercentage
            );
        }
    }

    public static ConsumableBonus getConsumableBonusByItemIDAndStatsName(
            Connection cxn,
            Consumable consumable,
            Statistics statistics
    ) throws SQLException {
        String selectConsumableBonusSql = "select * from ConsumableBonus where ItemID = ? and StatsName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectConsumableBonusSql)) {
            ps.setInt(1, consumable.getItemID());
            ps.setString(2, statistics.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ConsumableBonus(
                            consumable,
                            statistics,
                            rs.getInt("Value"),
                            rs.getInt("BonusCap"),
                            rs.getFloat("BonusPercentage")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static List<ConsumableBonus> getConsumableBonusByItemID(
            Connection cxn,
            Item item
    ) throws SQLException {
        String selectConsumableBonusSql = "select * from ConsumableBonus where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectConsumableBonusSql)) {
            ps.setInt(1, item.getItemID());
            try (ResultSet rs = ps.executeQuery()) {
                List<ConsumableBonus> bonuses = new ArrayList<>();
                Consumable consumable = ConsumableDAO.getConsumableById(cxn, item);
                while (rs.next()) {
                    bonuses.add(new ConsumableBonus(
                            consumable,
                            Statistics.valueOf(rs.getString("StatsName").toUpperCase()),
                            rs.getInt("Value"),
                            rs.getInt("BonusCap"),
                            rs.getFloat("BonusPercentage")
                    ));
                }
                return bonuses;
            }
        }
    }

}
