import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Start db server
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/atm_machine_sample_db";
    private static final String username = "root";
    private static final String password = "tannebaum55$";
    private static Connection connection;

    public static void createTables() {
        try {
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = conn.createStatement();

            List<String> tableNames = new ArrayList<String>();
            tableNames.add("atm_machine_sample_table");
            tableNames.add("atm_machine_sample_table_2");

            for (String tableName : tableNames) {
                statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            }

            String sql = null;
            for (String currentTableName : tableNames) {
                sql = "CREATE TABLE " + currentTableName + " " +
                        "(id INTEGER NOT NULL AUTO_INCREMENT, "
                        + "Account VARCHAR(255), "
                        + "Balance INTEGER, "
                        + "Date DATE, "
                        + "PRIMARY KEY (id))";
                statement.executeUpdate(sql);
                System.out.println(currentTableName + " created");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connectoDatabase() {
        try {
            Connection conn = getDatabaseConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS atm_machine_sample_table");

            System.out.println("Database successfully created!");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getDatabaseConnection() {
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

    public static void closeDatabaseConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
