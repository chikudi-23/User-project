package bankms; 

public class SavingsAccount extends BankAccount {
    
     private static final long serialVersionUID = 1L;
     
    private static final double MAX_LIMIT = 50000;

    public SavingsAccount(String accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > MAX_LIMIT) {
            System.out.println("⚠️ Warning: Deposit amount exceeds the ₹50,000 limit. Transaction cancelled.");
        } else {
            balance += amount;
            System.out.println("✅ Deposited: ₹" + amount);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > MAX_LIMIT) {
            System.out.println("⚠️ Warning: Withdraw amount exceeds the ₹50,000 limit. Transaction cancelled.");
        } else if (balance >= amount) {
            balance -= amount;
            System.out.println("✅ Withdrew: ₹" + amount);
        } else {
            System.out.println("❌ Insufficient balance. Transaction cancelled.");
        }
    }
}
