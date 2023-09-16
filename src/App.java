import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        try {
             // Connection to db
            Connection connection = DatabaseConnector.getConnection();
            System.out.println("Successfully connected to MySQL server!");

            // Create database
            DatabaseConnector.createDatabase();

            // Create table
            DatabaseConnector.createTable();
            
            // Insert values into exsiting table
            DatabaseOperations databaseOperations = new DatabaseOperations(connection);
            databaseOperations.performDatabaseOperations();

            // Perform transactions
            MainMenu options = new MainMenu();
            options.Menu(connection);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnector.closeConnection();
        }
    }
}
