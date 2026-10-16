// Title: Producer Consumer Queue with Condition Variables

// Demonstrates a thread-safe producer-consumer queue using Object.wait() and notify().
// This pattern is useful when you need to coordinate work between multiple threads
// and want explicit control over synchronization without using higher-level APIs.

import java.util.*;

class Queue<T> {
    private final LinkedList<T> buffer = new LinkedList<>();
    private final int capacity;
    
    Queue(int capacity) {
        this.capacity = capacity;
    }
    
    synchronized void produce(T item) throws InterruptedException {
        while (buffer.size() >= capacity) {
            wait();
        }
        buffer.add(item);
        notifyAll();
    }
    
    synchronized T consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        T item = buffer.removeFirst();
        notifyAll();
        return item;
    }
}

record Task(int id, String name) {}

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        Queue<Task> queue = new Queue<>(3);
        
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Task task = new Task(i, "Task-" + i);
                    queue.produce(task);
                    System.out.println("Produced: " + task);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Task task = queue.consume();
                    System.out.println("Consumed: " + task);
                    Thread.sleep(200);
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
