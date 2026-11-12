// Title: Command Pattern - Undo/Redo Operations

// The Command pattern encapsulates requests as objects, allowing you to queue, log,
// and undo operations. This example demonstrates a simple text editor with undo/redo
// capabilities using commands. Use this when you need to decouple requesters from
// executors, implement transaction-like behavior, or provide undo/redo functionality.

public class CommandPatternDemo {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();
        
        // Execute commands
        Command insertHello = new InsertCommand(editor, "Hello");
        Command insertSpace = new InsertCommand(editor, " ");
        Command insertWorld = new InsertCommand(editor, "World");
        
        history.execute(insertHello);
        history.execute(insertSpace);
        history.execute(insertWorld);
        System.out.println("After insertions: " + editor.getText());
        
        // Undo operations
        history.undo();
        System.out.println("After undo 1: " + editor.getText());
        
        history.undo();
        System.out.println("After undo 2: " + editor.getText());
        
        // Redo operations
        history.redo();
        System.out.println("After redo 1: " + editor.getText());
        
        history.redo();
        System.out.println("After redo 2: " + editor.getText());
    }
}

interface Command {
    void execute();
    void undo();
}

class TextEditor {
    private StringBuilder text = new StringBuilder();
    
    public void insert(String content) {
        text.append(content);
    }
    
    public void delete(int length) {
        if (length > 0 && length <= text.length()) {
            text.delete(text.length() - length, text.length());
        }
    }
    
    public String getText() {
        return text.toString();
    }
}

class InsertCommand implements Command {
    private TextEditor editor;
    private String content;
    
    public InsertCommand(TextEditor editor, String content) {
        this.editor = editor;
        this.content = content;
    }
    
    @Override
    public void execute() {
        editor.insert(content);
    }
    
    @Override
    public void undo() {
        editor.delete(content.length());
    }
}

class CommandHistory {
    private java.util.Stack<Command> undoStack = new java.util.Stack<>();
    private java.util.Stack<Command> redoStack = new java.util.Stack<>();
    
    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
