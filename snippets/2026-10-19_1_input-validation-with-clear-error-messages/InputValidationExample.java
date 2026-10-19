// Title: Input Validation with Clear Error Messages

// Demonstrates a structured approach to validate user input with descriptive error messages.
// Use this pattern when you need to validate multiple fields and provide specific feedback
// about what went wrong and how to fix it, making user experience clearer.

import java.util.ArrayList;
import java.util.List;

public class InputValidationExample {
    public static void main(String[] args) {
        demonstrateValidation();
    }

    private static void demonstrateValidation() {
        System.out.println("=== Input Validation Examples ===\n");

        testUserRegistration("", "password123");
        testUserRegistration("john.doe", "");
        testUserRegistration("jane.doe@example.com", "short");
        testUserRegistration("valid.user@example.com", "SecurePass123!");
    }

    private static void testUserRegistration(String email, String password) {
        ValidationResult result = validateUserInput(email, password);

        if (result.isValid()) {
            System.out.println("✓ Registration successful for: " + email);
        } else {
            System.out.println("✗ Registration failed:");
            result.getErrors().forEach(error -> System.out.println("  • " + error));
        }
        System.out.println();
    }

    private static ValidationResult validateUserInput(String email, String password) {
        List<String> errors = new ArrayList<>();

        if (email == null || email.isBlank()) {
            errors.add("Email is required");
        } else if (!email.contains("@") || !email.contains(".")) {
            errors.add("Email must be a valid format (e.g., user@example.com)");
        }

        if (password == null || password.isBlank()) {
            errors.add("Password is required");
        } else if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long (currently " + password.length() + ")");
        } else if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        } else if (!password.matches(".*[0-9].*")) {
            errors.add("Password must contain at least one digit");
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }
}

record ValidationResult(boolean isValid, List<String> getErrors) {
}
