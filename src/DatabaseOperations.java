import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public int getCurrentBalance(String accountName) {
        int balance = 0;
        try {
            String sql = "SELECT Balance FROM atm_machine_sample_table WHERE Account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getInt("Balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }
}
