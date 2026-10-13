// Title: Proxy Pattern - Logging Decorator

// The Proxy pattern provides a placeholder for another object to control access to it.
// This example shows a proxy that wraps a service to add logging, timing, and caching
// without modifying the original service. Use this when you need to add cross-cutting
// concerns like logging, caching, or access control transparently.

interface DataService {
    String fetchData(String key);
}

class RealDataService implements DataService {
    @Override
    public String fetchData(String key) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Data for " + key;
    }
}

class LoggingProxy implements DataService {
    private final DataService realService;
    private final java.util.Map<String, String> cache = new java.util.HashMap<>();
    
    LoggingProxy(DataService realService) {
        this.realService = realService;
    }
    
    @Override
    public String fetchData(String key) {
        System.out.println("[LOG] Fetching data for key: " + key);
        
        if (cache.containsKey(key)) {
            System.out.println("[LOG] Cache hit!");
            return cache.get(key);
        }
        
        long startTime = System.currentTimeMillis();
        String result = realService.fetchData(key);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("[LOG] Request completed in " + elapsed + "ms");
        cache.put(key, result);
        return result;
    }
}

public class ProxyPatternLoggingExample {
    public static void main(String[] args) {
        DataService service = new LoggingProxy(new RealDataService());
        
        System.out.println(service.fetchData("user123"));
        System.out.println();
        
        System.out.println(service.fetchData("user456"));
        System.out.println();
        
        System.out.println("Second call (cached):");
        System.out.println(service.fetchData("user123"));
    }
}
