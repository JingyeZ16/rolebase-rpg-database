package dal;

import model.GearBonus;
import model.Item;
import model.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GearBonusDAO {

    public static GearBonus create(
            Connection cxn,
            Item item,
            Statistics statistics,
            int value
    ) throws SQLException {
        String insertGearBonusSql = "insert into GearBonus (ItemID,StatsName, Value) values (?,?,?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertGearBonusSql)) {
            ps.setInt(1, item.getItemID());
            ps.setString(2, statistics.getName());
            ps.setInt(3, value);
            ps.executeUpdate();
            return new GearBonus(item, statistics, value);
        }

    }

    public static GearBonus getGearBonusByIdAndStatsName(
            Connection cxn,
            Item item,
            String statsName
    ) throws SQLException {
        String getGearBonusSql = "select * from GearBonus where ItemID = ? and StatsName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getGearBonusSql)) {
            ps.setInt(1, item.getItemID());
            ps.setString(2, statsName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Statistics statistics = Statistics.valueOf(statsName);
                    return new GearBonus(
                            item,
                            statistics,
                            rs.getInt("Value")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static List<GearBonus> getGearBonusById(
            Connection cxn,
            Item item
    ) throws SQLException {
        String getGearBonusSql = "select * from GearBonus where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getGearBonusSql)) {
            ps.setInt(1, item.getItemID());
            try (ResultSet rs = ps.executeQuery()) {

                List<GearBonus> gearBonusList = new ArrayList<>();
                while (rs.next()) {
                    Statistics statistics = Statistics.valueOf(rs.getString("StatsName").toUpperCase());
                    gearBonusList.add(new GearBonus(item, statistics, rs.getInt("Value")));

                }
                return gearBonusList;
            }
        }
    }
}
