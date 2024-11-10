// Appointment.java
import java.util.ArrayList;
import java.util.List;

public class Appointment {
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

    public void scheduleAppointment() {
        // Schedule logic
        notifyObservers("Appointment scheduled.");
    }

    public void cancelAppointment() {
        // Cancel logic
        notifyObservers("Appointment canceled.");
    }

    public void rescheduleAppointment() {
        // Reschedule logic
        notifyObservers("Appointment rescheduled.");
    }
}
