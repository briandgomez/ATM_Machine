import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

public class DatabaseHelper {
    public void authenticateUser(long cardNum, int pinNum, Connection connection) {
        int count = 0;
        ArrayList<String> tableNamesAsStr = getTableNames(connection);
        try {
            for (String tableName : tableNamesAsStr) {
                String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE primary_account_number = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, cardNum);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                    if (count == 1) {
                        System.out.println("Val exists in table: " + tableName);
                    }
                }
            }

            if (count == 0) {
                System.out.println("\nPermanent number does not exist. Please try again");
                MainMenu newMenu = new MainMenu();
                newMenu.Login(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTableNames(Connection connection) {
        ArrayList<String> tableNamesAsStr = new ArrayList<String>();
        try {
            String sql = "SHOW TABLES";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tableNamesAsStr.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNamesAsStr;
    }
}
