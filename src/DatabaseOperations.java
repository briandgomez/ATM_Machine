import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseOperations {
    private Connection connection;

    public DatabaseOperations(Connection connection) {
        this.connection = connection;
    }

    public void performDatabaseOperations() {
        try {
            String sql = "INSERT INTO atm_machine_sample_table (primary_account_number, pin, balance, date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, 1234567891L);
            preparedStatement.setLong(2, 4559);
            preparedStatement.setString(3, "9500");
            preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) changed");

            String sql2 = "INSERT INTO atm_machine_sample_table_2 (primary_account_number, pin, balance, date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setLong(1, 379837492L);
            preparedStatement2.setLong(2, 1234);
            preparedStatement2.setString(3, "10000000");
            preparedStatement2.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));

            int rowsAffected2 = preparedStatement2.executeUpdate();
            System.out.println(rowsAffected2 + " row(s) changed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getCurrentBalance(long cardNum) {
        long balance = 0;
        try {
            String sql = "SELECT balance FROM atm_machine_sample_table WHERE primary_account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cardNum);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getLong("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public long Withdraw(long cardNum) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to withdraw?");
        long withdrawAmount = scanner.nextInt();

        long currentBalance = getCurrentBalance(cardNum);
        long newBalance = currentBalance - withdrawAmount;
        try {
            String sql = "UPDATE atm_machine_sample_table SET balance = ? WHERE primary_account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, newBalance);
            preparedStatement.setLong(2, cardNum);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("\nBalance updated successfully.");
            } else {
                System.out.println("\nFailed to update balance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBalance;
    }

    public long Deposit(long cardNum) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to deposit?");
        long depositAmount = scanner.nextInt();

        long currentBalance = getCurrentBalance(cardNum);
        long newBalance = currentBalance + depositAmount;
        try {
            String sql = "UPDATE atm_machine_sample_table SET balance = ? WHERE primary_account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, newBalance);
            preparedStatement.setLong(2, cardNum);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("\nBalance updated successfully.");
            } else {
                System.out.println("\nFailed to update balance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBalance;
    }
}
