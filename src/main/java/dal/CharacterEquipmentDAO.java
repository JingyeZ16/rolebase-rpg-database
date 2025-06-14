package dal;

import model.CharacterEquipment;
import model.Characters;
import model.Item;
import model.Slot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterEquipmentDAO {

    public static CharacterEquipment create(
            Connection cxn,
            Characters characters,
            Slot slot,
            Item item
    ) throws SQLException {
        String insertCharacterEquipmentSql = "INSERT INTO CharacterEquipment (CharacterID, SlotName, ItemID) VALUES (?, ?, ?) ";
        try (PreparedStatement ps = cxn.prepareStatement(insertCharacterEquipmentSql)) {
            ps.setInt(1, characters.getCharacterID());
            ps.setString(2, slot.getName());
            ps.setInt(3, item.getItemID());
            ps.executeUpdate();
            return new CharacterEquipment(characters, slot, item);
        }
    }

    public static CharacterEquipment getEquipmentByIdAndSlot(
            Connection cxn,
            Characters character,
            Slot slot
    ) throws SQLException {
        String getEquipmentByIDAndSlotSql = "SELECT * FROM CharacterEquipment WHERE CharacterID = ? AND SlotName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getEquipmentByIDAndSlotSql)) {

            ps.setInt(1, character.getCharacterID());
            ps.setString(2, slot.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CharacterEquipment(
                        character,
                        slot,
                        ItemDAO.getItemById(cxn, rs.getInt("ItemID"))
                );
            } else {
                return null;
            }

        }
    }

    public static List<CharacterEquipment> getAllEquipmentById(
            Connection cxn,
            Characters character
    ) throws SQLException {
        String getAllEquipmentByIdSql = "SELECT * FROM CharacterEquipment WHERE CharacterID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(getAllEquipmentByIdSql)) {
            ps.setInt(1, character.getCharacterID());
            ResultSet rs = ps.executeQuery();
            List<CharacterEquipment> equipmentList = new ArrayList<>();
            while (rs.next()) {
                equipmentList.add(new CharacterEquipment(
                        character,
                        Slot.valueOf(rs.getString("SlotName").toUpperCase()),
                        ItemDAO.getItemById(cxn, rs.getInt("ItemID"))
                ));
            }
            return equipmentList;
        }
    }
}
