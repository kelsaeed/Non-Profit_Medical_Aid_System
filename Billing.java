// Billing.java
import java.util.ArrayList;
import java.util.List;

public class Billing {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void updateBillingStatus() {
        // Update billing status logic
        notifyObservers("Billing status updated.");
    }

    public void updateAmount(double amount) {
        // Update billing amount logic
        notifyObservers("Billing amount updated to: " + amount);
    }
}
