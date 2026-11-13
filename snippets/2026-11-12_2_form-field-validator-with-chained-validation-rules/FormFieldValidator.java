// Title: Form Field Validator with Chained Validation Rules

// This example demonstrates a fluent API for validating form fields with multiple
// rules and accumulating all validation errors. Use this when you need clear, 
// detailed feedback about what went wrong with user input across multiple constraints.

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FormFieldValidator {
    public static void main(String[] args) {
        // Example 1: Valid email
        try {
            validateEmail("user@example.com");
            System.out.println("✓ user@example.com is valid");
        } catch (ValidationException e) {
            System.out.println("✗ " + e.getMessage());
        }

        // Example 2: Invalid email with multiple errors
        try {
            validateEmail("invalid.email");
        } catch (ValidationException e) {
            System.out.println("✗ " + e.getMessage());
        }

        // Example 3: Valid password
        try {
            validatePassword("SecurePass123!");
            System.out.println("✓ SecurePass123! is valid");
        } catch (ValidationException e) {
            System.out.println("✗ " + e.getMessage());
        }

        // Example 4: Invalid password with multiple errors
        try {
            validatePassword("weak");
        } catch (ValidationException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }

    private static void validateEmail(String email) {
        FieldValidator validator = new FieldValidator("Email")
            .notEmpty("Email cannot be empty")
            .matches(".*@.*\\..*", "Email must contain @ and a domain")
            .minLength(5, "Email must be at least 5 characters")
            .maxLength(100, "Email must not exceed 100 characters");
        validator.validate(email);
    }

    private static void validatePassword(String password) {
        FieldValidator validator = new FieldValidator("Password")
            .notEmpty("Password cannot be empty")
            .minLength(8, "Password must be at least 8 characters")
            .matches(".*[A-Z].*", "Password must contain an uppercase letter")
            .matches(".*[0-9].*", "Password must contain a digit")
            .matches(".*[!@#$%^&*].*", "Password must contain a special character");
        validator.validate(password);
    }
}

class FieldValidator {
    private final String fieldName;
    private final List<ValidationRule> rules = new ArrayList<>();

    FieldValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    FieldValidator notEmpty(String errorMessage) {
        rules.add(new ValidationRule(
            value -> value != null && !value.trim().isEmpty(),
            errorMessage
        ));
        return this;
    }

    FieldValidator minLength(int min, String errorMessage) {
        rules.add(new ValidationRule(
            value -> value != null && value.length() >= min,
            errorMessage
        ));
        return this;
    }

    FieldValidator maxLength(int max, String errorMessage) {
        rules.add(new ValidationRule(
            value -> value != null && value.length() <= max,
            errorMessage
        ));
        return this;
    }

    FieldValidator matches(String regex, String errorMessage) {
        rules.add(new ValidationRule(
            value -> value != null && value.matches(regex),
            errorMessage
        ));
        return this;
    }

    void validate(String value) {
        List<String> errors = new ArrayList<>();
        for (ValidationRule rule : rules) {
            if (!rule.isValid(value)) {
                errors.add(rule.errorMessage());
            }
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(fieldName, errors);
        }
    }
}

record ValidationRule(Predicate<String> predicate, String errorMessage) {
    boolean isValid(String value) {
        return predicate.test(value);
    }
}

class ValidationException extends RuntimeException {
    ValidationException(String fieldName, List<String> errors) {
        super(fieldName + ": " + String.join("; ", errors));
    }
}
