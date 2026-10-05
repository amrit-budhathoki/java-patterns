// Title: Proxy Pattern - Lazy Loading Database Connection

// The Proxy pattern provides a surrogate or placeholder for another object to control access.
// Use it when you want lazy initialization, access control, logging, or caching before
// accessing the real object. This example shows lazy loading of an expensive database connection.

interface Database {
    String query(String sql);
}

class RealDatabase implements Database {
    private String connectionString;

    RealDatabase(String connectionString) {
        this.connectionString = connectionString;
        System.out.println("⏳ Establishing expensive database connection to: " + connectionString);
        try {
            Thread.sleep(500); // Simulate connection delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("✅ Database connected!");
    }

    @Override
    public String query(String sql) {
        return "Result from [" + connectionString + "]: " + sql;
    }
}

class DatabaseProxy implements Database {
    private RealDatabase realDatabase;
    private String connectionString;

    DatabaseProxy(String connectionString) {
        this.connectionString = connectionString;
        this.realDatabase = null; // Lazy - not created yet
    }

    @Override
    public String query(String sql) {
        if (realDatabase == null) {
            System.out.println("🔄 Proxy: First access detected, initializing real database...");
            realDatabase = new RealDatabase(connectionString);
        }
        System.out.println("📤 Proxy: Forwarding query to real database");
        return realDatabase.query(sql);
    }
}

public class ProxyPatternExample {
    public static void main(String[] args) {
        System.out.println("=== Proxy Pattern: Lazy Loading Demo ===\n");

        System.out.println("1️⃣ Creating proxy (no expensive connection yet):");
        Database db = new DatabaseProxy("jdbc:mysql://localhost:3306/myapp");
        System.out.println("✓ Proxy created instantly!\n");

        System.out.println("2️⃣ First query triggers actual connection:");
        String result1 = db.query("SELECT * FROM users");
        System.out.println("Response: " + result1 + "\n");

        System.out.println("3️⃣ Second query reuses existing connection:");
        String result2 = db.query("SELECT * FROM orders");
        System.out.println("Response: " + result2 + "\n");

        System.out.println("✨ Demo complete! Expensive resource was only created when actually needed.");
    }
}
