// Title: Thread Pool for Parallel Task Processing

// A simple demonstration of ExecutorService for managing a fixed pool of threads.
// Use this when you need to execute multiple independent tasks concurrently
// without creating a new thread for each one, which is more efficient and controlled.

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("Submitting 8 tasks to a 3-thread pool...\n");
        
        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            executor.submit(() -> processTask(taskId));
        }
        
        executor.shutdown();
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);
        
        if (finished) {
            System.out.println("\nAll tasks completed successfully.");
        } else {
            System.out.println("\nTimeout: some tasks did not complete.");
            executor.shutdownNow();
        }
    }
    
    private static void processTask(int taskId) {
        String threadName = Thread.currentThread().getName();
        System.out.println("Task " + taskId + " started on " + threadName);
        
        try {
            Thread.sleep((long) (Math.random() * 500) + 200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Task " + taskId + " completed on " + threadName);
    }
}
