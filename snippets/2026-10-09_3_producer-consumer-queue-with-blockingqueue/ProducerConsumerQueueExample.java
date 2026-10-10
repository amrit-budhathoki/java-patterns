// Title: Producer Consumer Queue with BlockingQueue

/**
 * Demonstrates a producer-consumer pattern using BlockingQueue.
 * Use this when you need decoupled threads where producers add items
 * and consumers process them asynchronously with thread-safe buffering.
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Item {
    int id;
    String data;
    
    Item(int id, String data) {
        this.id = id;
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "Item{id=" + id + ", data='" + data + "'}";
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Item> queue;
    private final int itemCount;
    
    Producer(BlockingQueue<Item> queue, int itemCount) {
        this.queue = queue;
        this.itemCount = itemCount;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                Item item = new Item(i, "data-" + i);
                queue.put(item);
                System.out.println("Produced: " + item);
                Thread.sleep(100);
            }
            queue.put(new Item(-1, "END"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Item> queue;
    
    Consumer(BlockingQueue<Item> queue) {
        this.queue = queue;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Item item = queue.poll(2, TimeUnit.SECONDS);
                if (item == null) break;
                if (item.id == -1) break;
                System.out.println("Consumed: " + item);
                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerQueueExample {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Item> queue = new LinkedBlockingQueue<>(5);
        
        Thread producer = new Thread(new Producer(queue, 4));
        Thread consumer1 = new Thread(new Consumer(queue));
        Thread consumer2 = new Thread(new Consumer(queue));
        
        producer.start();
        consumer1.start();
        consumer2.start();
        
        producer.join();
        consumer1.join();
        consumer2.join();
        
        System.out.println("Demo completed successfully");
    }
}
