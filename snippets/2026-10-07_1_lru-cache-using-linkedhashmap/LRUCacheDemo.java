// Title: LRU Cache using LinkedHashMap

import java.util.*;

/**
 * An LRU (Least Recently Used) Cache implementation using LinkedHashMap.
 * 
 * This is useful when you need to cache a limited number of items and
 * automatically evict the least recently used items when capacity is reached.
 * Common uses include CPU caches, database query caches, and web page caches.
 */
public class LRUCacheDemo {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);
        
        // Add some entries
        cache.put("user1", "Alice");
        cache.put("user2", "Bob");
        cache.put("user3", "Charlie");
        System.out.println("After adding 3 items: " + cache);
        
        // Access user1 (makes it recently used)
        System.out.println("Getting user1: " + cache.get("user1"));
        System.out.println("After accessing user1: " + cache);
        
        // Add a new item, should evict user2 (least recently used)
        cache.put("user4", "David");
        System.out.println("After adding user4: " + cache);
        
        // Try to get evicted item
        System.out.println("Getting user2 (should be null): " + cache.get("user2"));
        
        // Access user3, then add user5 to evict user3
        cache.get("user3");
        cache.put("user5", "Eve");
        System.out.println("After adding user5: " + cache);
    }
}

class LRUCache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> cache;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // LinkedHashMap with accessOrder=true maintains insertion/access order
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }
    
    public void put(K key, V value) {
        cache.put(key, value);
    }
    
    public V get(K key) {
        return cache.get(key);
    }
    
    @Override
    public String toString() {
        return cache.toString();
    }
}
