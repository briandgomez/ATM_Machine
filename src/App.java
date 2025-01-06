import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            // Connection to db
            Connection connection = DatabaseConnector.getDatabaseConnection();
            System.out.println("Successfully connected to MySQL server!");

            // Create database
            DatabaseConnector.connectoDatabase();

            // Create table
            DatabaseConnector.createTables();

            // Insert values into exsiting table
            DatabaseOperations databaseOperations = new DatabaseOperations(connection);
            databaseOperations.initializeSampleData();

            // Perform transactions
            MainMenu options = new MainMenu();
            long cardNum = options.Login(connection);
            options.Menu(connection, cardNum);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnector.closeDatabaseConnection();
        }
    }
}
