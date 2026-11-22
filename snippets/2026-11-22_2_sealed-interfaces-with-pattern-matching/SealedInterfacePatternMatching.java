// Title: Sealed Interfaces with Pattern Matching

// Demonstrates sealed interfaces combined with pattern matching to create type-safe
// hierarchies where only specific implementations are allowed. Use this when you want
// to restrict which types can implement an interface and handle them exhaustively
// with pattern matching, ensuring compile-time safety.

public class SealedInterfacePatternMatching {
    
    sealed interface Response permits SuccessResponse, ErrorResponse, PendingResponse {}
    
    record SuccessResponse(String data, int statusCode) implements Response {}
    
    record ErrorResponse(String message, int errorCode) implements Response {}
    
    record PendingResponse(String taskId, int percentComplete) implements Response {}
    
    static String handleResponse(Response response) {
        return switch (response) {
            case SuccessResponse(String data, int code) -> 
                "✓ Success [%d]: %s".formatted(code, data);
            case ErrorResponse(String msg, int code) -> 
                "✗ Error [%d]: %s".formatted(code, msg);
            case PendingResponse(String id, int percent) -> 
                "⏳ Processing %s: %d%% complete".formatted(id, percent);
        };
    }
    
    public static void main(String[] args) {
        Response[] responses = {
            new SuccessResponse("User data loaded", 200),
            new ErrorResponse("Authentication failed", 401),
            new PendingResponse("job-12345", 75),
            new SuccessResponse("Resource created", 201),
            new ErrorResponse("Not found", 404),
            new PendingResponse("job-67890", 42)
        };
        
        System.out.println("Processing API Responses:");
        System.out.println("------------------------");
        for (Response response : responses) {
            System.out.println(handleResponse(response));
        }
    }
}
