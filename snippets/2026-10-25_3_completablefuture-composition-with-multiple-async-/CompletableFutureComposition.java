// Title: CompletableFuture Composition with Multiple Async Sources

// This example demonstrates composing multiple async operations and combining their results.
// Use this when you need to fetch data from multiple sources in parallel and then process the combined result.

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureComposition {
    
    static class DataService {
        CompletableFuture<String> fetchUserName(int userId) {
            return CompletableFuture.supplyAsync(() -> {
                sleep(100);
                return "User_" + userId;
            });
        }
        
        CompletableFuture<String> fetchUserEmail(int userId) {
            return CompletableFuture.supplyAsync(() -> {
                sleep(150);
                return "user" + userId + "@example.com";
            });
        }
        
        CompletableFuture<String> fetchUserRole(int userId) {
            return CompletableFuture.supplyAsync(() -> {
                sleep(120);
                return "ADMIN";
            });
        }
        
        private void sleep(long ms) {
            try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
    
    record UserProfile(String name, String email, String role) {}
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DataService service = new DataService();
        int userId = 42;
        
        long startTime = System.currentTimeMillis();
        
        // Fetch all three pieces of data in parallel
        CompletableFuture<String> nameFuture = service.fetchUserName(userId);
        CompletableFuture<String> emailFuture = service.fetchUserEmail(userId);
        CompletableFuture<String> roleFuture = service.fetchUserRole(userId);
        
        // Combine all results when all futures complete
        CompletableFuture<UserProfile> profileFuture = nameFuture.thenCombine(emailFuture, 
                (name, email) -> new UserProfile(name, email, null))
            .thenCombine(roleFuture, 
                (profile, role) -> new UserProfile(profile.name(), profile.email(), role));
        
        // Wait for all operations to complete
        UserProfile profile = profileFuture.get();
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Profile: " + profile);
        System.out.println("Total time: " + elapsed + "ms (parallel execution)");
    }
}
