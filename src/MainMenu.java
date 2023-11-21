import java.util.Scanner;
import java.sql.Connection;

public class MainMenu {

    public long Login(Connection connection) {
        Scanner primAccntNum = new Scanner(System.in);
        Scanner pin = new Scanner(System.in);
        DatabaseHelper util = new DatabaseHelper();

        System.out.println("\nPlease provide your primary account number and PIN:");
        System.out.println("Primary account number: ");
        long cardNum = primAccntNum.nextInt();
        System.out.println("PIN: ");
        int pinNum = pin.nextInt();
        util.authenticateUser(cardNum, pinNum, connection);
        return cardNum;
    }

    public void Menu(Connection connection, long cardNum) {
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
                long defaultAmount = 0;
                boolean deafaultTestVal = false;
                long balance = databaseOperations.Withdraw(cardNum, defaultAmount, deafaultTestVal);
                System.out.println("Current balance: $" + balance);
                break;
            } else if (response.equals("deposit")) {
                // newTransaction.Deposit();
                long balance = databaseOperations.Deposit(cardNum);
                System.out.println("Current balance: $" + balance);
                break;
            } else if (response.equals("balance")) {
                long balance = databaseOperations.getCurrentBalance(cardNum);
                System.out.println("Current balance: $" + balance);
                break;
            } else {
                System.out.println("Please select a valid choice");
            }
        }
        performNewTransaction(connection, cardNum);
    }

    public void performNewTransaction(Connection connection, long cardNum) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nWould you like to perform another transaction?");
        System.out.println("Type Yes or No below: ");
        String response = input.next().toLowerCase();
        if (response.equals("yes")) {
            Menu(connection, cardNum);
        } else {
            input.close();
        }
    }
}
