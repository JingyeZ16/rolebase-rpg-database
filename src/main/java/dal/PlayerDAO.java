package dal;

import model.Player;

import java.sql.*;

public class PlayerDAO {

    public static Player create(
            Connection cxn,
            String fullName,
            String email
    ) throws SQLException {
        String createPlayerSql = "INSERT INTO player (Fullname, Email) VALUES(?,?)";
        try (PreparedStatement stmt = cxn.prepareStatement(createPlayerSql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.executeUpdate();
            return new Player(
                    Utils.getAutoIncrementKey(stmt),
                    fullName,
                    email
            );
        }
    }

    public static Player getPlayerByID(
            Connection cxn,
            int playerId
    ) throws SQLException {
        String getPlayerSql = "SELECT * FROM player WHERE PlayerId = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(getPlayerSql)) {
            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Player(
                            playerId,
                            rs.getString("FullName"),
                            rs.getString("Email")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static Player getPlayerByEmail(
            Connection cxn,
            String email
    ) throws SQLException {
        String getPlayerSql = "SELECT * FROM player WHERE Email = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(getPlayerSql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Player(
                            rs.getInt("PlayerID"),
                            rs.getString("FullName"),
                            email
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static void deletePlayerByID(
            Connection cxn,
            int playerId
    ) throws SQLException {
        String deletePlayerSql = "DELETE FROM player WHERE id = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(deletePlayerSql)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }

}
