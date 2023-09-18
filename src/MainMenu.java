import java.util.Scanner;
import java.sql.Connection;

public class MainMenu {

    public void Menu(Connection connection) {
        Scanner input = new Scanner(System.in);
        DatabaseOperations databaseOperations = new DatabaseOperations(connection);

        while (true) {
            System.out.println("What would you like to do?\n");
            System.out.println(
                    "Please type 1 of the following: \n" +
                            "Deposit\n" +
                            "Withdraw\n" +
                            "Balance\n");
            String response = input.next().toLowerCase();
            Transactions newTransaction = new Transactions();

            if (response.equals("withdraw")) {
                // newTransaction.Withdrawal();
                int balance = databaseOperations.Withdraw("Checking");
                System.out.println("Current balance: $" + balance);
                break;
            } else if (response.equals("deposit")) {
                newTransaction.Deposit();
                break;
            } else if (response.equals("balance")) {
                int balance = databaseOperations.getCurrentBalance("Checking");
                System.out.println("Current balance: $" + balance);
                break;
            } else {
                System.out.println("Please select a valid choice");
            }
        }
        performNewTransaction(connection);
    }

    public void performNewTransaction(Connection connection) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nWould you like to perform another transaction?");
        System.out.println("Type Yes or No below: ");
        String response = input.next().toLowerCase();
        if (response.equals("yes")) {
            Menu(connection);
        } else {
            input.close();
        }
    }
}
