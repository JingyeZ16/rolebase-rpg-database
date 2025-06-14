package dal;

import model.CharacterInventory;
import model.Characters;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterInventoryDAO {

    public static CharacterInventory create(
            Connection cxn,
            int slotNumber,
            Characters characters,
            Item item,
            int quantity
    ) throws SQLException {
        String insertCharacterInventorySql = "insert into CharacterInventory(SlotNumber, CharacterID, ItemID, Quantity) values(?,?,?,?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertCharacterInventorySql)) {
            ps.setInt(1, slotNumber);
            ps.setInt(2, characters.getCharacterID());
            ps.setInt(3, item.getItemID());
            ps.setInt(4, quantity);
            ps.executeUpdate();
            return new CharacterInventory(slotNumber, characters, item, quantity);
        }
    }

    public static CharacterInventory getInventoryByIdAndSlotNumber(
            Connection cxn,
            Characters character,
            int slotNumber
    ) throws SQLException {
        String selectCharacterInventorySQL = "select * from CharacterInventory where CharacterID = ? and SlotNumber = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCharacterInventorySQL)) {
            ps.setInt(1, character.getCharacterID());
            ps.setInt(2, slotNumber);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Item item = ItemDAO.getItemById(cxn, rs.getInt("ItemID"));
                    return new CharacterInventory(
                            slotNumber,
                            character,
                            item,
                            rs.getInt("Quantity"));
                } else {
                    return null;
                }
            }
        }
    }

    public static List<CharacterInventory> getAllInventoryById(
            Connection cxn,
            Characters character
    ) throws SQLException {
        String selectCharacterInventorySQL = "select * from CharacterInventory where CharacterID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCharacterInventorySQL)) {
            ps.setInt(1, character.getCharacterID());
            try (ResultSet rs = ps.executeQuery()) {
                List<CharacterInventory> inventoryList = new ArrayList<>();
                while (rs.next()) {
                    inventoryList.add(
                            new CharacterInventory(
                                    rs.getInt("SlotNumber"),
                                    character,
                                    ItemDAO.getItemById(cxn, rs.getInt("ItemID")),
                                    rs.getInt("Quantity")
                            )
                    );
                }
                return inventoryList;
            }
        }
    }
}
