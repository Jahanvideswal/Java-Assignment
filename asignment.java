import java.util.Scanner;

class Account {
    int accountNumber;
    String accountHolderName;
    double balance;
    String email;
    String phoneNumber;

    Account(int accountNumber, String accountHolderName, double balance, String email, String phoneNumber) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    int getAccountNumber() {
        return accountNumber;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + " | New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | Remaining Balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal! Check amount and balance.");
        }
    }

    void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
    }

    void updateContactDetails(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        System.out.println("Contact details updated successfully!");
    }
}

class UserInterface {
    Account[] accounts;
    int accountCount;
    Scanner sc;

    UserInterface() {
        accounts = new Account[500];
        accountCount = 0;
        sc = new Scanner(System.in);
    }

    void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine();

        System.out.print("Enter deposit amount: ");
        double balance = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        int accNum = 1000 + accountCount + 1;
        accounts[accountCount] = new Account(accNum, name, balance, email, phone);
        accountCount++;

        System.out.println("Account created successfully! Account Number: " + accNum);
    }

    void performDeposit() {
        System.out.print("Enter account number: ");
        int accNum = sc.nextInt();
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();

        Account acc = findAccount(accNum);
        if (acc != null) {
            acc.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    void performWithdrawal() {
        System.out.print("Enter account number: ");
        int accNum = sc.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();

        Account acc = findAccount(accNum);
        if (acc != null) {
            acc.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    void showAccountDetails() {
        System.out.print("Enter account number: ");
        int accNum = sc.nextInt();

        Account acc = findAccount(accNum);
        if (acc != null) {
            acc.displayAccountDetails();
        } else {
            System.out.println("Account not found!");
        }
    }

    void updateContact() {
        System.out.print("Enter account number: ");
        int accNum = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        System.out.print("Enter new phone number: ");
        String phone = sc.nextLine();

        Account acc = findAccount(accNum);
        if (acc != null) {
            acc.updateContactDetails(email, phone);
        } else {
            System.out.println("Account not found!");
        }
    }

    Account findAccount(int accNum) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] != null && accounts[i].getAccountNumber() == accNum) {
                return accounts[i];
            }
        }
        return null;
    }

    void mainMenu() {
        int choice;
        do {
            System.out.println("\n--- Banking Application ---");
            System.out.println("1. Create a new account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. View account details");
            System.out.println("5. Update contact details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: createAccount(); break;
                case 2: performDeposit(); break;
                case 3: performWithdrawal(); break;
                case 4: showAccountDetails(); break;
                case 5: updateContact(); break;
                case 6: System.out.println("Thank you for using the banking app!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }
}

class Main {
    public static void main(String[] args) {
        UserInterface obj = new UserInterface();
        obj.mainMenu();
    }
}
