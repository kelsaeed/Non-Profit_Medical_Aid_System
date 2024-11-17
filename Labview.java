import java.util.Scanner;

public class LabView {

    private LabController controller;

    // Constructor to initialize the controller
    public LabView(LabController controller) {
        this.controller = controller;
    }

    // Method to display the main menu for the lab view
    public void displayMenu() {
        System.out.println("Welcome to the Lab Test Management");
        System.out.println("1. Add a new Lab Test");
        System.out.println("2. Conduct a Lab Test");
        System.out.println("3. Exit");
        System.out.print("Please choose an option: ");
    }

    // Method to add a new test
    public void addTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Test ID: ");
        String testID = scanner.nextLine();
        System.out.print("Enter Test Name: ");
        String testName = scanner.nextLine();

        // Create a new LabTest object
        LabTest newTest = new LabTest(testID, testName);
        
        // Call controller to add the test
        controller.addTest(newTest);
        System.out.println("Test added successfully!");
    }

    // Method to conduct a test
    public void conductTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Lab Test ID: ");
        String labTestID = scanner.nextLine();
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        // Call controller to conduct the test and get the result
        String result = controller.conductTest(labTestID, patientID);
        System.out.println(result);
    }

    // Method to handle user interactions
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Display the menu to the user
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Add a new test
                    addTest();
                    break;
                case 2:
                    // Conduct a test
                    conductTest();
                    break;
                case 3:
                    // Exit the program
                    running = false;
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
