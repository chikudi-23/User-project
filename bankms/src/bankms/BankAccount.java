/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankms;



import java.io.Serializable;

public abstract class BankAccount implements BankOperations, Serializable {
    private static final long serialVersionUID = 1L;
    protected String accountNumber;
    protected String name;
    protected double balance;

    // Constructor
    public BankAccount(String accountNumber, String name, double balance) {
        if (isValidAccountNumber(accountNumber)) {
            this.accountNumber = accountNumber;
        } else {
            throw new IllegalArgumentException("Account number must start with 'P' followed by up to 5 digits.");
        }
        this.name = name;
        this.balance = balance;
    }

    // Validating account number (should start with 'P' and be followed by up to 5 digits)
    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber.matches("^P\\d{1,5}$"); // Starts with 'P' followed by 1 to 5 digits
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount <= 50000) {
            balance += amount;
            System.out.println("Deposited ₹" + amount + ". Current balance: ₹" + balance);
        } else {
            System.out.println("❌ Deposit amount exceeds ₹50,000 limit.");
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount <= 50000) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew ₹" + amount + ". Current balance: ₹" + balance);
            } else {
                System.out.println("❌ Insufficient balance.");
            }
        } else {
            System.out.println("❌ Withdraw amount exceeds ₹50,000 limit.");
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Name: " + name + ", Balance: ₹" + balance;
    }
}
