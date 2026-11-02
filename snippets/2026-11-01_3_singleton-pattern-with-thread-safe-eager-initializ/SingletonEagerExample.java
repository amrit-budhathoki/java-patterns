// Title: Singleton Pattern with Thread-Safe Eager Initialization

// Demonstrates the Singleton pattern using eager initialization.
// Use this when you need exactly one instance of a class that should be created
// at JVM startup. It's simple, thread-safe, and works well for most scenarios.

public class SingletonEagerExample {
    public static void main(String[] args) {
        // Get the singleton instance
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        // Both references point to the same instance
        System.out.println("Same instance: " + (logger1 == logger2));
        
        // Use the singleton
        logger1.log("Application started");
        logger2.log("Processing request");
        logger1.log("Application stopped");
        
        System.out.println("Total logs: " + logger1.getLogCount());
    }
}

class Logger {
    private static final Logger INSTANCE = new Logger();
    private int logCount = 0;
    
    // Private constructor prevents instantiation
    private Logger() {
    }
    
    // Public accessor for the singleton instance
    public static Logger getInstance() {
        return INSTANCE;
    }
    
    public void log(String message) {
        logCount++;
        System.out.println("[LOG " + logCount + "] " + message);
    }
    
    public int getLogCount() {
        return logCount;
    }
}
