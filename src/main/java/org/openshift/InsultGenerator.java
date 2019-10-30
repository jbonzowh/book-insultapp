package org.openshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsultGenerator {
    private final String databaseURL = "jdbc:postgresql://" + System.getenv("POSTGRESQL_SERVICE_HOST") + "/"
            + System.getenv("POSTGRESQL_DATABASE");
    private final String username = System.getenv("POSTGRESQL_USER");
    private final String password = System.getenv("PGPASSWORD");
    private final String vowels = "AEIOU";

    public String generateInsult() {
        try (Connection connection = DriverManager.getConnection(databaseURL, username, password)) {
            if (connection != null) {
                String SQL =
                        "select a.string AS first, b.string AS second, c.string AS noun    from short_adjective a , long_adjective b, noun c ORDER BY random() limit 1";
                try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
                    while (rs.next()) {
                        String article = "an";
                        if (vowels.indexOf(rs.getString("first").charAt(0)) == -1) {
                            article = "a";
                        }
                        return String.format("Thou art %s %s %s %s!", article, rs.getString("first"),
                                rs.getString("second"), rs.getString("noun"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Database connection problem!";
        }
        return "";
    }
}
