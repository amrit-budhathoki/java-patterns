// Title: Retry Policy with Exponential Backoff

// A retry mechanism that automatically retries failed operations with exponential backoff.
// Useful when calling unreliable external services that may temporarily fail.

import java.util.Random;
import java.util.function.Supplier;

public class RetryPolicyExample {
    public static void main(String[] args) {
        RetryPolicy policy = new RetryPolicy(3, 100, 2.0);
        
        System.out.println("Attempt 1 - Flaky service:");
        String result = policy.execute(() -> flakyService(0.7));
        System.out.println("Final result: " + result);
        
        System.out.println("\nAttempt 2 - Eventually succeeds:");
        counter = 0;
        result = policy.execute(() -> eventualSuccess(2));
        System.out.println("Final result: " + result);
    }
    
    static int counter = 0;
    
    static String flakyService(double failureRate) {
        if (Math.random() < failureRate) {
            throw new RuntimeException("Service temporarily unavailable");
        }
        return "Success!";
    }
    
    static String eventualSuccess(int failAfter) {
        if (counter++ < failAfter) {
            throw new RuntimeException("Not ready yet");
        }
        return "Finally succeeded!";
    }
}

class RetryPolicy {
    private final int maxRetries;
    private final long initialDelayMs;
    private final double backoffMultiplier;
    
    RetryPolicy(int maxRetries, long initialDelayMs, double backoffMultiplier) {
        this.maxRetries = maxRetries;
        this.initialDelayMs = initialDelayMs;
        this.backoffMultiplier = backoffMultiplier;
    }
    
    <T> T execute(Supplier<T> supplier) {
        long delayMs = initialDelayMs;
        
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                System.out.println("  Attempt " + attempt + "...");
                return supplier.get();
            } catch (Exception e) {
                System.out.println("  Failed: " + e.getMessage());
                if (attempt == maxRetries) {
                    throw new RuntimeException("All retries exhausted", e);
                }
                try {
                    Thread.sleep(delayMs);
                    delayMs = (long) (delayMs * backoffMultiplier);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
            }
        }
        throw new RuntimeException("Unreachable");
    }
}
