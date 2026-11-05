// Title: Decorator Pattern - Coffee Shop Order

// Demonstrates the Decorator pattern by dynamically adding features (milk, sugar, etc.)
// to a base coffee order. Use this when you need to add responsibilities to objects
// at runtime without modifying their structure or creating many subclasses.

public class CoffeeShopDecorator {
    public static void main(String[] args) {
        Coffee order = new SimpleCoffee();
        System.out.println(order.getDescription() + " - $" + order.getCost());

        order = new MilkDecorator(order);
        System.out.println(order.getDescription() + " - $" + order.getCost());

        order = new SugarDecorator(order);
        System.out.println(order.getDescription() + " - $" + order.getCost());

        order = new CaramelDecorator(order);
        System.out.println(order.getDescription() + " - $" + order.getCost());

        System.out.println("\nAnother order:");
        Coffee espresso = new EspressoCoffee();
        espresso = new MilkDecorator(espresso);
        espresso = new MilkDecorator(espresso);
        System.out.println(espresso.getDescription() + " - $" + espresso.getCost());
    }
}

interface Coffee {
    String getDescription();
    double getCost();
}

class SimpleCoffee implements Coffee {
    public String getDescription() {
        return "Coffee";
    }

    public double getCost() {
        return 2.00;
    }
}

class EspressoCoffee implements Coffee {
    public String getDescription() {
        return "Espresso";
    }

    public double getCost() {
        return 2.50;
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee wrappedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.wrappedCoffee = coffee;
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    public String getDescription() {
        return wrappedCoffee.getDescription() + ", Milk";
    }

    public double getCost() {
        return wrappedCoffee.getCost() + 0.50;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    public String getDescription() {
        return wrappedCoffee.getDescription() + ", Sugar";
    }

    public double getCost() {
        return wrappedCoffee.getCost() + 0.25;
    }
}

class CaramelDecorator extends CoffeeDecorator {
    public CaramelDecorator(Coffee coffee) {
        super(coffee);
    }

    public String getDescription() {
        return wrappedCoffee.getDescription() + ", Caramel";
    }

    public double getCost() {
        return wrappedCoffee.getCost() + 0.75;
    }
}
