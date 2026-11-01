// Title: Simple Thread-Safe Counter with Atomic Operations

// A thread-safe counter demonstrating AtomicInteger for concurrent access.
// Use this when multiple threads need to safely increment/decrement a shared counter
// without explicit synchronization or locks.

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;

public class ThreadSafeCounterExample {
    static class Counter {
        private final AtomicInteger value = new AtomicInteger(0);

        void increment() {
            value.incrementAndGet();
        }

        void decrement() {
            value.decrementAndGet();
        }

        int getValue() {
            return value.get();
        }

        int addAndGet(int delta) {
            return value.addAndGet(delta);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        int threadCount = 5;
        int incrementsPerThread = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        System.out.println("Starting counter test with " + threadCount + " threads...");
        System.out.println("Initial counter: " + counter.getValue());

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        int expectedValue = threadCount * incrementsPerThread;
        int actualValue = counter.getValue();

        System.out.println("Expected counter: " + expectedValue);
        System.out.println("Actual counter: " + actualValue);
        System.out.println("Test " + (expectedValue == actualValue ? "PASSED" : "FAILED"));

        counter.addAndGet(50);
        System.out.println("After adding 50: " + counter.getValue());

        counter.decrement();
        System.out.println("After decrement: " + counter.getValue());
    }
}
