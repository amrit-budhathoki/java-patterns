// Title: Generic Result Type

/*
 * A generic Result type that encapsulates success or failure outcomes.
 * Use this when you want to return either a successful value or an error
 * without relying on exceptions or null values.
 */

sealed interface Result<T> permits Success, Failure {}

record Success<T>(T value) implements Result<T> {}

record Failure<T>(String error) implements Result<T> {}

public class GenericResultExample {
    static Result<Integer> divide(int numerator, int denominator) {
        if (denominator == 0) {
            return new Failure<>("Division by zero");
        }
        return new Success<>(numerator / denominator);
    }

    static Result<String> fetchUser(int userId) {
        if (userId < 0) {
            return new Failure<>("Invalid user ID");
        }
        return new Success<>("User #" + userId);
    }

    static <T> void handleResult(Result<T> result, String operation) {
        switch (result) {
            case Success<T> success -> System.out.println("✓ " + operation + ": " + success.value());
            case Failure<T> failure -> System.out.println("✗ " + operation + ": " + failure.error());
        }
    }

    public static void main(String[] args) {
        Result<Integer> division1 = divide(10, 2);
        handleResult(division1, "10 ÷ 2");

        Result<Integer> division2 = divide(10, 0);
        handleResult(division2, "10 ÷ 0");

        Result<String> user1 = fetchUser(42);
        handleResult(user1, "Fetch user 42");

        Result<String> user2 = fetchUser(-1);
        handleResult(user2, "Fetch user -1");
    }
}
