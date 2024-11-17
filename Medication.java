import java.util.List;

public class Medication {
    private String medicationID;
    private String name;
    private String dosage;
    private String frequency;
    private List<String> sideEffects;

    // Constructor
    public Medication(String medicationID, String name, String dosage, String frequency, List<String> sideEffects) {
        this.medicationID = medicationID;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.sideEffects = sideEffects;
    }

    // Getters and setters
    public String getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(String medicationID) {
        this.medicationID = medicationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<String> getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(List<String> sideEffects) {
        this.sideEffects = sideEffects;
    }

    // Method to return the side effects of the medication
    public List<String> viewSideEffects() {
        return sideEffects;
    }
}
