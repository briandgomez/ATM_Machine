import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("What would you like to do?");
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
        input.close();
    }
}
