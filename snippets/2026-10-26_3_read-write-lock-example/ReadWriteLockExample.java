// Title: Read-Write Lock Example

// A ReadWriteLock allows multiple threads to read simultaneously while ensuring
// exclusive write access. Use this when you have frequent reads and occasional writes
// to shared data, to maximize concurrency compared to a simple synchronized lock.

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    static class SharedCounter {
        private int value = 0;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        int read() {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " reading: " + value);
                Thread.sleep(100);
                return value;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            } finally {
                lock.readLock().unlock();
            }
        }

        void write(int newValue) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " writing: " + newValue);
                Thread.sleep(100);
                value = newValue;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SharedCounter counter = new SharedCounter();

        Thread reader1 = new Thread(() -> counter.read(), "Reader-1");
        Thread reader2 = new Thread(() -> counter.read(), "Reader-2");
        Thread writer = new Thread(() -> counter.write(42), "Writer-1");

        reader1.start();
        reader2.start();
        writer.start();
        reader1.join();
        reader2.join();
        writer.join();

        Thread reader3 = new Thread(() -> counter.read(), "Reader-3");
        reader3.start();
        reader3.join();
    }
}
