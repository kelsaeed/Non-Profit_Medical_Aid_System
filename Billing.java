// Billing.java
import java.util.ArrayList;
import java.util.List;

public class Billing implements BillingStrategy {

    BillingStrategy b;
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

    @Override
    public void calculateBill() {
        System.out.println("Default Billing applied.");
    };

    public Billing(BillingStrategy b){
        this.b= b;
    };
    public Billing(){

    };

}
