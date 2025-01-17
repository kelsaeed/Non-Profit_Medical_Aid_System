
class UnscheduledState implements AppointmentState {
    private Appointment appointment;

    public UnscheduledState(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void schedule() {
        appointment.setState(new ScheduledState(appointment));
        appointment.notifyStateChange("Appointment scheduled.");
    }

    @Override
    public void cancel() {
        appointment.setState(new CancelState(appointment));
        appointment.notifyStateChange("Appointment canceled.");
    }

    @Override
    public void reschedule() {
        appointment.notifyStateChange("Cannot reschedule an unscheduled appointment.");
    }

    @Override
    public void confirm() {
        appointment.notifyStateChange("Cannot confirm an unscheduled appointment.");
    }

    @Override
    public void markAsPaid() {
        appointment.notifyStateChange("Cannot mark an unscheduled appointment as paid.");
    }
}
