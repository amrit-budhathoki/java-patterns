// Title: Chain of Responsibility - Request Handler

// Chain of Responsibility pattern: pass a request along a chain of handlers where
// each handler decides to process it or pass it to the next handler. Use when you
// have multiple objects that might handle a request and you want to avoid coupling
// the sender to the receivers.

public class ChainOfResponsibilityDemo {
    
    static abstract class RequestHandler {
        protected RequestHandler next;
        
        public void setNext(RequestHandler next) {
            this.next = next;
        }
        
        public void handle(Request request) {
            if (canHandle(request)) {
                process(request);
            } else if (next != null) {
                next.handle(request);
            } else {
                System.out.println("No handler found for: " + request.type);
            }
        }
        
        protected abstract boolean canHandle(Request request);
        protected abstract void process(Request request);
    }
    
    static class Request {
        String type;
        String content;
        
        Request(String type, String content) {
            this.type = type;
            this.content = content;
        }
    }
    
    static class InfoHandler extends RequestHandler {
        protected boolean canHandle(Request request) {
            return "INFO".equals(request.type);
        }
        
        protected void process(Request request) {
            System.out.println("[INFO] Processing: " + request.content);
        }
    }
    
    static class WarningHandler extends RequestHandler {
        protected boolean canHandle(Request request) {
            return "WARNING".equals(request.type);
        }
        
        protected void process(Request request) {
            System.out.println("[WARNING] Alert: " + request.content);
        }
    }
    
    static class ErrorHandler extends RequestHandler {
        protected boolean canHandle(Request request) {
            return "ERROR".equals(request.type);
        }
        
        protected void process(Request request) {
            System.out.println("[ERROR] Critical: " + request.content);
        }
    }
    
    public static void main(String[] args) {
        RequestHandler infoHandler = new InfoHandler();
        RequestHandler warningHandler = new WarningHandler();
        RequestHandler errorHandler = new ErrorHandler();
        
        infoHandler.setNext(warningHandler);
        warningHandler.setNext(errorHandler);
        
        infoHandler.handle(new Request("INFO", "Application started"));
        infoHandler.handle(new Request("WARNING", "Memory usage high"));
        infoHandler.handle(new Request("ERROR", "Database connection failed"));
        infoHandler.handle(new Request("DEBUG", "Some debug info"));
    }
}
