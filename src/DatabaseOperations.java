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

    public int Withdraw(String accountName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to withdraw?");
        int withdrawAmount = scanner.nextInt();

        int currentBalance = getCurrentBalance(accountName);
        int newBalance = currentBalance - withdrawAmount;
        try {
            String sql = "UPDATE atm_machine_sample_table SET Balance = ? WHERE Account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newBalance);
            preparedStatement.setString(2, accountName);
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

    public int Deposit(String accountName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to deposit?");
        int depositAmount = scanner.nextInt();

        int currentBalance = getCurrentBalance(accountName);
        int newBalance = currentBalance + depositAmount;
        try {
            String sql = "UPDATE atm_machine_sample_table SET Balance = ? WHERE Account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newBalance);
            preparedStatement.setString(2, accountName);
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
