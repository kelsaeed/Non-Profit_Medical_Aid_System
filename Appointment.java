
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private List<Observer> observers = new ArrayList<>();
    private AppointmentState state; // Current state

    public Appointment() {
        // Set the initial state to Unscheduled
        this.state = new UnscheduledState(this);
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }

    public AppointmentState getState() {
        return state;
    }

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
        state.schedule();
    }

    public void cancelAppointment() {
        state.cancel();
    }

    public void rescheduleAppointment() {
        state.reschedule();
    }

    public void confirmAppointment() {
        state.confirm();
    }

    public void markAsPaid() {
        state.markAsPaid();
    }

    public void notifyStateChange(String message) {
        notifyObservers(message);
    }

    public String getCurrentStateName() {
        return state.getClass().getSimpleName().replace("State", "");
    }
}
