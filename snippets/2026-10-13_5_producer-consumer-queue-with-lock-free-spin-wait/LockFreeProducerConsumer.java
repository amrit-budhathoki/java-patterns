// Title: Producer Consumer Queue with Lock-Free Spin-Wait

// Demonstrates a producer-consumer queue using AtomicReference for thread-safe
// lock-free coordination. Useful when you want minimal blocking overhead and
// can tolerate spin-waiting for synchronization between threads.

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.TimeUnit;

public class LockFreeProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        var queue = new LockFreeQueue<String>();
        
        var producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.put("Item-" + i);
                System.out.println("Produced: Item-" + i);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        
        var consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                String item = queue.take();
                System.out.println("Consumed: " + item);
                try { Thread.sleep(150); } catch (InterruptedException e) {}
            }
        });
        
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Done!");
    }
}

class Node<T> {
    T value;
    AtomicReference<Node<T>> next = new AtomicReference<>(null);
    
    Node(T value) {
        this.value = value;
    }
}

class LockFreeQueue<T> {
    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;
    
    LockFreeQueue() {
        var dummy = new Node<T>(null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }
    
    void put(T value) {
        var newNode = new Node<>(value);
        while (true) {
            var last = tail.get();
            var next = last.next.get();
            if (next == null) {
                if (last.next.compareAndSet(null, newNode)) {
                    tail.compareAndSet(last, newNode);
                    return;
                }
            } else {
                tail.compareAndSet(last, next);
            }
        }
    }
    
    T take() {
        while (true) {
            var first = head.get();
            var next = first.next.get();
            if (next != null) {
                T value = next.value;
                if (head.compareAndSet(first, next)) {
                    return value;
                }
            }
            Thread.onSpinWait();
        }
    }
}
