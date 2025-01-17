//import java.util.Scanner;
//
//public class MedicalAidView {
//
//    private MedicalAidController controller;
//
//    // Constructor to initialize the MedicalAidController
//    public MedicalAidView(MedicalAidController controller) {
//        this.controller = controller;
//    }
//
//    // Method to display the main menu for the user
//    public void displayMenu() {
//        System.out.println("Welcome to the Medical Aid Program");
//        System.out.println("1. Enroll in a Medical Aid Program");
//        System.out.println("2. View Medical Aid Program Details");
//        System.out.println("3. Exit");
//        System.out.print("Please choose an option: ");
//    }
//
//    // Method to enroll the patient
//    public void enrollPatient() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter Patient ID to enroll: ");
//        String patientID = scanner.nextLine();
//
//        // Call controller to enroll the patient
//        boolean success = controller.enrollPatient(patientID);
//        if (success) {
//            System.out.println("Patient enrolled successfully in the medical aid program!");
//        } else {
//            System.out.println("Failed to enroll patient. Please check the Patient ID and try again.");
//        }
//    }
//
//    // Method to display the medical aid program details
//    public void displayMedicalAidDetails() {
//        String details = controller.getMedicalAidDetails();
//        System.out.println("Medical Aid Program Details: " + details);
//    }
//
//    // Method to handle user interactions
//    public void start() {
//        Scanner scanner = new Scanner(System.in);
//        boolean running = true;
//
//        while (running) {
//            // Display the menu to the user
//            displayMenu();
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//
//            switch (choice) {
//                case 1:
//                    // Enroll a patient
//                    enrollPatient();
//                    break;
//                case 2:
//                    // View Medical Aid Program details
//                    displayMedicalAidDetails();
//                    break;
//                case 3:
//                    // Exit the program
//                    running = false;
//                    System.out.println("Exiting... Thank you!");
//                    break;
//                default:
//                    System.out.println("Invalid choice, please try again.");
//            }
//        }
//    }
//}
