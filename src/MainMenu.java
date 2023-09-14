import java.util.Scanner;

public class MainMenu {

    public void Menu() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("What would you like to do?\n");
            System.out.println(
                    "Please type 1 of the following: \n" +
                            "Deposit\n" +
                            "Withdraw\n");
            String response = input.next().toLowerCase();
            Transactions newTransaction = new Transactions();

            if (response.equals("withdraw")) {
                newTransaction.Withdrawal();
                break;
            } else if (response.equals("deposit")) {
                newTransaction.Deposit();
                break;
            } else if (response.equals("balance")) {
                newTransaction.Balance();
                break;
            } else {
                System.out.println("Please select a valid choice");
            }
        }
        performNewTransaction();
    }

    public void performNewTransaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nWould you like to perform another transaction?");
        System.out.println("Type Yes or No bewlow: ");
        String response = input.next().toLowerCase();
        if (response.equals("yes")) {
            Menu();
        } else {
            input.close();
        }
    }
}
