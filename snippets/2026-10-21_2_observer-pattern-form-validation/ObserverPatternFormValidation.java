// Title: Observer Pattern - Form Validation

// Demonstrates the Observer pattern where a form notifies multiple validators
// when field values change. Use this when you need loose coupling between a
// subject and multiple observers that react to state changes independently.

import java.util.*;

interface FieldObserver {
    void onFieldChanged(String fieldName, String value);
}

class FormField {
    private final String name;
    private String value = "";
    private final List<FieldObserver> observers = new ArrayList<>();
    
    FormField(String name) {
        this.name = name;
    }
    
    void subscribe(FieldObserver observer) {
        observers.add(observer);
    }
    
    void setValue(String value) {
        this.value = value;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (FieldObserver observer : observers) {
            observer.onFieldChanged(name, value);
        }
    }
    
    String getValue() {
        return value;
    }
}

class LengthValidator implements FieldObserver {
    private final int minLength;
    
    LengthValidator(int minLength) {
        this.minLength = minLength;
    }
    
    @Override
    public void onFieldChanged(String fieldName, String value) {
        if (value.length() < minLength) {
            System.out.println("❌ " + fieldName + ": too short (min " + minLength + ")");
        } else {
            System.out.println("✓ " + fieldName + ": length valid");
        }
    }
}

class PatternValidator implements FieldObserver {
    private final String pattern;
    
    PatternValidator(String pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public void onFieldChanged(String fieldName, String value) {
        if (value.matches(pattern)) {
            System.out.println("✓ " + fieldName + ": format valid");
        } else {
            System.out.println("❌ " + fieldName + ": format invalid");
        }
    }
}

public class ObserverPatternFormValidation {
    public static void main(String[] args) {
        FormField email = new FormField("Email");
        email.subscribe(new LengthValidator(5));
        email.subscribe(new PatternValidator(".*@.*\\..*"));
        
        System.out.println("--- Testing email field ---");
        email.setValue("ab");
        System.out.println();
        
        email.setValue("user@example.com");
        System.out.println();
        
        FormField password = new FormField("Password");
        password.subscribe(new LengthValidator(8));
        password.subscribe(new PatternValidator(".*[A-Z].*"));
        
        System.out.println("--- Testing password field ---");
        password.setValue("weak");
        System.out.println();
        
        password.setValue("SecurePass123");
    }
}
