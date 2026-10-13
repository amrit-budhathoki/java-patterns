// Title: Proxy Pattern - Access Control

// Demonstrates the Proxy pattern by controlling access to a sensitive resource.
// Use this when you need to add access control, logging, or delay initialization
// of expensive objects. A proxy acts as a placeholder/surrogate for the real object.

public class ProxyPatternExample {
    
    public static void main(String[] args) {
        BankAccount account = new AccountProxy("user123", "password123");
        
        System.out.println("--- Authorized Access ---");
        account.withdraw(100);
        account.deposit(50);
        account.getBalance();
        
        System.out.println("\n--- Unauthorized Access ---");
        BankAccount unauthorizedAccount = new AccountProxy("user123", "wrongpassword");
        unauthorizedAccount.withdraw(100);
    }
}

interface BankAccount {
    void withdraw(double amount);
    void deposit(double amount);
    void getBalance();
}

class RealBankAccount implements BankAccount {
    private double balance = 1000;
    
    @Override
    public void withdraw(double amount) {
        balance -= amount;
        System.out.println("Withdrew: $" + amount + ", New balance: $" + balance);
    }
    
    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount + ", New balance: $" + balance);
    }
    
    @Override
    public void getBalance() {
        System.out.println("Current balance: $" + balance);
    }
}

class AccountProxy implements BankAccount {
    private RealBankAccount realAccount;
    private String userId;
    private String password;
    private boolean authenticated;
    
    public AccountProxy(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.authenticated = authenticate(password);
    }
    
    private boolean authenticate(String providedPassword) {
        return "password123".equals(providedPassword);
    }
    
    @Override
    public void withdraw(double amount) {
        if (!authenticated) {
            System.out.println("Access Denied: Invalid credentials for user " + userId);
            return;
        }
        if (realAccount == null) {
            realAccount = new RealBankAccount();
        }
        realAccount.withdraw(amount);
    }
    
    @Override
    public void deposit(double amount) {
        if (!authenticated) {
            System.out.println("Access Denied: Invalid credentials for user " + userId);
            return;
        }
        if (realAccount == null) {
            realAccount = new RealBankAccount();
        }
        realAccount.deposit(amount);
    }
    
    @Override
    public void getBalance() {
        if (!authenticated) {
            System.out.println("Access Denied: Invalid credentials for user " + userId);
            return;
        }
        if (realAccount == null) {
            realAccount = new RealBankAccount();
        }
        realAccount.getBalance();
    }
}
