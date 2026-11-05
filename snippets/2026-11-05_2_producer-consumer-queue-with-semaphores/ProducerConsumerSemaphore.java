// Title: Producer-Consumer Queue with Semaphores

// A thread-safe queue using Semaphores to coordinate producers and consumers.
// Use when you need explicit control over synchronization between producers and consumers,
// and want to limit queue capacity while blocking threads appropriately.

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumerSemaphore {
    static class BoundedQueue<T> {
        private final Queue<T> queue;
        private final Semaphore empty;
        private final Semaphore full;
        private final Semaphore mutex;

        BoundedQueue(int capacity) {
            this.queue = new LinkedList<>();
            this.empty = new Semaphore(capacity);
            this.full = new Semaphore(0);
            this.mutex = new Semaphore(1);
        }

        void produce(T item) throws InterruptedException {
            empty.acquire();
            mutex.acquire();
            queue.offer(item);
            mutex.release();
            full.release();
        }

        T consume() throws InterruptedException {
            full.acquire();
            mutex.acquire();
            T item = queue.poll();
            mutex.release();
            empty.release();
            return item;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<String> queue = new BoundedQueue<>(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = "Item-" + i;
                    queue.produce(item);
                    System.out.println("Produced: " + item);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = queue.consume();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Done!");
    }
}
