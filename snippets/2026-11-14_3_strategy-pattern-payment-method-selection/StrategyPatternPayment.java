// Title: Strategy Pattern - Payment Method Selection

// Demonstrates the Strategy pattern where different payment methods (Credit Card, PayPal, Bitcoin)
// are encapsulated as interchangeable strategies. Use this when you have multiple algorithms
// that can be selected at runtime, allowing clients to choose behavior dynamically.

public class StrategyPatternPayment {
    public static void main(String[] args) {
        Order order = new Order(150.00);
        
        System.out.println("Order total: $" + order.getAmount());
        System.out.println();
        
        // Strategy 1: Credit Card
        order.setPaymentStrategy(new CreditCardPayment("4532-1234-5678-9010"));
        order.checkout();
        System.out.println();
        
        // Strategy 2: PayPal
        order.setPaymentStrategy(new PayPalPayment("user@example.com"));
        order.checkout();
        System.out.println();
        
        // Strategy 3: Bitcoin
        order.setPaymentStrategy(new BitcoinPayment("1A1z7agoat4WFhN2AWWvCn4G8wP9qilCCn"));
        order.checkout();
    }
}

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
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("Card: " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Payment successful via Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    
    PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("Account: " + email);
        System.out.println("Payment successful via PayPal");
    }
}

class BitcoinPayment implements PaymentStrategy {
    private String walletAddress;
    
    BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing Bitcoin payment of $" + amount);
        System.out.println("Wallet: " + walletAddress.substring(0, 10) + "...");
        System.out.println("Payment successful via Bitcoin");
    }
}

class Order {
    private double amount;
    private PaymentStrategy paymentStrategy;
    
    Order(double amount) {
        this.amount = amount;
    }
    
    void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    void checkout() {
        paymentStrategy.pay(amount);
    }
    
    double getAmount() {
        return amount;
    }
}
