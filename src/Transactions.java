import java.util.Scanner;

public class Transactions {
    private int currentBalance = 100;

    public void Withdrawal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to wihtdraw?");

        int withdrawAmount = scanner.nextInt();

        if (withdrawAmount > 0) {
            if (currentBalance > 0) {
                currentBalance -= withdrawAmount;
                System.out.println("Amount withdrawn: $" + withdrawAmount);
                System.out.println("Current balance: $" + Balance());
                if (currentBalance < 0) {
                    System.out.println(
                            "You have withdrawn more than you currently have in your balance.\nYou will be charged a overdraft fee");
                }
            } else {
                System.out.println(
                        "You have withdrawn more than you currently have in your balance.\nYou will be charged a overdraft fee");
            }
        } else {
            System.out.println("Can't remove 0 from account");
        }
    }

    public void Deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to deposit?");

        int depositAmount = scanner.nextInt();

        if(depositAmount > 0) {
            currentBalance += depositAmount;
            Balance();
        }
        System.out.println("Amount deposited: $" + depositAmount);
        System.out.println("Current balance: $" + Balance());
    }

    public int Balance() {
        return currentBalance;
    }
}
