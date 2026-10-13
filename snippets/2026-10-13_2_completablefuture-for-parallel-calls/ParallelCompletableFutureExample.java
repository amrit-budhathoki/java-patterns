// Title: CompletableFuture for Parallel Calls

/**
 * Demonstrates how to use CompletableFuture to make multiple independent
 * network/IO calls in parallel and wait for all results. Useful when you need
 * to fetch data from multiple sources and combine results without blocking threads.
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.List;

record UserData(int id, String name, String email) {}

record UserProfile(int id, String name, String email, String location, String bio) {}

public class ParallelCompletableFutureExample {

    private static UserData fetchUser(int id) {
        sleep(100);
        return new UserData(id, "User" + id, "user" + id + "@example.com");
    }

    private static String fetchLocation(int id) {
        sleep(150);
        return "City" + (id % 3);
    }

    private static String fetchBio(int id) {
        sleep(120);
        return "Bio for user " + id;
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // Start all three calls in parallel
        CompletableFuture<UserData> userFuture = CompletableFuture.supplyAsync(() -> fetchUser(1));
        CompletableFuture<String> locationFuture = CompletableFuture.supplyAsync(() -> fetchLocation(1));
        CompletableFuture<String> bioFuture = CompletableFuture.supplyAsync(() -> fetchBio(1));

        // Combine all results when all complete
        CompletableFuture<UserProfile> profileFuture = userFuture
            .thenCombine(locationFuture, (user, location) -> 
                new UserProfile(user.id(), user.name(), user.email(), location, null))
            .thenCombine(bioFuture, (profile, bio) -> 
                new UserProfile(profile.id(), profile.name(), profile.email(), profile.location(), bio));

        // Wait and display result
        UserProfile profile = profileFuture.join();
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Profile: " + profile);
        System.out.println("Time elapsed: " + elapsed + "ms (parallel, not sequential)");
    }
}
