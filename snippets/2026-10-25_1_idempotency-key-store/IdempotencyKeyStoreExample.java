// Title: Idempotency Key Store

// An idempotency key store prevents duplicate processing of requests by tracking
// request identifiers and their responses. Use this when building APIs that need
// to handle retries safely without executing the same operation twice.

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

record IdempotencyResult(String requestId, String response, long timestamp) {}

class IdempotencyKeyStore {
    private final ConcurrentHashMap<String, IdempotencyResult> store = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final long expiryMs;

    IdempotencyKeyStore(long expiryMs) {
        this.expiryMs = expiryMs;
    }

    String processRequest(String idempotencyKey, String operation) {
        lock.readLock().lock();
        try {
            IdempotencyResult cached = store.get(idempotencyKey);
            if (cached != null && !isExpired(cached)) {
                return cached.response();
            }
        } finally {
            lock.readLock().unlock();
        }

        // Execute operation
        String response = executeOperation(operation);

        // Store result
        lock.writeLock().lock();
        try {
            store.put(idempotencyKey, new IdempotencyResult(idempotencyKey, response, System.currentTimeMillis()));
        } finally {
            lock.writeLock().unlock();
        }

        return response;
    }

    private String executeOperation(String operation) {
        return "Result of: " + operation;
    }

    private boolean isExpired(IdempotencyResult result) {
        return System.currentTimeMillis() - result.timestamp() > expiryMs;
    }
}

public class IdempotencyKeyStoreExample {
    public static void main(String[] args) {
        IdempotencyKeyStore store = new IdempotencyKeyStore(5000);

        // First request with idempotency key
        String key1 = "txn-001";
        String result1 = store.processRequest(key1, "transfer 100 USD");
        System.out.println("First call: " + result1);

        // Retry with same key - returns cached result
        String result2 = store.processRequest(key1, "transfer 100 USD");
        System.out.println("Retry call: " + result2);
        System.out.println("Same result: " + result1.equals(result2));

        // Different key - executes new operation
        String key2 = "txn-002";
        String result3 = store.processRequest(key2, "transfer 200 USD");
        System.out.println("Different key: " + result3);
        System.out.println("Different result: " + !result1.equals(result3));
    }
}
