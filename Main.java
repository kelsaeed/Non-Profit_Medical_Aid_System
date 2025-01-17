import java.util.Scanner;

public class Main {
    public static void main(String[] args) {







         Facade facade = new Facade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Update Billing Type");
            System.out.println("3. Add Service to Patient");
            System.out.println("4. View Patient Details");
            System.out.println("5. Add Doctor");
            System.out.println("6. Assign Doctor to Patient");
            System.out.println("7. Schedule Appointment");
            System.out.println("8. Add Service to All Patients");
            System.out.println("9. Show Patient Record");
            System.out.println("10. Add Medication to Patient");  // New option for adding medication
            System.out.println("11. View Medications for Patient");  // New option for viewing medications
            System.out.println("12. Donate ");
            System.out.println("13. View Donations");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== Add Patient ===");
                    facade.addPatient();
                }
                case 2 -> {
                    System.out.println("\n=== Update Billing Type ===");
                    facade.updateBillingType();
                }
                case 3 -> {
                    System.out.println("\n=== Add Service to Patient ===");
                    facade.addServiceToPatient();
                }
                case 4 -> {
                    System.out.println("\n=== View Patient Details ===");
                    facade.viewPatientDetails();
                }
                case 5 -> {
                    System.out.println("\n=== Add Doctor ===");
                    facade.addDoctor();
                }
                case 6 -> {
                    System.out.println("\n=== Assign Doctor to Patient ===");
                    facade.assignDoctorToPatient();
                }
                case 7 -> {
                    System.out.println("\n=== Schedule Appointment ===");
                    facade.scheduleAppointment();
                }
                case 8 -> {
                    System.out.println("\n=== Add Service to All Patients ===");
                    facade.addServiceToAllPatients();
                }
                case 9 -> {
                    System.out.println("\n=== Show Patient Record ===");
                    facade.ShowPatientRecord();
                }
                case 10 -> {
                    System.out.println("\n=== Add Medication to Patient ===");
                    facade.addMedicationToPatient();  // New case for adding medication
                }
                case 11 -> {
                    System.out.println("\n=== View Medications for Patient ===");
                    facade.viewMedicationsForPatient();  // New case for viewing medications
                }
                case 12 -> {
                    System.out.println("\n=== Donate ===");
                    facade.donate();
                }

                case 13 -> {
                    System.out.println("\n=== View Donations ===");
                    facade.viewDonations();
                }

                case 14 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
