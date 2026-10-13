// Title: Singleton Pattern with Lazy Initialization

// The Singleton pattern ensures a class has only one instance and provides a global access point.
// Use it for shared resources like database connections, logging, configuration managers, or thread pools.

public class SingletonPatternExample {
    public static void main(String[] args) {
        System.out.println("Accessing Singleton instances...\n");
        
        // First access - creates the instance
        Logger logger1 = Logger.getInstance();
        logger1.log("Application started");
        
        // Second access - returns the same instance
        Logger logger2 = Logger.getInstance();
        logger2.log("Processing data");
        
        // Third access - still the same instance
        Logger logger3 = Logger.getInstance();
        logger3.log("Application ending");
        
        // Verify all references point to the same object
        System.out.println("\nInstance identity check:");
        System.out.println("logger1 == logger2: " + (logger1 == logger2));
        System.out.println("logger2 == logger3: " + (logger2 == logger3));
        System.out.println("All references point to same instance: " + 
                          (logger1 == logger2 && logger2 == logger3));
    }
}

class Logger {
    private static Logger instance;
    private int messageCount;
    
    // Private constructor prevents instantiation from outside
    private Logger() {
        this.messageCount = 0;
    }
    
    // Thread-safe lazy initialization using synchronized
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        messageCount++;
        System.out.println("[Message #" + messageCount + "] " + message);
    }
}
