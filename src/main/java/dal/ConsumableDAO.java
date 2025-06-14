package dal;

import model.Consumable;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumableDAO {

    public static Consumable create(
            Connection cxn,
            Item item,
            String description
    ) throws SQLException {
        String insertConsumableSql = "insert into Consumable (ItemID, Description) values (?, ?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertConsumableSql)) {
            ps.setInt(1, item.getItemID());
            ps.setString(2, description);
            ps.executeUpdate();
            return new Consumable(
                    item.getItemID(),
                    item.getItemName(),
                    item.getLevel(),
                    item.getMaxStackSize(),
                    item.getPrice(),
                    item.getRequiredLevel(),
                    description
            );
        }
    }

    public static Consumable getConsumableById(
            Connection cxn,
            Item item
    ) throws SQLException {
        String selectConsumableSql = "select * from Consumable where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectConsumableSql)) {
            ps.setInt(1, item.getItemID());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Consumable(
                            item.getItemID(),
                            item.getItemName(),
                            item.getLevel(),
                            item.getMaxStackSize(),
                            item.getPrice(),
                            item.getRequiredLevel(),
                            rs.getString("Description")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static Consumable updateDescription(
            Connection cxn,
            Consumable consumable,
            String newDescription
    ) throws SQLException {
        String updateConsumableSql = "update Consumable set Description = ? where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(updateConsumableSql)) {
            ps.setString(1, newDescription);
            ps.setInt(2, consumable.getItemID());
            ps.executeUpdate();
            Item item = ItemDAO.getItemById(cxn, consumable.getItemID());
            return new Consumable(
                    consumable.getItemID(),
                    item.getItemName(),
                    item.getLevel(),
                    item.getMaxStackSize(),
                    item.getPrice(),
                    item.getRequiredLevel(),
                    newDescription
            );
        }
    }
}
