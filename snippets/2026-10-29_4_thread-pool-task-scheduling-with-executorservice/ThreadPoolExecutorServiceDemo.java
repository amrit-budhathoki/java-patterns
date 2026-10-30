// Title: Thread Pool Task Scheduling with ExecutorService

// Demonstrates using ExecutorService to manage a fixed thread pool for concurrent task execution.
// Use this when you need to process multiple independent tasks concurrently without creating
// a new thread for each task, which saves resources and improves performance.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Task implements Runnable {
    private final int id;
    private final AtomicInteger counter;

    Task(int id, AtomicInteger counter) {
        this.id = id;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            System.out.println("Task " + id + " started by " + Thread.currentThread().getName());
            Thread.sleep(500);
            counter.incrementAndGet();
            System.out.println("Task " + id + " completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Task " + id + " interrupted");
        }
    }
}

public class ThreadPoolExecutorServiceDemo {
    public static void main(String[] args) {
        int poolSize = 3;
        int taskCount = 10;
        AtomicInteger completedTasks = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        System.out.println("Submitting " + taskCount + " tasks to pool of " + poolSize + " threads\n");

        for (int i = 1; i <= taskCount; i++) {
            executor.submit(new Task(i, completedTasks));
        }

        executor.shutdown();

        try {
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("\nAll tasks completed. Total: " + completedTasks.get());
            } else {
                System.out.println("Timeout waiting for tasks to complete");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
