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
        System.out.println("--------------------------------------------------");
        appointment.cancelAppointment();
        System.out.println("--------------------------------------------------");
        appointment.rescheduleAppointment();
        System.out.println("--------------------------------------------------");

        // Observers for Billing
        InsuranceBilling insuranceBilling = new InsuranceBilling();
        CharityBilling charityBilling = new CharityBilling();

        Billing billing = new Billing();
        billing.addObserver(patient);
        billing.addObserver(insuranceBilling);
        billing.addObserver(charityBilling);

        // Billing notifications
        billing.updateBillingStatus();
        System.out.println("--------------------------------------------------");
        billing.updateAmount(500.00);
        System.out.println("--------------------------------------------------");

        // Applying Strategy Pattern
        Billing cashbill = new Billing(new CashBilling());
        Billing insurancebill = new Billing(new InsuranceBilling());
        Billing charitybill = new Billing(new CharityBilling());

        cashbill.b.calculateBill();
        insurancebill.b.calculateBill();
        charitybill.b.calculateBill();

        System.out.println("--------------------------------------------------");

        //Applying Decorator Pattern
        MedicalAidProgram annualCheck = new AnnualCheckUp();
        System.out.println(annualCheck.MedicalType());

        annualCheck = new BloodTest(annualCheck);
        System.out.println(annualCheck.MedicalType());

        annualCheck = new CTScan(annualCheck);
        System.out.println(annualCheck.MedicalType());

        annualCheck = new ECG(annualCheck);
        System.out.println(annualCheck.MedicalType());

        System.out.println("--------------------------------------------------");

        MedicalAidProgram weeklyCheck = new WeeklyCheckup();
        System.out.println(weeklyCheck.MedicalType());

        weeklyCheck = new BloodTest(weeklyCheck);
        System.out.println(weeklyCheck.MedicalType());

        weeklyCheck = new CTScan(weeklyCheck);
        System.out.println(weeklyCheck.MedicalType());

        weeklyCheck = new ECG(weeklyCheck);
        System.out.println(weeklyCheck.MedicalType());


    }
}
