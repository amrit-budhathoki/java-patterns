// Title: State Pattern - Document Workflow

// Demonstrates the State pattern where a Document transitions through different states
// (Draft, Review, Published) with different behavior in each state.
// Use this when an object's behavior changes based on its internal state.

public class DocumentWorkflowExample {
    public static void main(String[] args) {
        Document doc = new Document("My Article");
        
        System.out.println("Initial state: " + doc.getState());
        doc.publish();
        System.out.println("After publish: " + doc.getState());
        
        doc.sendForReview();
        System.out.println("After review: " + doc.getState());
        
        doc.publish();
        System.out.println("After publish again: " + doc.getState());
        
        doc.edit();
        System.out.println("After edit: " + doc.getState());
    }
}

interface DocumentState {
    void publish(Document doc);
    void edit(Document doc);
    void sendForReview(Document doc);
    String getName();
}

class DraftState implements DocumentState {
    public void publish(Document doc) {
        System.out.println("Cannot publish from Draft. Send for review first.");
    }
    public void edit(Document doc) {
        System.out.println("Editing in Draft state.");
    }
    public void sendForReview(Document doc) {
        System.out.println("Sending to Review...");
        doc.setState(new ReviewState());
    }
    public String getName() { return "Draft"; }
}

class ReviewState implements DocumentState {
    public void publish(Document doc) {
        System.out.println("Publishing from Review...");
        doc.setState(new PublishedState());
    }
    public void edit(Document doc) {
        System.out.println("Returning to Draft for edits...");
        doc.setState(new DraftState());
    }
    public void sendForReview(Document doc) {
        System.out.println("Already in Review state.");
    }
    public String getName() { return "Review"; }
}

class PublishedState implements DocumentState {
    public void publish(Document doc) {
        System.out.println("Already published.");
    }
    public void edit(Document doc) {
        System.out.println("Cannot edit published document.");
    }
    public void sendForReview(Document doc) {
        System.out.println("Cannot send published document for review.");
    }
    public String getName() { return "Published"; }
}

class Document {
    private String title;
    private DocumentState state;
    
    public Document(String title) {
        this.title = title;
        this.state = new DraftState();
    }
    
    public void publish() { state.publish(this); }
    public void edit() { state.edit(this); }
    public void sendForReview() { state.sendForReview(this); }
    public void setState(DocumentState state) { this.state = state; }
    public String getState() { return state.getName(); }
}
