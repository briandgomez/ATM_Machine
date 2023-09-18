import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperations {
    private Connection connection;

    public DatabaseOperations(Connection connection) {
        this.connection = connection;
    }

    public void performDatabaseOperations() {
        try {
            String sql = "INSERT INTO atm_machine_sample_table (Account, Balance, Date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Checking");
            preparedStatement.setString(2, "9500");
            preparedStatement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) changed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
