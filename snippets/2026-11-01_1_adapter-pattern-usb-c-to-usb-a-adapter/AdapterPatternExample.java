// Title: Adapter Pattern - USB-C to USB-A Adapter

// The Adapter pattern converts the interface of an existing class into another interface
// clients expect. Use it when you need to integrate incompatible interfaces, such as
// connecting a modern device (USB-C) to legacy hardware (USB-A) without changing either.

interface USBTypeA {
    void connectTypeA();
    String transferData();
}

interface USBTypeC {
    void connectTypeC();
    String sendData();
}

class ModernDevice implements USBTypeC {
    @Override
    public void connectTypeC() {
        System.out.println("Modern device: USB-C connected");
    }

    @Override
    public String sendData() {
        return "High-speed data transfer via USB-C";
    }
}

class LegacyPrinter implements USBTypeA {
    @Override
    public void connectTypeA() {
        System.out.println("Legacy printer: USB-A connected");
    }

    @Override
    public String transferData() {
        return "Slow data transfer via USB-A";
    }
}

class USBCToUSBAAdapter implements USBTypeA {
    private USBTypeC device;

    public USBCToUSBAAdapter(USBTypeC device) {
        this.device = device;
    }

    @Override
    public void connectTypeA() {
        System.out.println("Adapter: Converting USB-C to USB-A");
        device.connectTypeC();
    }

    @Override
    public String transferData() {
        return device.sendData() + " (via adapter)";
    }
}

public class AdapterPatternExample {
    public static void main(String[] args) {
        USBTypeA printer = new LegacyPrinter();
        printer.connectTypeA();
        System.out.println("Printer: " + printer.transferData());

        System.out.println("\n--- Using Adapter ---\n");

        ModernDevice device = new ModernDevice();
        USBTypeA adapter = new USBCToUSBAAdapter(device);
        adapter.connectTypeA();
        System.out.println("Result: " + adapter.transferData());
    }
}
