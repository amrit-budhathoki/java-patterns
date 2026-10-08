// Title: Simple Circuit Breaker

/*
 * A circuit breaker pattern implementation that prevents cascading failures
 * by temporarily stopping calls to a failing service. Use this when you need
 * to protect against repeated failures to external services or dependencies.
 */

import java.time.Instant;

enum CircuitState {
    CLOSED, OPEN, HALF_OPEN
}

class CircuitBreaker {
    private CircuitState state = CircuitState.CLOSED;
    private int failureCount = 0;
    private int successCount = 0;
    private Instant lastFailureTime;
    private final int failureThreshold;
    private final int successThreshold;
    private final int timeout;

    CircuitBreaker(int failureThreshold, int successThreshold, int timeoutSeconds) {
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
        this.timeout = timeoutSeconds;
    }

    synchronized void recordSuccess() {
        failureCount = 0;
        if (state == CircuitState.HALF_OPEN) {
            successCount++;
            if (successCount >= successThreshold) {
                state = CircuitState.CLOSED;
                successCount = 0;
                System.out.println("✓ Circuit CLOSED - service recovered");
            }
        }
    }

    synchronized void recordFailure() {
        lastFailureTime = Instant.now();
        failureCount++;
        if (failureCount >= failureThreshold && state == CircuitState.CLOSED) {
            state = CircuitState.OPEN;
            System.out.println("✗ Circuit OPEN - too many failures");
        }
    }

    synchronized boolean isAvailable() {
        if (state == CircuitState.CLOSED) {
            return true;
        }
        if (state == CircuitState.OPEN) {
            long elapsedSeconds = Instant.now().getEpochSecond() - lastFailureTime.getEpochSecond();
            if (elapsedSeconds >= timeout) {
                state = CircuitState.HALF_OPEN;
                successCount = 0;
                System.out.println("~ Circuit HALF_OPEN - attempting recovery");
                return true;
            }
            return false;
        }
        return true; // HALF_OPEN state allows calls through
    }

    synchronized CircuitState getState() {
        return state;
    }
}

class UnreliableService {
    private int callCount = 0;

    boolean call() {
        callCount++;
        // Fail on calls 2-4, succeed otherwise
        return callCount < 2 || callCount > 4;
    }
}

public class SimpleCircuitBreakerExample {
    public static void main(String[] args) throws InterruptedException {
        CircuitBreaker breaker = new CircuitBreaker(3, 2, 2);
        UnreliableService service = new UnreliableService();

        System.out.println("Testing Circuit Breaker Pattern\n");

        // Initial successful calls
        for (int i = 1; i <= 4; i++) {
            if (breaker.isAvailable()) {
                boolean success = service.call();
                if (success) {
                    breaker.recordSuccess();
                    System.out.println("Call " + i + ": SUCCESS");
                } else {
                    breaker.recordFailure();
                    System.out.println("Call " + i + ": FAILURE");
                }
            } else {
                System.out.println("Call " + i + ": REJECTED (circuit open)");
            }
        }

        System.out.println("\nWaiting for timeout...");
        Thread.sleep(2500);

        // Try recovery
        System.out.println("\nRecovery attempts:");
        for (int i = 5; i <= 7; i++) {
            if (breaker.isAvailable()) {
                boolean success = service.call();
                if (success) {
                    breaker.recordSuccess();
                    System.out.println("Call " + i + ": SUCCESS");
                } else {
                    breaker.recordFailure();
                    System.out.println("Call " + i + ": FAILURE");
                }
            } else {
                System.out.println("Call " + i + ": REJECTED (circuit open)");
            }
        }

        System.out.println("\nFinal state: " + breaker.getState());
    }
}
