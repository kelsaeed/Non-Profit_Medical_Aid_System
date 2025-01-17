import java.util.Scanner;

public class LabView {
    private LabController controller;

    public LabView(LabController controller) {
        this.controller = controller;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Lab Test Management ===");
            System.out.println("1. Add a Lab Test");
            System.out.println("2. Conduct a Test");
            System.out.println("3. View All Lab Tests");
            System.out.println("4. Import Lab Tests (External)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Test ID: ");
                    String testID = scanner.nextLine();
                    System.out.print("Enter Test Name: ");
                    String testName = scanner.nextLine();
                    controller.addLabTest(new LabTest(testID, testName));
                }
                case 2 -> {
                    System.out.print("Enter Test ID to conduct: ");
                    String testID = scanner.nextLine();
                    controller.conductTest(testID);
                }
                case 3 -> controller.getLabTests().forEach(System.out::println);
                case 4 -> {
                    // Example: Import from a CSV source using the adapter
                    ExternalCSVLabSource csvSource = new ExternalCSVLabSource();
                    LabTestAdapter csvAdapter = new CSVLabTestAdapter(csvSource);
                    controller.importLabTests(csvAdapter);
                    System.out.println("Imported tests from external CSV source.");
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
