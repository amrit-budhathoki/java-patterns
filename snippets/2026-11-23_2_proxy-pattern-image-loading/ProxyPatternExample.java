// Title: Proxy Pattern - Image Loading

// The Proxy pattern provides a surrogate or placeholder for another object to control
// access to it. Use it when you need lazy initialization, access control, logging,
// or caching before delegating to the real object.

public class ProxyPatternExample {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("photo1.jpg");
        Image image2 = new ProxyImage("photo2.jpg");
        
        System.out.println("Creating proxies... (images not loaded yet)");
        System.out.println();
        
        System.out.println("First display of image1:");
        image1.display();
        System.out.println();
        
        System.out.println("Second display of image1 (cached, no reload):");
        image1.display();
        System.out.println();
        
        System.out.println("First display of image2:");
        image2.display();
    }
}

interface Image {
    void display();
}

class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    
    private void loadFromDisk() {
        System.out.println("  Loading image from disk: " + filename);
    }
    
    @Override
    public void display() {
        System.out.println("  Displaying image: " + filename);
    }
}

class ProxyImage implements Image {
    private String filename;
    private RealImage realImage;
    
    public ProxyImage(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
