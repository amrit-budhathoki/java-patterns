// Title: Thread Pool with ExecutorService

// Demonstrates how to use ExecutorService to manage a fixed thread pool for executing tasks concurrently.
// Use this when you need to limit the number of concurrent threads, reuse threads efficiently, and queue
// tasks for asynchronous execution without creating a new thread for each task.

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit 10 tasks to the thread pool
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " started by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed");
            });
        }
        
        // Initiate shutdown and wait for all tasks to complete
        executor.shutdown();
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);
        
        if (finished) {
            System.out.println("All tasks completed successfully");
        } else {
            System.out.println("Some tasks did not complete in time");
            executor.shutdownNow();
        }
    }
}
