
class ScheduledState implements AppointmentState {
    private Appointment appointment;

    public ScheduledState(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void schedule() {
        appointment.notifyStateChange("Appointment is already scheduled.");
    }

    @Override
    public void cancel() {
        appointment.setState(new CancelState(appointment));
        appointment.notifyStateChange("Appointment canceled.");
    }

    @Override
    public void reschedule() {
        appointment.setState(new RescheduledState(appointment));
        appointment.notifyStateChange("Appointment rescheduled.");
    }

    @Override
    public void confirm() {
        appointment.notifyStateChange("Cannot confirm a scheduled appointment. Please reschedule or mark it as paid.");
    }

    @Override
    public void markAsPaid() {
        appointment.setState(new PaidState(appointment));
        appointment.notifyStateChange("Appointment marked as paid.");
    }
}
