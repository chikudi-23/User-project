package bankms;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.text.NumberFormat;
import java.util.Locale;

public class BankManager {

    private static final double MAX_INITIAL_BALANCE = 5000;
    private static final double MAX_DEPOSIT_WITHDRAW = 50000;
    private static final String DATA_FILE = "bank_data.ser";
    private static Map<String, BankAccount> accountMap = new HashMap<>();

    public static void main(String[] args) {
        loadUserData();

        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Welcome to Jojo Bank =====");

        String name = null;
        while (true) {
            try {
                System.out.print("Enter your name: ");
                name = scanner.nextLine().trim();
                if (!name.matches("[a-zA-Z ]+")) {
                    throw new InvalidInputException("❌ Name must contain only alphabetic characters and spaces.");
                }
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.toString());
            }
        }

        String accountNumber = null;
        while (true) {
            try {
                System.out.print("Enter your account number (must start with 'P' followed by up to 5 digits): ");
                accountNumber = scanner.nextLine().trim();

                if (accountNumber.matches(".*[^a-zA-Z0-9].*")) {
                    throw new InvalidInputException("❌ Account number cannot contain special symbols.");
                }
                if (!accountNumber.matches("^P\\d{1,5}$")) {
                    throw new InvalidInputException("❌ Invalid account number format! Account number must start with 'P' followed by up to 5 digits.");
                }
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.toString());
            }
        }

        String key = (name + accountNumber).toLowerCase();
        BankAccount account;

        if (accountMap.containsKey(key)) {
            System.out.println("\n✅ Welcome back, " + name + "! Loading your existing account...\n");
            account = accountMap.get(key);
        } else {
            double balance;
            while (true) {
                try {
                    System.out.print("Enter initial balance (max ₹5000): ");
                    balance = Double.parseDouble(scanner.nextLine().trim());
                    if (balance > MAX_INITIAL_BALANCE) {
                        throw new InvalidAmountException("❌ Initial balance exceeds the ₹5000 limit.");
                    }
                    if (balance < 0) {
                        throw new InvalidAmountException("❌ Initial balance cannot be negative.");
                    }
                    if (balance == 0) {
                        throw new InvalidAmountException("❌ Initial balance cannot be zero.");
                    }
                    account = new SavingsAccount(accountNumber, name, balance);
                    accountMap.put(key, account);
                    System.out.println("\n🆕 New account created for " + name + " with ₹" + balance + ".\n");
                    break;

                } catch (InvalidAmountException e) {
                    System.out.println(e.toString());
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input for balance. Please enter a number.");
                }
            }
        }

        while (true) {
            System.out.println("===== Menu =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Contact Us");
            System.out.println("5. Account Details"); // New Option
            System.out.println("6. Exit");          // Shifted Exit option
            System.out.print("Choose an option (1–6): "); // Updated prompt

            String input = scanner.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number (1–6).\n");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter amount to deposit (max ₹50,000): ");
                        double depositAmount = Double.parseDouble(scanner.nextLine().trim());
                        if (depositAmount <= 0 || depositAmount > MAX_DEPOSIT_WITHDRAW) {
                            throw new InvalidAmountException("❌ Invalid deposit amount.");
                        }
                        account.deposit(depositAmount);
                    } catch (InvalidAmountException e) {
                        System.out.println(e.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input for deposit amount.");
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter amount to withdraw (max ₹50,000): ");
                        double withdrawAmount = Double.parseDouble(scanner.nextLine().trim());
                        if (withdrawAmount <= 0 || withdrawAmount > MAX_DEPOSIT_WITHDRAW) {
                            throw new InvalidAmountException("❌ Invalid withdrawal amount.");
                        }
                        account.withdraw(withdrawAmount);
                    } catch (InvalidAmountException e) {
                        System.out.println(e.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input for withdrawal amount.");
                    }
                    break;
                case 3:
                    System.out.println("Current Balance: ₹" + account.getBalance());
                    break;
                case 4:
                    ContactUs.showContactInfo();
                    break;
                case 5: // Account Details Case
                    System.out.println("===== Account Details =====");
                    System.out.println("Account Holder Name: " + account.getName());
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("=========================");
                    break;
                case 6: // Exit Case
                    System.out.println("Thank you for banking with Jojo Bank, " + name + "!");
                    scanner.close();
                    saveUserData();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accountMap = (Map<String, BankAccount>) ois.readObject();
            System.out.println("Existing user data loaded.\n");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found, starting fresh.\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accountMap);
            System.out.println("User data saved.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}