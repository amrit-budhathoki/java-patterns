// Title: Strategy Pattern - Payment Processing

/**
 * Demonstrates the Strategy pattern where different payment methods can be swapped at runtime.
 * Use this when you have multiple algorithms that can be selected dynamically based on context,
 * allowing clients to choose the implementation without coupling to concrete classes.
 */

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    
    CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount + " using card " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    
    PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount + " for account " + email);
    }
}

class CryptoPayment implements PaymentStrategy {
    private String walletAddress;
    
    CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing crypto payment of $" + amount + " to wallet " + walletAddress);
    }
}

class PaymentProcessor {
    private PaymentStrategy strategy;
    
    PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    void processPayment(double amount) {
        strategy.pay(amount);
    }
}

public class StrategyPatternExample {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor(new CreditCardPayment("1234-5678-9012-3456"));
        processor.processPayment(99.99);
        
        processor.setPaymentStrategy(new PayPalPayment("user@example.com"));
        processor.processPayment(49.99);
        
        processor.setPaymentStrategy(new CryptoPayment("0x123abc..."));
        processor.processPayment(0.5);
    }
}
