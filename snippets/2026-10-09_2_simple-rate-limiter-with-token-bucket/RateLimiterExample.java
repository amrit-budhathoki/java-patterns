// Title: Simple Rate Limiter with Token Bucket

// A rate limiter that controls how often an operation can execute.
// Use this when you need to prevent a service from being overwhelmed
// by limiting requests to a certain rate (e.g., API calls, database queries).

import java.time.Instant;

public class RateLimiterExample {
    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter(5, 2000); // 5 tokens per 2 seconds
        
        System.out.println("Attempting 8 requests with 5 tokens per 2 seconds:");
        for (int i = 1; i <= 8; i++) {
            if (limiter.allowRequest()) {
                System.out.println("Request " + i + ": ALLOWED");
            } else {
                System.out.println("Request " + i + ": REJECTED (rate limit exceeded)");
            }
        }
        
        System.out.println("\nWaiting 2.1 seconds for tokens to refill...");
        try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nAttempting 3 more requests:");
        for (int i = 9; i <= 11; i++) {
            if (limiter.allowRequest()) {
                System.out.println("Request " + i + ": ALLOWED");
            } else {
                System.out.println("Request " + i + ": REJECTED (rate limit exceeded)");
            }
        }
    }
}

class RateLimiter {
    private final int capacity;
    private final long refillInterval;
    private double tokens;
    private long lastRefillTime;
    
    public RateLimiter(int capacity, long refillIntervalMs) {
        this.capacity = capacity;
        this.refillInterval = refillIntervalMs;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    public synchronized boolean allowRequest() {
        refillTokens();
        
        if (tokens >= 1.0) {
            tokens -= 1.0;
            return true;
        }
        return false;
    }
    
    private void refillTokens() {
        long now = System.currentTimeMillis();
        long timePassed = now - lastRefillTime;
        
        if (timePassed >= refillInterval) {
            double refillRate = (double) capacity / refillInterval;
            double tokensToAdd = refillRate * timePassed;
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }
}
