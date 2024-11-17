import java.util.Date;
import java.util.List;

public class TreatmentController {
    private Treatment treatment;

    // Constructor to initialize the Treatment object
    public TreatmentController(Treatment treatment) {
        this.treatment = treatment;
    }

    // Update the details of the treatment
    public void updateTreatmentDetails(String treatmentType, Date startDate, Doctor doctor, List<Medication> medications) {
        treatment.setTreatmentType(treatmentType);
        treatment.setStartDate(startDate);
        treatment.setDoctor(doctor);
        treatment.setMedications(medications);
    }

    // End the treatment by setting the end date
    public void endTreatment() {
        // Set the end date of the treatment
        treatment.setEndDate(new Date());  // Assuming the treatment ends at the current time
    }

    // Getter for treatment details (optional for testing or view)
    public Treatment getTreatmentDetails() {
        return treatment;
    }
}
