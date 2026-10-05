// Title: State Pattern - Traffic Light Example

/**
 * Demonstrates the State pattern where an object changes behavior based on its internal state.
 * Use this pattern when an object must alter its behavior when its internal state changes,
 * and you want to avoid conditional logic scattered throughout your code.
 */

interface TrafficLightState {
    void next(TrafficLight light);
    void display();
}

record RedLight() implements TrafficLightState {
    @Override
    public void next(TrafficLight light) {
        light.setState(new GreenLight());
    }

    @Override
    public void display() {
        System.out.println("🔴 RED - Stop!");
    }
}

record GreenLight() implements TrafficLightState {
    @Override
    public void next(TrafficLight light) {
        light.setState(new YellowLight());
    }

    @Override
    public void display() {
        System.out.println("🟢 GREEN - Go!");
    }
}

record YellowLight() implements TrafficLightState {
    @Override
    public void next(TrafficLight light) {
        light.setState(new RedLight());
    }

    @Override
    public void display() {
        System.out.println("🟡 YELLOW - Prepare to stop!");
    }
}

class TrafficLight {
    private TrafficLightState state;

    TrafficLight() {
        this.state = new RedLight();
    }

    void setState(TrafficLightState state) {
        this.state = state;
    }

    void cycle() {
        state.display();
        state.next(this);
    }
}

public class StatePatternExample {
    public static void main(String[] args) {
        TrafficLight light = new TrafficLight();

        System.out.println("Traffic Light State Pattern Demo:");
        System.out.println("--------------------------------");

        for (int i = 0; i < 6; i++) {
            light.cycle();
        }
    }
}
