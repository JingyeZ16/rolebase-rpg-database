package dal;

import model.Characters;
import model.Clan;
import model.Player;
import model.Race;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {

    public static Characters create(
            Connection cxn,
            Player player,
            String firstName,
            String lastName,
            Race race,
            Clan clan
    ) throws SQLException {
        String createCharacterSql = "insert into Characters (PlayerID, FirstName, LastName, RaceName, ClanName ) values(?,?,?,?,?)";
        try (PreparedStatement insertStmt = cxn.prepareStatement(createCharacterSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setInt(1, player.getPlayerID());
            insertStmt.setString(2, firstName);
            insertStmt.setString(3, lastName);
            insertStmt.setString(4, race.getName());
            insertStmt.setString(5, clan.getClan());
            insertStmt.executeUpdate();
            return new Characters(
                    Utils.getAutoIncrementKey(insertStmt),
                    player,
                    firstName,
                    lastName,
                    race,
                    clan
            );
        }

    }

    public static Characters getCharacterByID(
            Connection cxn,
            int characterID
    ) throws SQLException {
        String getCharacterSql = "select * from Characters where CharacterID = ?";
        try (PreparedStatement selectStmt = cxn.prepareStatement(getCharacterSql)) {
            selectStmt.setInt(1, characterID);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                Player player = PlayerDAO.getPlayerByID(cxn, rs.getInt("PlayerID"));
                Race race = Race.valueOf(rs.getString("RaceName").toUpperCase());
                Clan clan = ClanDAO.getClanByName(cxn, rs.getString("ClanName"), race);
                return new Characters(
                        characterID,
                        player,
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        race,
                        clan
                );
            } else {
                return null;
            }
        }
    }

    public static List<Characters> getCharactersByPlayerId(
            Connection cxn,
            Player player
    ) throws SQLException {
        String getCharacterSql = "select * from Characters where PlayerID = ?";
        try (PreparedStatement selectStmt = cxn.prepareStatement(getCharacterSql)) {
            selectStmt.setInt(1, player.getPlayerID());
            ResultSet rs = selectStmt.executeQuery();
            List<Characters> characters = new ArrayList<>();

            while (rs.next()) {
                Race race = Race.valueOf(rs.getString("RaceName").toUpperCase());
                Clan clan = ClanDAO.getClanByName(cxn, rs.getString("ClanName"), race);
                characters.add(new Characters(
                        rs.getInt("CharacterID"),
                        player,
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        race,
                        clan
                ));

            }
            return characters;
        }
    }

    public static int deleteCharacterByName(
            Connection cxn,
            String firstName,
            String lastName
    ) throws SQLException {
        String deleteCharacterSql = "delete from Characters where FirstName = ? and LastName = ?";
        try (PreparedStatement deleteStmt = cxn.prepareStatement(deleteCharacterSql)) {
            deleteStmt.setString(1, firstName);
            deleteStmt.setString(2, lastName);
            return deleteStmt.executeUpdate();
        }
    }

    public static List<Characters> searchCharactersByPartialName(
            Connection cxn,
            String name) throws SQLException {
        String getCharacterSql = "select * from Player where FullName like ?";

        try (PreparedStatement selectStmt = cxn.prepareStatement(getCharacterSql)) {
            selectStmt.setString(1, name + "%");
            ResultSet rs = selectStmt.executeQuery();
            List<Characters> allCharacters = new ArrayList<>();
            while (rs.next()) {
                Player player = PlayerDAO.getPlayerByID(cxn, rs.getInt("PlayerID"));
                List<Characters> characters = CharacterDAO.getCharactersByPlayerId(cxn, player);
                allCharacters.addAll(characters);
            }
            return allCharacters;
        }
    }

    public static List<Characters> getAllCharacters(Connection cxn) throws SQLException {
        String sql = "SELECT * FROM Characters";
        List<Characters> characters = new ArrayList<>();
        try (PreparedStatement ps = cxn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Characters character = CharacterDAO.getCharacterByID(cxn, rs.getInt("CharacterID"));
                characters.add(character);
            }
        }
        return characters;
    }
}
