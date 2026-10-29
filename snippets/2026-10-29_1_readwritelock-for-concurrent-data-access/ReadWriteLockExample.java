// Title: ReadWriteLock for Concurrent Data Access

// Demonstrates a ReadWriteLock pattern where multiple threads can read simultaneously,
// but writes have exclusive access. Use this when you have frequent reads and infrequent
// writes to improve concurrency compared to a simple synchronized lock.

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    static class Counter {
        private int value = 0;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public void increment() {
            lock.writeLock().lock();
            try {
                value++;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public int getValue() {
            lock.readLock().lock();
            try {
                return value;
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Create multiple reader threads
        Thread[] readers = new Thread[4];
        for (int i = 0; i < 4; i++) {
            int id = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    int val = counter.getValue();
                    System.out.println("Reader " + id + " read: " + val);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // Create writer thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                counter.increment();
                System.out.println("Writer incremented to: " + counter.getValue());
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Start all threads
        for (Thread reader : readers) {
            reader.start();
        }
        writer.start();

        // Wait for all threads
        for (Thread reader : readers) {
            reader.join();
        }
        writer.join();

        System.out.println("Final value: " + counter.getValue());
    }
}
