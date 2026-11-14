// Title: Decorator Pattern - Text Formatting

// Demonstrates the Decorator pattern for dynamically adding formatting
// responsibilities to text. Use this when you need to add features to objects
// without modifying their structure, and want flexible combinations of features.

public class DecoratorPatternTextFormatting {
    public static void main(String[] args) {
        TextComponent text = new PlainText("Hello World");
        System.out.println("Plain: " + text.render());

        text = new BoldDecorator(text);
        System.out.println("Bold: " + text.render());

        text = new ItalicDecorator(text);
        System.out.println("Bold + Italic: " + text.render());

        text = new UnderlineDecorator(text);
        System.out.println("Bold + Italic + Underline: " + text.render());

        TextComponent anotherText = new UnderlineDecorator(new PlainText("Decorator"));
        System.out.println("Just Underline: " + anotherText.render());
    }
}

interface TextComponent {
    String render();
}

class PlainText implements TextComponent {
    private final String content;

    PlainText(String content) {
        this.content = content;
    }

    @Override
    public String render() {
        return content;
    }
}

abstract class FormattingDecorator implements TextComponent {
    protected TextComponent wrappedComponent;

    FormattingDecorator(TextComponent component) {
        this.wrappedComponent = component;
    }

    @Override
    public String render() {
        return wrappedComponent.render();
    }
}

class BoldDecorator extends FormattingDecorator {
    BoldDecorator(TextComponent component) {
        super(component);
    }

    @Override
    public String render() {
        return "**" + wrappedComponent.render() + "**";
    }
}

class ItalicDecorator extends FormattingDecorator {
    ItalicDecorator(TextComponent component) {
        super(component);
    }

    @Override
    public String render() {
        return "_" + wrappedComponent.render() + "_";
    }
}

class UnderlineDecorator extends FormattingDecorator {
    UnderlineDecorator(TextComponent component) {
        super(component);
    }

    @Override
    public String render() {
        return "<u>" + wrappedComponent.render() + "</u>";
    }
}
