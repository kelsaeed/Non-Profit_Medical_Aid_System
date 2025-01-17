
class CancelState implements AppointmentState {
    private Appointment appointment;

    public CancelState(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void schedule() {
        appointment.setState(new ScheduledState(appointment));
        appointment.notifyStateChange("Appointment rescheduled after cancellation.");
    }

    @Override
    public void cancel() {
        appointment.notifyStateChange("Appointment is already canceled.");
    }

    @Override
    public void reschedule() {
        appointment.setState(new RescheduledState(appointment));
        appointment.notifyStateChange("Appointment rescheduled.");
    }

    @Override
    public void confirm() {
        appointment.notifyStateChange("Cannot confirm a canceled appointment. Please reschedule.");
    }

    @Override
    public void markAsPaid() {
        appointment.notifyStateChange("Cannot mark a canceled appointment as paid.");
    }
}