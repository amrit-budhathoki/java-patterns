// Title: Singleton Pattern with Bill Pugh Technique

// The Singleton pattern ensures a class has only one instance and provides global access to it.
// This uses the Bill Pugh technique (class loader initialization) which is thread-safe, lazy, and doesn't
// require synchronized blocks. Use this when you need exactly one instance of a class throughout your
// application, like a configuration manager, logger, or database connection pool.

public class SingletonDemo {
    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern (Bill Pugh Technique) ===\n");
        
        // Get instances from different parts of code
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        DatabaseConnection db3 = DatabaseConnection.getInstance();
        
        // All references point to the same instance
        System.out.println("db1 == db2: " + (db1 == db2));
        System.out.println("db2 == db3: " + (db2 == db3));
        
        System.out.println("\nUsing singleton instance:");
        db1.connect();
        db1.query("SELECT * FROM users");
        
        System.out.println("\nAccessing from another reference:");
        db2.query("SELECT * FROM products");
        
        System.out.println("\nConnection state is shared:");
        System.out.println("Is connected: " + db3.isConnected());
    }
}

class DatabaseConnection {
    private boolean connected = false;
    
    // Private constructor prevents instantiation from outside
    private DatabaseConnection() {
    }
    
    // Inner class that holds the single instance (lazy initialized by class loader)
    private static class SingletonHolder {
        static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }
    
    // Public method to get the singleton instance
    public static DatabaseConnection getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public void connect() {
        if (!connected) {
            System.out.println("[DB] Establishing connection...");
            connected = true;
            System.out.println("[DB] Connected to database");
        } else {
            System.out.println("[DB] Already connected");
        }
    }
    
    public void query(String sql) {
        if (connected) {
            System.out.println("[DB] Executing: " + sql);
        } else {
            System.out.println("[DB] Error: Not connected");
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
}
