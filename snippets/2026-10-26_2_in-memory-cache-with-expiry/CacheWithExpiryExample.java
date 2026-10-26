// Title: In-Memory Cache with Expiry

// A simple thread-safe cache that automatically removes entries after a specified TTL (time-to-live).
// Use this when you need to cache frequently-accessed data that becomes stale over time,
// such as API responses, computed results, or session data.

import java.util.*;
import java.util.concurrent.*;

public class CacheWithExpiryExample {
    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = new Cache<>(2000);
        
        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob");
        cache.put("user:3", "Charlie");
        
        System.out.println("Initial get user:1: " + cache.get("user:1"));
        System.out.println("Initial get user:2: " + cache.get("user:2"));
        System.out.println("Cache size: " + cache.size());
        
        System.out.println("\nWaiting 2.5 seconds for entries to expire...");
        Thread.sleep(2500);
        
        System.out.println("After expiry get user:1: " + cache.get("user:1"));
        System.out.println("After expiry get user:2: " + cache.get("user:2"));
        System.out.println("Cache size: " + cache.size());
        
        cache.put("user:4", "Diana");
        System.out.println("\nAdded user:4: " + cache.get("user:4"));
        System.out.println("Final cache size: " + cache.size());
    }
}

class Cache<K, V> {
    private static class Entry<V> {
        V value;
        long expiryTime;
        
        Entry(V value, long ttl) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + ttl;
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
    
    private final Map<K, Entry<V>> map = new ConcurrentHashMap<>();
    private final long ttlMillis;
    
    Cache(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }
    
    void put(K key, V value) {
        map.put(key, new Entry<>(value, ttlMillis));
    }
    
    V get(K key) {
        Entry<V> entry = map.get(key);
        if (entry == null) return null;
        if (entry.isExpired()) {
            map.remove(key);
            return null;
        }
        return entry.value;
    }
    
    int size() {
        map.entrySet().removeIf(e -> e.getValue().isExpired());
        return map.size();
    }
}
