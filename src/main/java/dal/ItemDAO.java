package dal;

import model.Item;

import java.sql.*;

public class ItemDAO {

    public static Item create(
            Connection cxn,
            String itemName,
            int level,
            int maxStackSize,
            int price,
            int requiredLevel
    ) throws SQLException {
        String insertItemSQL = "insert into Item (ItemName, Level, MaxStackSize, Price, RequiredLevel) values (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertItemSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemName);
            ps.setInt(2, level);
            ps.setInt(3, maxStackSize);
            ps.setInt(4, price);
            ps.setInt(5, requiredLevel);
            ps.executeUpdate();
            return new Item(
                    Utils.getAutoIncrementKey(ps),
                    itemName,
                    level,
                    maxStackSize,
                    price,
                    requiredLevel
            );
        }
    }

    public static Item getItemById(
            Connection cxn,
            int itemId
    ) throws SQLException {
        String geyItemByIdSql = "select * from Item where ItemId = ?";
        try (PreparedStatement ps = cxn.prepareStatement(geyItemByIdSql)) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Item(
                            itemId,
                            rs.getString("ItemName"),
                            rs.getInt("Level"),
                            rs.getInt("MaxStackSize"),
                            rs.getInt("Price"),
                            rs.getInt("RequiredLevel")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static void deleteItemById(
            Connection cxn,
            int itemId
    ) throws SQLException {
        String deleteItemByIdSql = "delete from Item where ItemId = ?";
        try (PreparedStatement ps = cxn.prepareStatement(deleteItemByIdSql)) {
            ps.setInt(1, itemId);
            ps.executeUpdate();

        }
    }

}
