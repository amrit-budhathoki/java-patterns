// Title: Bounded Blocking Queue from Scratch

/**
 * A thread-safe bounded queue that blocks producers when full and consumers when empty.
 * Use this when you need to coordinate multiple threads with a fixed-size buffer,
 * implementing backpressure to prevent unbounded memory growth.
 */

import java.util.LinkedList;
import java.util.Queue;

class BoundedBlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;
    private final Object lock = new Object();

    BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    void put(T item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() >= capacity) {
                lock.wait();
            }
            queue.offer(item);
            lock.notifyAll();
        }
    }

    T take() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }
            T item = queue.poll();
            lock.notifyAll();
            return item;
        }
    }

    int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
}

public class BoundedBlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Producer: putting " + i);
                    queue.put(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(300);
                    int item = queue.take();
                    System.out.println("Consumer: took " + item + ", queue size: " + queue.size());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Done! Final queue size: " + queue.size());
    }
}
