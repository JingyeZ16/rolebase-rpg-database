package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Utils {

    public static Timestamp dateToTimestamp(Date d) {
        return new Timestamp(Objects.requireNonNull(d).getTime());
    }

    public static Date timestampToDate(Timestamp t) {
        return new Date(Objects.requireNonNull(t).getTime());
    }

    public static int getAutoIncrementKey(final PreparedStatement insertStmt) throws SQLException {
        try (ResultSet rs = insertStmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key");
            }
        }

    }
}
