import java.sql.*;
import java.util.Scanner;

class MoneyDonation extends Donation {
    private double amount;
    private String donationMethod; // To store the donation method (Cash, Visa, or Check)
    private String transactionDetails; // To store Visa or Check details (if applicable)
    private String donorName;  // Assuming you want to capture the donor's name
    private Connection connection;

    public MoneyDonation() {
        this.donorName = donorName;
    }

    @Override
    public void collectDonationDetails() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");

            System.out.println("Are you donating by:");
            System.out.println("1. Cash");
            System.out.println("2. Visa");
            System.out.println("3. Check");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Cash
                    System.out.print("Enter the amount in EGP: ");
                    amount = scanner.nextDouble();
                    donationMethod = "Cash";
                    break;

                case 2: // Visa
                    System.out.print("Enter your Visa number: ");
                    String visaNumber = scanner.nextLine();
                    System.out.print("Enter the amount in EGP: ");
                    amount = scanner.nextDouble();
                    transactionDetails = "Visa Number: " + visaNumber;
                    donationMethod = "Visa";
                    break;

                case 3: // Check
                    System.out.print("Enter the check ID number: ");
                    String checkID = scanner.nextLine();
                    System.out.print("Enter the amount in EGP: ");
                    amount = scanner.nextDouble();
                    transactionDetails = "Check ID: " + checkID;
                    donationMethod = "Check";
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
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
        // Display the donation details
        System.out.println("Donation Method: " + donationMethod);
        System.out.println("Donation Amount: EGP " + amount);
        if (transactionDetails != null) {
            System.out.println("Transaction Details: " + transactionDetails);
        }

        // Ask for confirmation
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you confirm this donation? (Y/N): ");
        char confirm = scanner.next().charAt(0);

        if (confirm == 'Y' || confirm == 'y') {
            try {
                // Re-establish a connection to the database
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");

                // Prepare and execute the insertion of donation details into the database
                String insertQuery = "INSERT INTO Donation (DonorName, DonationType, DonationDetails, Amount) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                    stmt.setString(1, donorName);
                    stmt.setString(2, "Monetary");  // Since it's a monetary donation
                    stmt.setString(3, donationMethod + " - " + (transactionDetails != null ? transactionDetails : ""));
                    stmt.setDouble(4, amount);
                    stmt.executeUpdate();
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
