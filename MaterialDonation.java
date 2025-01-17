import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class MaterialDonation extends Donation {
    private Map<String, Integer> donations = new HashMap<>();
    private String donorName;  // Assuming you want to capture the donor's name
    private Connection connection;


    public MaterialDonation() {

    }

    @Override
    public void collectDonationDetails() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");

            while (true) {
                System.out.println("Are you donating:");
                System.out.println("1. Clothes");
                System.out.println("2. Medical Equipment");
                System.out.println("3. Other");
                System.out.print("Enter your choice (1/2/3): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Clothes
                        while (true) {
                            System.out.println("What type of clothes are you donating?");
                            System.out.println("P: Pants | J: Jackets | T: T-Shirts");
                            System.out.print("Enter your choice (P/J/T): ");
                            char clothType = scanner.next().charAt(0);
                            scanner.nextLine(); // Consume newline

                            System.out.print("Enter the number of pieces: ");
                            int count = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            String clothKey = switch (clothType) {
                                case 'P' -> "Pants";
                                case 'J' -> "Jackets";
                                case 'T' -> "T-Shirts";
                                default -> "Unknown";
                            };
                            donations.put(clothKey, donations.getOrDefault(clothKey, 0) + count);

                            System.out.print("Anything else? (Y/N): ");
                            char more = scanner.next().charAt(0);
                            scanner.nextLine(); // Consume newline
                            if (more == 'N' || more == 'n') break;
                        }
                        break;

                    case 2: // Medical Equipment
                        System.out.print("Enter the type of medical equipment: ");
                        String equipment = scanner.nextLine();
                        System.out.print("Enter the quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        donations.put(equipment, donations.getOrDefault(equipment, 0) + quantity);
                        break;

                    case 3: // Other
                        System.out.print("What are you donating? ");
                        String otherDonation = scanner.nextLine();
                        donations.put(otherDonation, 1);
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

                System.out.print("Do you want to add more donations? (Y/N): ");
                char moreDonations = scanner.next().charAt(0);
                scanner.nextLine(); // Consume newline
                if (moreDonations == 'N' || moreDonations == 'n') break;
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    @Override
    public void validateDonation() {
        // Display the collected donations
        System.out.println("Donations: ");
        donations.forEach((item, quantity) -> System.out.println("Item: " + item + ", Quantity: " + quantity));

        // Ask for confirmation to insert the donations into the database
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you confirm this donation? (Y/N): ");
        char confirm = scanner.next().charAt(0);

        if (confirm == 'Y' || confirm == 'y') {
            try {
                // Re-establish a connection to the database
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");

                // Loop through donations and insert them into the database
                for (Map.Entry<String, Integer> entry : donations.entrySet()) {
                    String item = entry.getKey();
                    int quantity = entry.getValue();

                    String insertQuery = "INSERT INTO Donation (DonorName, DonationType, DonationDetails) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                        stmt.setString(1, donorName);
                        stmt.setString(2, "Material");  // Since it's a material donation
                        stmt.setString(3, item + ": Quantity " + quantity);
                        stmt.executeUpdate();
                    }
                }

                System.out.println("Donation confirmed and recorded!");
            } catch (SQLException e) {
                System.err.println("Database error during insertion: " + e.getMessage());
            } finally {
                try {
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing database connection: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Donation canceled.");
        }
    }
}
