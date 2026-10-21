// Title: Optional for Safe Null Handling in Data Processing Pipelines

// Demonstrates using Optional to safely navigate and transform potentially null values
// in a data processing chain without explicit null checks. Use this pattern when
// working with APIs that return nullable values or when chaining operations that
// might fail to produce a result.

import java.util.Optional;
import java.util.List;

record User(String id, String name, String email) {}

record UserRepository(List<User> users) {
    Optional<User> findById(String id) {
        return users.stream()
            .filter(u -> u.id().equals(id))
            .findFirst();
    }
}

record EmailService() {
    Optional<String> extractDomain(String email) {
        int atIndex = email.lastIndexOf('@');
        return atIndex > 0
            ? Optional.of(email.substring(atIndex + 1))
            : Optional.empty();
    }
}

public class OptionalDataPipelineExample {
    public static void main(String[] args) {
        List<User> users = List.of(
            new User("1", "Alice", "alice@example.com"),
            new User("2", "Bob", "bob@company.org"),
            new User("3", "Charlie", null)
        );

        UserRepository repo = new UserRepository(users);
        EmailService emailService = new EmailService();

        // Safe pipeline: find user -> extract email -> extract domain
        String result = repo.findById("1")
            .map(User::email)
            .flatMap(emailService::extractDomain)
            .map(domain -> "Domain: " + domain)
            .orElse("Could not extract domain");
        System.out.println(result);

        // Handles missing user gracefully
        String missing = repo.findById("99")
            .map(User::email)
            .flatMap(emailService::extractDomain)
            .map(domain -> "Domain: " + domain)
            .orElse("User not found");
        System.out.println(missing);

        // Handles null email gracefully
        String nullEmail = repo.findById("3")
            .map(User::email)
            .flatMap(emailService::extractDomain)
            .map(domain -> "Domain: " + domain)
            .orElse("Email not available");
        System.out.println(nullEmail);

        // Using ifPresentOrElse for side effects
        repo.findById("2").ifPresentOrElse(
            user -> System.out.println("Processing user: " + user.name()),
            () -> System.out.println("User not found")
        );
    }
}
