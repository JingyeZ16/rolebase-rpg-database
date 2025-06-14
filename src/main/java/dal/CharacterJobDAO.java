package dal;

import model.CharacterJob;
import model.Characters;
import model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterJobDAO {

    public static CharacterJob createCharacterJob(
            Connection cxn,
            Job job,
            Characters character,
            int level,
            int xp,
            boolean lock
    ) throws SQLException {
        String insertCharacterJobSql = "INSERT INTO CharacterJob (JobName, CharacterID, Level, XP, `Lock`) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = cxn.prepareStatement(insertCharacterJobSql)) {
            ps.setString(1, job.getName());
            ps.setInt(2, character.getCharacterID());
            ps.setInt(3, level);
            ps.setInt(4, xp);
            ps.setBoolean(5, lock);
            ps.executeUpdate();
            return new CharacterJob(
                    job,
                    character,
                    level, xp,
                    lock
            );
        }
    }

    public static CharacterJob getCharacterJobByIdAndJobName(
            Connection cxn,
            Characters character,
            Job job
    ) throws SQLException {
        String selectCharacterJobSql = "SELECT * FROM CharacterJob WHERE CharacterID = ? AND JobName = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCharacterJobSql)) {
            ps.setInt(1, character.getCharacterID());
            ps.setString(2, job.getName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    return new CharacterJob(
                            job,
                            character,
                            rs.getInt("Level"),
                            rs.getInt("XP"),
                            rs.getBoolean("Lock")
                    );
                } else {
                    return null;

                }
            }
        }
    }

    public static List<CharacterJob> getAllCharacterJobsById(
            Connection cxn,
            Characters character

    ) throws SQLException {
        String selectCharacterJobSql = "SELECT * FROM CharacterJob WHERE CharacterID = ?";
        try (PreparedStatement ps = cxn.prepareStatement(selectCharacterJobSql)) {
            ps.setInt(1, character.getCharacterID());
            try (ResultSet rs = ps.executeQuery()) {
                List<CharacterJob> jobs = new ArrayList<>();
                while (rs.next()) {
                    jobs.add(
                            new CharacterJob(
                                    Job.valueOf(rs.getString("JobName").toUpperCase()),
                                    character,
                                    rs.getInt("Level"),
                                    rs.getInt("XP"),
                                    rs.getBoolean("Lock")
                            )
                    );
                }
                return jobs;
            }
        }
    }
}
