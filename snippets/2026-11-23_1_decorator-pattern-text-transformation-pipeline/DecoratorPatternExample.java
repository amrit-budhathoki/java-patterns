// Title: Decorator Pattern - Text Transformation Pipeline

// The Decorator pattern allows you to add behavior to objects dynamically by wrapping them.
// Use it when you need flexible combinations of features without creating many subclasses.
// Example: stacking text transformations (uppercase, reverse, repeat) independently.

interface TextTransformer {
    String transform(String text);
}

class PlainText implements TextTransformer {
    private final String text;
    
    PlainText(String text) {
        this.text = text;
    }
    
    public String transform(String text) {
        return text;
    }
}

class UppercaseDecorator implements TextTransformer {
    private final TextTransformer wrapped;
    
    UppercaseDecorator(TextTransformer wrapped) {
        this.wrapped = wrapped;
    }
    
    public String transform(String text) {
        return wrapped.transform(text).toUpperCase();
    }
}

class ReverseDecorator implements TextTransformer {
    private final TextTransformer wrapped;
    
    ReverseDecorator(TextTransformer wrapped) {
        this.wrapped = wrapped;
    }
    
    public String transform(String text) {
        return new StringBuilder(wrapped.transform(text)).reverse().toString();
    }
}

class RepeatDecorator implements TextTransformer {
    private final TextTransformer wrapped;
    private final int times;
    
    RepeatDecorator(TextTransformer wrapped, int times) {
        this.wrapped = wrapped;
        this.times = times;
    }
    
    public String transform(String text) {
        String transformed = wrapped.transform(text);
        return transformed.repeat(times);
    }
}

public class DecoratorPatternExample {
    public static void main(String[] args) {
        String original = "hello";
        
        TextTransformer plain = new PlainText(original);
        System.out.println("Plain: " + plain.transform(original));
        
        TextTransformer uppercase = new UppercaseDecorator(plain);
        System.out.println("Uppercase: " + uppercase.transform(original));
        
        TextTransformer reversed = new ReverseDecorator(uppercase);
        System.out.println("Uppercase + Reverse: " + reversed.transform(original));
        
        TextTransformer repeated = new RepeatDecorator(reversed, 2);
        System.out.println("Uppercase + Reverse + Repeat(2): " + repeated.transform(original));
    }
}
