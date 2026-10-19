// Title: Observer Pattern - Stock Price Monitor

// The Observer pattern lets multiple objects (observers) react to state changes
// in another object (subject). Use it when you need loose coupling between objects
// that need to stay synchronized, like UI updates, event notifications, or alerts.

import java.util.*;

interface PriceObserver {
    void priceChanged(String stock, double newPrice);
}

class Stock {
    private final String symbol;
    private double price;
    private final List<PriceObserver> observers = new ArrayList<>();

    Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }

    void subscribe(PriceObserver observer) {
        observers.add(observer);
    }

    void unsubscribe(PriceObserver observer) {
        observers.remove(observer);
    }

    void setPrice(double newPrice) {
        if (this.price != newPrice) {
            this.price = newPrice;
            notifyObservers();
        }
    }

    private void notifyObservers() {
        for (PriceObserver observer : observers) {
            observer.priceChanged(symbol, price);
        }
    }
}

class PortfolioTracker implements PriceObserver {
    private final String name;

    PortfolioTracker(String name) {
        this.name = name;
    }

    @Override
    public void priceChanged(String stock, double newPrice) {
        System.out.printf("[%s] Alert: %s updated to $%.2f%n", name, stock, newPrice);
    }
}

class TradingBot implements PriceObserver {
    @Override
    public void priceChanged(String stock, double newPrice) {
        if (newPrice > 150) {
            System.out.printf("[TradingBot] SELL signal for %s at $%.2f%n", stock, newPrice);
        } else if (newPrice < 100) {
            System.out.printf("[TradingBot] BUY signal for %s at $%.2f%n", stock, newPrice);
        }
    }
}

public class ObserverPatternDemo {
    public static void main(String[] args) {
        Stock apple = new Stock("AAPL", 120.0);

        PortfolioTracker tracker1 = new PortfolioTracker("User1");
        PortfolioTracker tracker2 = new PortfolioTracker("User2");
        TradingBot bot = new TradingBot();

        apple.subscribe(tracker1);
        apple.subscribe(tracker2);
        apple.subscribe(bot);

        System.out.println("=== Stock Price Changes ===");
        apple.setPrice(125.0);
        apple.setPrice(98.5);
        apple.setPrice(155.0);

        apple.unsubscribe(tracker1);
        System.out.println("\n=== After unsubscribing User1 ===");
        apple.setPrice(120.0);
    }
}
