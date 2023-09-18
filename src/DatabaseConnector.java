import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Start db server
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/atm_machine_sample_db";
    private static final String username = "root";
    private static final String password = "tannebaum55$";
    private static final String databasename = "atm_machine_sample_db";

    private static Connection connection;

    public static void createTable() {
        try {
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS " + databasename);

            String sql = "CREATE TABLE atm_machine_sample_table " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, "
                    + "Account VARCHAR(255), "
                    + "Balance INTEGER, "
                    + "Date DATE, "
                    + "PRIMARY KEY (id))";

            statement.executeUpdate(sql);
            System.out.println("Table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDatabase() {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS atm_machine_sample_table");

            System.out.println("Database successfully created!");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Connect to db using credentials
                connection = DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
