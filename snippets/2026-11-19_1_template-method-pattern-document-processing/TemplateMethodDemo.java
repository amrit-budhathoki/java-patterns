// Title: Template Method Pattern - Document Processing

// The Template Method pattern defines the skeleton of an algorithm in a base class
// and lets subclasses override specific steps without changing the algorithm's structure.
// Use it when multiple classes share similar workflows with variations in specific steps.

public class TemplateMethodDemo {
    public static void main(String[] args) {
        Document pdfDoc = new PdfDocument();
        pdfDoc.process();
        
        System.out.println();
        
        Document csvDoc = new CsvDocument();
        csvDoc.process();
    }
}

abstract class Document {
    public final void process() {
        load();
        validate();
        transform();
        save();
    }
    
    protected abstract void load();
    protected abstract void validate();
    protected abstract void transform();
    protected abstract void save();
}

class PdfDocument extends Document {
    @Override
    protected void load() {
        System.out.println("Loading PDF file from disk...");
    }
    
    @Override
    protected void validate() {
        System.out.println("Validating PDF structure and compression...");
    }
    
    @Override
    protected void transform() {
        System.out.println("Converting PDF to high-resolution format...");
    }
    
    @Override
    protected void save() {
        System.out.println("Saving processed PDF to archive...");
    }
}

class CsvDocument extends Document {
    @Override
    protected void load() {
        System.out.println("Loading CSV file from database...");
    }
    
    @Override
    protected void validate() {
        System.out.println("Validating CSV headers and data types...");
    }
    
    @Override
    protected void transform() {
        System.out.println("Parsing CSV rows and applying filters...");
    }
    
    @Override
    protected void save() {
        System.out.println("Saving processed CSV to data warehouse...");
    }
}
