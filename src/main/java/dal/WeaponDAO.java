package dal;

import model.Item;
import model.Job;
import model.Weapon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeaponDAO {

    public static Weapon create(
            Connection cxn,
            String itemName,
            int level,
            int maxStackSize,
            int price,
            int requiredLevel,
            Job job,
            int damage
    ) throws SQLException {
        String insertWeaponSql = "insert into weapon (ItemID, JobName, Damage) values (?, ?, ?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertWeaponSql)) {
            Item item = ItemDAO.create(cxn, itemName, level, maxStackSize, price, requiredLevel);
            ps.setInt(1, item.getItemID());
            ps.setString(2, job.getName());
            ps.setInt(3, damage);
            ps.executeUpdate();
            return new Weapon(
                    item.getItemID(),
                    itemName,
                    level,
                    maxStackSize,
                    price,
                    requiredLevel,
                    job,
                    damage
            );
        }
    }

    public static Weapon getWeaponById(
            Connection cxn,
            int itemId
    ) throws SQLException {
        String getWeaponSql = "select * from Weapon where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getWeaponSql)) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = ItemDAO.getItemById(cxn, itemId);
                    Job job = Job.valueOf(rs.getString("JobName").toUpperCase());
                    return new Weapon(
                            itemId,
                            item.getItemName(),
                            item.getLevel(),
                            item.getMaxStackSize(),
                            item.getPrice(),
                            item.getRequiredLevel(),
                            job,
                            rs.getInt("Damage")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static Weapon updateWeaponDamage(
            Connection cxn,
            Weapon weapon,
            int newDamage
    ) throws SQLException {
        String updateDamageSql = "update Weapon set damage = ? where ItemID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(updateDamageSql)) {
            Item item = ItemDAO.getItemById(cxn, weapon.getItemID());
            ps.setInt(1, newDamage);
            ps.setInt(2, weapon.getItemID());
            ps.executeUpdate();
            return new Weapon(
                    weapon.getItemID(),
                    item.getItemName(),
                    item.getLevel(),
                    item.getMaxStackSize(),
                    item.getPrice(),
                    item.getRequiredLevel(),
                    weapon.getJob(),
                    newDamage
            );
        }
    }
}
