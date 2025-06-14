package dal;

import model.Clan;
import model.Race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClanDAO {

    public static Clan create(
            Connection cxn,
            Race race,
            String clanName
    ) throws SQLException {
        String createClanSql = "insert into clan (RaceName, ClanName) values (?, ?)";
        try (PreparedStatement ps = cxn.prepareStatement(createClanSql)) {
            ps.setString(1, race.getName());
            ps.setString(2, clanName);
            ps.executeUpdate();
            return new Clan(
                    race,
                    clanName
            );

        }
    }

    public static Clan getClanByName(
            Connection cxn,
            String clanName,
            Race race
    ) throws SQLException {
        String selectClanSql = "select * from clan where ClanName = ? and RaceName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectClanSql)) {
            ps.setString(1, clanName);
            ps.setString(2, race.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Clan(
                            race,
                            rs.getString("ClanName")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static List<Clan> getAllClansByRaceName(
            Connection cxn,
            Race race
    ) throws SQLException {
        String selectClanSql = "select * from clan where RaceName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectClanSql)) {
            ps.setString(1, race.getName());
            try (ResultSet rs = ps.executeQuery()) {
                List<Clan> clanList = new ArrayList<>();
                while (rs.next()) {
                    clanList.add(new Clan(
                            race,
                            rs.getString("ClanName")
                    ));
                }
                return clanList;
            }
        }
    }

}
