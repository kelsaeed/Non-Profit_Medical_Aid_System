class PaidState implements AppointmentState {
    private Appointment appointment;

    public PaidState(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void schedule() {
        appointment.notifyStateChange("Cannot schedule a paid appointment.");
    }

    @Override
    public void cancel() {
        appointment.notifyStateChange("Cannot cancel a paid appointment.");
    }

    @Override
    public void reschedule() {
        appointment.notifyStateChange("Cannot reschedule a paid appointment.");
    }

    @Override
    public void confirm() {
        appointment.notifyStateChange("Cannot confirm a paid appointment.");
    }

    @Override
    public void markAsPaid() {
        appointment.notifyStateChange("Appointment is already marked as paid.");
    }
}
