// Title: Observer Pattern - Weather Station

// The Observer pattern allows multiple objects to subscribe to changes in another object.
// Use it when you need loose coupling between objects that need to react to state changes,
// like UI components listening to data model updates or event notification systems.

import java.util.*;

interface WeatherObserver {
    void update(String weather, int temperature);
}

class WeatherStation {
    private String currentWeather;
    private int temperature;
    private final List<WeatherObserver> observers = new ArrayList<>();
    
    public void subscribe(WeatherObserver observer) {
        observers.add(observer);
    }
    
    public void unsubscribe(WeatherObserver observer) {
        observers.remove(observer);
    }
    
    public void setWeather(String weather, int temperature) {
        this.currentWeather = weather;
        this.temperature = temperature;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(currentWeather, temperature);
        }
    }
}

class MobileApp implements WeatherObserver {
    private final String name;
    
    MobileApp(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String weather, int temperature) {
        System.out.println(name + " received: " + weather + " at " + temperature + "°C");
    }
}

class WebDashboard implements WeatherObserver {
    @Override
    public void update(String weather, int temperature) {
        System.out.println("Dashboard updated: Conditions are " + weather + " (" + temperature + "°C)");
    }
}

public class ObserverPatternDemo {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        
        MobileApp app1 = new MobileApp("iOS App");
        MobileApp app2 = new MobileApp("Android App");
        WebDashboard dashboard = new WebDashboard();
        
        station.subscribe(app1);
        station.subscribe(app2);
        station.subscribe(dashboard);
        
        System.out.println("=== Weather Update 1 ===");
        station.setWeather("Sunny", 25);
        
        System.out.println("\n=== Weather Update 2 ===");
        station.unsubscribe(app2);
        station.setWeather("Rainy", 15);
    }
}
