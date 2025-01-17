import java.util.Date;
import java.util.List;

public class Treatment {
    private String treatmentID;
    private String treatmentType;
    private Date startDate;
    private Date endDate;
    private Doctor doctor;
    private List<Medication> medications;

    // Constructor
    public Treatment(String treatmentID, String treatmentType, Date startDate, Doctor doctor, List<Medication> medications) {
        this.treatmentID = treatmentID;
        this.treatmentType = treatmentType;
        this.startDate = startDate;
        this.doctor = doctor;
        this.medications = medications;
    }

    // Getters and setters for all attributes
    public String getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(String treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
