// Title: Email and Password Validator with Detailed Feedback

// A validation utility that checks user input against multiple rules
// and provides specific, actionable error messages. Use this when you need
// to validate user registration data with clear feedback on what went wrong.

public class InputValidationExample {
    
    static class ValidationResult {
        boolean valid;
        String message;
        
        ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
    }
    
    static class UserValidator {
        static ValidationResult validateEmail(String email) {
            if (email == null || email.isBlank()) {
                return new ValidationResult(false, "Email cannot be empty");
            }
            if (email.length() > 254) {
                return new ValidationResult(false, "Email exceeds maximum length of 254 characters");
            }
            if (!email.contains("@")) {
                return new ValidationResult(false, "Email must contain @ symbol");
            }
            String[] parts = email.split("@");
            if (parts.length != 2) {
                return new ValidationResult(false, "Email must have exactly one @ symbol");
            }
            if (parts[0].isEmpty()) {
                return new ValidationResult(false, "Email local part (before @) cannot be empty");
            }
            if (parts[1].isEmpty() || !parts[1].contains(".")) {
                return new ValidationResult(false, "Email domain must contain at least one dot (.)");
            }
            return new ValidationResult(true, "Email is valid");
        }
        
        static ValidationResult validatePassword(String password) {
            if (password == null || password.isEmpty()) {
                return new ValidationResult(false, "Password cannot be empty");
            }
            if (password.length() < 8) {
                return new ValidationResult(false, "Password must be at least 8 characters long");
            }
            if (!password.matches(".*[A-Z].*")) {
                return new ValidationResult(false, "Password must contain at least one uppercase letter");
            }
            if (!password.matches(".*[a-z].*")) {
                return new ValidationResult(false, "Password must contain at least one lowercase letter");
            }
            if (!password.matches(".*\\d.*")) {
                return new ValidationResult(false, "Password must contain at least one digit");
            }
            return new ValidationResult(true, "Password is valid");
        }
    }
    
    public static void main(String[] args) {
        String[][] testCases = {
            {"user@example.com", "SecurePass123"},
            {"invalid.email", "weak"},
            {"@example.com", "StrongPass1"},
            {"user@domain", "ValidPass99"},
            {"user@example.com", "NoNumbers"},
        };
        
        for (String[] testCase : testCases) {
            String email = testCase[0];
            String password = testCase[1];
            
            ValidationResult emailResult = UserValidator.validateEmail(email);
            ValidationResult passwordResult = UserValidator.validatePassword(password);
            
            System.out.println("Email: " + email);
            System.out.println("  → " + (emailResult.valid ? "✓" : "✗") + " " + emailResult.message);
            System.out.println("Password: " + password);
            System.out.println("  → " + (passwordResult.valid ? "✓" : "✗") + " " + passwordResult.message);
            System.out.println();
        }
    }
}
