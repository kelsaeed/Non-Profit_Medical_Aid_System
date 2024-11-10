// Main.java
public class Main {
    public static void main(String[] args) {
        // Observers for Appointment
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Nurse nurse = new Nurse();

        Appointment appointment = new Appointment();
        appointment.addObserver(doctor);
        appointment.addObserver(patient);
        appointment.addObserver(nurse);

        // Appointment notifications
        appointment.scheduleAppointment();
        appointment.cancelAppointment();
        appointment.rescheduleAppointment();

        // Observers for Billing
        InsuranceBilling insuranceBilling = new InsuranceBilling();
        CharityBilling charityBilling = new CharityBilling();

        Billing billing = new Billing();
        billing.addObserver(patient);
        billing.addObserver(insuranceBilling);
        billing.addObserver(charityBilling);

        // Billing notifications
        billing.updateBillingStatus();
        billing.updateAmount(500.00);
    }
}
