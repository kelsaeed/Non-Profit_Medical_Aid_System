import java.util.List;

public class HospitalController {
    private static HospitalController instance = null;

    private Hospital model; // Reference to the Hospital model

    // Private constructor to prevent instantiation from outside
    private HospitalController(Hospital model) {
        this.model = model;
    }

    // Public static method to provide access to the single instance of HospitalController
    public static HospitalController getInstance(Hospital model) {
        if (instance == null) {
            instance = new HospitalController(model);
        }
        return instance;
    }

    // Admit a patient to the hospital (this updates the bed availability and patient records)
    public void admitPatient(Patient patient) {
        // Check if there are available beds
        if (model.getNumberOfBeds() > 0) {
            // Add the patient to the hospital records
            model.getPatientRecords().add(new PatientRecord(patient));
            // Decrease the available bed count
            model.setNumberOfBeds(model.getNumberOfBeds() - 1);
        } else {
            // Handle no available beds, but this will be dealt with in the view
        }
    }

    // Discharge a patient (this updates the bed availability and removes the patient record)
    public void dischargePatient(String patientID) {
        PatientRecord patientRecordToRemove = null;

        // Search for the patient record
        for (PatientRecord record : model.getPatientRecords()) {
            if (record.getPatient().getPatientID().equals(patientID)) {
                patientRecordToRemove = record;
                break;
            }
        }

        if (patientRecordToRemove != null) {
            // Remove the patient record from the hospital
            model.getPatientRecords().remove(patientRecordToRemove);
            // Increase the available bed count
            model.setNumberOfBeds(model.getNumberOfBeds() + 1);
        } else {
            // Handle patient not found, but this will be dealt with in the view
        }
    }

    // Update the bed availability based on hospital records (for any changes in patient discharge/admission)
    public int updateBedAvailability() {
        return model.getNumberOfBeds(); // Return the current available beds count
    }

    // Manage the billing for a patient (this will generate a new billing record for the patient)
    public void manageBilling(String patientID, double amount) {
        // Find the patient record using the patient ID
        PatientRecord patientRecord = null;
        for (PatientRecord record : model.getPatientRecords()) {
            if (record.getPatient().getPatientID().equals(patientID)) {
                patientRecord = record;
                break;
            }
        }

        if (patientRecord != null) {
            // Create a new billing record for this patient
            Billing billing = new Billing(patientID, amount);
            model.getBillingRecords().add(billing);
        } else {
            // Handle patient not found for billing, but this will be dealt with in the view
        }
    }
}
