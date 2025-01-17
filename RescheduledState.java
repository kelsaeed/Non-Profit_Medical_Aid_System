
class RescheduledState implements AppointmentState {
    private Appointment appointment;

    public RescheduledState(Appointment appointment) {
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
        appointment.notifyStateChange("Appointment is already rescheduled.");
    }

    @Override
    public void confirm() {
        appointment.setState(new ScheduledState(appointment));
        appointment.notifyStateChange("Appointment confirmed and scheduled.");
    }

    @Override
    public void markAsPaid() {
        appointment.notifyStateChange("Cannot mark a rescheduled appointment as paid. Please confirm it first.");
    }
}
