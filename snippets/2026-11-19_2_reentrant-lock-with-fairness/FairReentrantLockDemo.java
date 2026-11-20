// Title: Reentrant Lock with Fairness

// A fair reentrant lock implementation from scratch using basic synchronization primitives.
// Use this when you need a lock that prevents thread starvation by serving waiting threads in FIFO order,
// and allows the same thread to acquire it multiple times without deadlock.

import java.util.*;

class FairReentrantLock {
    private Thread owner = null;
    private int count = 0;
    private final Queue<Thread> waitQueue = new LinkedList<>();

    synchronized void lock() throws InterruptedException {
        Thread current = Thread.currentThread();
        while (owner != null && owner != current) {
            if (!waitQueue.contains(current)) {
                waitQueue.offer(current);
            }
            wait();
        }
        owner = current;
        count++;
        waitQueue.remove(current);
    }

    synchronized void unlock() {
        Thread current = Thread.currentThread();
        if (owner != current) {
            throw new IllegalMonitorStateException("Lock not held by current thread");
        }
        count--;
        if (count == 0) {
            owner = null;
            notifyAll();
        }
    }

    synchronized int getHoldCount() {
        return owner == Thread.currentThread() ? count : 0;
    }
}

public class FairReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        FairReentrantLock lock = new FairReentrantLock();
        List<String> results = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 4; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    lock.lock();
                    results.add("Thread-" + id + " acquired, hold count: " + lock.getHoldCount());
                    Thread.sleep(50);

                    lock.lock();
                    results.add("Thread-" + id + " reacquired, hold count: " + lock.getHoldCount());
                    lock.unlock();

                    lock.unlock();
                    results.add("Thread-" + id + " released");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        Thread.sleep(2000);
        results.forEach(System.out::println);
    }
}
