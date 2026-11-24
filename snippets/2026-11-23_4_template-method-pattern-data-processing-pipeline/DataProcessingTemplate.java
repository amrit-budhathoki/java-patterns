// Title: Template Method Pattern - Data Processing Pipeline

// Template method pattern defines the skeleton of an algorithm in a base class,
// letting subclasses override specific steps without changing the algorithm's structure.
// Use it when you have a process with fixed steps but variable implementations.

public class DataProcessingTemplate {
    public static void main(String[] args) {
        DataProcessor csvProcessor = new CsvProcessor();
        DataProcessor jsonProcessor = new JsonProcessor();

        System.out.println("=== Processing CSV ===");
        csvProcessor.process("users.csv");

        System.out.println("\n=== Processing JSON ===");
        jsonProcessor.process("users.json");
    }
}

abstract class DataProcessor {
    // Template method - defines the algorithm skeleton
    public final void process(String filename) {
        validate(filename);
        parse(filename);
        transform();
        save();
    }

    private void validate(String filename) {
        System.out.println("Validating file: " + filename);
        if (!filename.contains(".")) {
            throw new IllegalArgumentException("Invalid filename");
        }
    }

    // Abstract methods - subclasses must implement
    protected abstract void parse(String filename);

    protected abstract void transform();

    protected abstract void save();
}

class CsvProcessor extends DataProcessor {
    @Override
    protected void parse(String filename) {
        System.out.println("Parsing CSV file with comma delimiter");
    }

    @Override
    protected void transform() {
        System.out.println("Transforming CSV records to objects");
    }

    @Override
    protected void save() {
        System.out.println("Saving to CSV database");
    }
}

class JsonProcessor extends DataProcessor {
    @Override
    protected void parse(String filename) {
        System.out.println("Parsing JSON file with Jackson");
    }

    @Override
    protected void transform() {
        System.out.println("Transforming JSON nodes to objects");
    }

    @Override
    protected void save() {
        System.out.println("Saving to JSON document store");
    }
}
