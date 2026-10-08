// Title: Simple Event Bus

/**
 * A basic event bus implementation demonstrating the Observer pattern.
 * Use this when you want to decouple event producers from consumers,
 * allowing multiple listeners to react to events without tight coupling.
 */

import java.util.*;
import java.util.function.Consumer;

interface Event {
    String getType();
}

record UserLoginEvent(String username, long timestamp) implements Event {
    @Override
    public String getType() {
        return "user.login";
    }
}

record UserLogoutEvent(String username, long timestamp) implements Event {
    @Override
    public String getType() {
        return "user.logout";
    }
}

class EventBus {
    private final Map<String, List<Consumer<Event>>> subscribers = new HashMap<>();

    public void subscribe(String eventType, Consumer<Event> handler) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public void publish(Event event) {
        List<Consumer<Event>> handlers = subscribers.get(event.getType());
        if (handlers != null) {
            handlers.forEach(handler -> handler.accept(event));
        }
    }
}

public class SimpleEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        // Subscribe to login events
        eventBus.subscribe("user.login", event -> {
            UserLoginEvent loginEvent = (UserLoginEvent) event;
            System.out.println("✓ User logged in: " + loginEvent.username());
        });

        // Subscribe to logout events
        eventBus.subscribe("user.logout", event -> {
            UserLogoutEvent logoutEvent = (UserLogoutEvent) event;
            System.out.println("✓ User logged out: " + logoutEvent.username());
        });

        // Another subscriber for login events (audit logging)
        eventBus.subscribe("user.login", event -> {
            UserLoginEvent loginEvent = (UserLoginEvent) event;
            System.out.println("  [AUDIT] Login recorded at " + loginEvent.timestamp());
        });

        // Publish events
        System.out.println("Publishing events:");
        eventBus.publish(new UserLoginEvent("alice", System.currentTimeMillis()));
        eventBus.publish(new UserLoginEvent("bob", System.currentTimeMillis()));
        eventBus.publish(new UserLogoutEvent("alice", System.currentTimeMillis()));
    }
}
