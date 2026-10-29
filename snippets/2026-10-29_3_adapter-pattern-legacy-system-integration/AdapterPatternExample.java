// Title: Adapter Pattern - Legacy System Integration

// The Adapter pattern converts the interface of a class into another interface clients expect.
// Use it when you need to use a class with an incompatible interface, or integrate legacy systems
// with new code without modifying either.

public class AdapterPatternExample {
    
    interface ModernPaymentProcessor {
        void processPayment(double amount);
    }
    
    static class LegacyPaymentGateway {
        void chargeCard(String cardNumber, int amountInCents) {
            System.out.println("Legacy system charging card " + cardNumber + 
                             " with " + amountInCents + " cents");
        }
    }
    
    static class PaymentProcessorAdapter implements ModernPaymentProcessor {
        private final LegacyPaymentGateway legacyGateway;
        private final String cardNumber;
        
        PaymentProcessorAdapter(LegacyPaymentGateway legacyGateway, String cardNumber) {
            this.legacyGateway = legacyGateway;
            this.cardNumber = cardNumber;
        }
        
        @Override
        public void processPayment(double amount) {
            int amountInCents = (int) (amount * 100);
            legacyGateway.chargeCard(cardNumber, amountInCents);
        }
    }
    
    static class CheckoutService {
        void checkout(ModernPaymentProcessor processor, double price) {
            System.out.println("Checkout: Processing payment of $" + price);
            processor.processPayment(price);
        }
    }
    
    public static void main(String[] args) {
        LegacyPaymentGateway legacyGateway = new LegacyPaymentGateway();
        String cardNumber = "4532-1111-2222-3333";
        
        ModernPaymentProcessor adapter = 
            new PaymentProcessorAdapter(legacyGateway, cardNumber);
        
        CheckoutService checkout = new CheckoutService();
        checkout.checkout(adapter, 99.99);
        checkout.checkout(adapter, 45.50);
    }
}
