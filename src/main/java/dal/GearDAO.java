package dal;

import model.Gear;
import model.Item;
import model.Slot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GearDAO {

    public static Gear create(
            Connection cxn,
            String itemName,
            int level,
            int maxStackSize,
            int price,
            int requiredLevel,
            Slot slot
    ) throws SQLException {
        String insertGearSQL = "insert into gear (ItemID, SlotName) values(?,?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertGearSQL)) {
            Item item = ItemDAO.create(cxn, itemName, level, maxStackSize, price, requiredLevel);
            ps.setInt(1, item.getItemID());
            ps.setString(2, slot.getName());
            ps.executeUpdate();
            return new Gear(
                    item.getItemID(),
                    itemName,
                    level,
                    maxStackSize,
                    price,
                    requiredLevel,
                    slot
            );
        }
    }

    public static Gear getGearByid(
            Connection cxn,
            Item item
    ) throws SQLException {
        String getGearSql = "select * from gear where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getGearSql)) {
            ps.setInt(1, item.getItemID());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Slot slot = Slot.valueOf(rs.getString("SlotName"));
                    return new Gear(
                            item.getItemID(),
                            item.getItemName(),
                            item.getLevel(),
                            item.getMaxStackSize(),
                            item.getPrice(),
                            item.getRequiredLevel(),
                            slot
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static void deleteGear(
            Connection cxn,
            Item item
    ) throws SQLException {
        String deleteGearSQL = "delete from gear where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(deleteGearSQL)) {
            ItemDAO.deleteItemById(cxn, item.getItemID());
            ps.setInt(1, item.getItemID());
            ps.executeUpdate();

        }
    }

}
