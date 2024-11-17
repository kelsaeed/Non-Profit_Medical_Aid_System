import java.util.List;

public class MedicationView {

    // Method to display the medication details, including side effects
    public void displayMedicationDetails(Medication medication) {
        System.out.println("Medication ID: " + medication.getMedicationID());
        System.out.println("Name: " + medication.getName());
        System.out.println("Dosage: " + medication.getDosage());
        System.out.println("Frequency: " + medication.getFrequency());
        
        // Call viewSideEffects method to display side effects if available
        List<String> sideEffects = medication.viewSideEffects();
        if (sideEffects.isEmpty()) {
            System.out.println("No known side effects.");
        } else {
            System.out.println("Side Effects:");
            for (String sideEffect : sideEffects) {
                System.out.println("- " + sideEffect);
            }
        }
    }
}
