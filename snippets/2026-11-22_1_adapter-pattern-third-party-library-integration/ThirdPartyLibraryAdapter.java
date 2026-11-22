// Title: Adapter Pattern - Third-party Library Integration

// The Adapter pattern allows incompatible interfaces to work together by wrapping
// one interface in another. Use it when you need to integrate legacy code, third-party
// libraries, or external systems that have incompatible APIs with your application.

public class ThirdPartyLibraryAdapter {
    
    interface PaymentProcessor {
        void processPayment(double amount);
    }
    
    static class LegacyBillingSystem {
        void charge(int cents) {
            System.out.println("Legacy system charged: $" + (cents / 100.0));
        }
    }
    
    static class BillingAdapter implements PaymentProcessor {
        private final LegacyBillingSystem legacySystem;
        
        BillingAdapter(LegacyBillingSystem legacySystem) {
            this.legacySystem = legacySystem;
        }
        
        @Override
        public void processPayment(double amount) {
            int cents = (int) Math.round(amount * 100);
            legacySystem.charge(cents);
        }
    }
    
    static class ModernCheckoutService {
        private final PaymentProcessor processor;
        
        ModernCheckoutService(PaymentProcessor processor) {
            this.processor = processor;
        }
        
        void checkout(double amount) {
            System.out.println("Processing order...");
            processor.processPayment(amount);
            System.out.println("Order complete!");
        }
    }
    
    public static void main(String[] args) {
        LegacyBillingSystem legacy = new LegacyBillingSystem();
        PaymentProcessor adapter = new BillingAdapter(legacy);
        ModernCheckoutService checkout = new ModernCheckoutService(adapter);
        
        checkout.checkout(99.99);
        checkout.checkout(49.50);
    }
}
