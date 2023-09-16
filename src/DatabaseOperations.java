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
            String sql = "INSERT INTO atm_machine_sample_table (first, last, age) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Judy");
            preparedStatement.setString(2, "McLean");
            preparedStatement.setString(3, "35");

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) changed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
