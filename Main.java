// Main.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // JDBC credentials
        String jdbcURL = "jdbc:mysql://localhost:3306/hospital"; // Replace "hospital" with your database name
        String username = "root"; // Replace with your MySQL username
        String password = "1234"; // Replace with your MySQL password

        Scanner scanner = new Scanner(System.in);

        // Observer pattern for appointments
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Nurse nurse = new Nurse();

        Appointment appointment = new Appointment();
        appointment.addObserver(doctor);
        appointment.addObserver(patient);
        appointment.addObserver(nurse);

        // Observer pattern notifications
        System.out.println("Observer Pattern: Appointment notifications");
        appointment.scheduleAppointment();
        System.out.println("--------------------------------------------------");
        appointment.cancelAppointment();
        System.out.println("--------------------------------------------------");
        appointment.rescheduleAppointment();
        System.out.println("--------------------------------------------------");

        // Observer pattern for billing
        InsuranceBilling insuranceBilling = new InsuranceBilling();
        CharityBilling charityBilling = new CharityBilling();

        Billing billing = new Billing();
        billing.addObserver(patient);
        billing.addObserver(insuranceBilling);
        billing.addObserver(charityBilling);

        // Billing notifications
        System.out.println("Observer Pattern: Billing notifications");
        billing.updateBillingStatus();
        System.out.println("--------------------------------------------------");
        billing.updateAmount(500.00);
        System.out.println("--------------------------------------------------");

        // Strategy pattern
        System.out.println("Strategy Pattern: Billing calculation");
        Billing cashbill = new Billing(new CashBilling());
        Billing insurancebill = new Billing(new InsuranceBilling());
        Billing charitybill = new Billing(new CharityBilling());

        cashbill.b.calculateBill();
        insurancebill.b.calculateBill();
        charitybill.b.calculateBill();
        System.out.println("--------------------------------------------------");

        // Decorator pattern
        System.out.println("Decorator Pattern: Medical aid programs");
        MedicalAidProgram annualCheck = new AnnualCheckUp();
        System.out.println(annualCheck.MedicalType());

        annualCheck = new BloodTest(annualCheck);
        System.out.println(annualCheck.MedicalType());

        annualCheck = new CTScan(annualCheck);
        System.out.println(annualCheck.MedicalType());

        annualCheck = new ECG(annualCheck);
        System.out.println(annualCheck.MedicalType());
        System.out.println("--------------------------------------------------");

        MedicalAidProgram weeklyCheck = new WeeklyCheckup();
        System.out.println(weeklyCheck.MedicalType());

        weeklyCheck = new BloodTest(weeklyCheck);
        System.out.println(weeklyCheck.MedicalType());

        weeklyCheck = new CTScan(weeklyCheck);
        System.out.println(weeklyCheck.MedicalType());

        weeklyCheck = new ECG(weeklyCheck);
        System.out.println(weeklyCheck.MedicalType());
        System.out.println("--------------------------------------------------");

        // Database operations with user input

                // Database operations with user input
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Modify a table");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> {
                        System.out.println("Choose the table to modify:");
                        System.out.println("1. Hospitals");
                        System.out.println("2. Doctors");
                        System.out.println("3. Patients");
                        System.out.print("Enter your choice: ");
                        int tableChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (tableChoice) {
                            case 1 -> modifyHospitals(connection, scanner);
                            case 2 -> modifyDoctors(connection, scanner);
                            case 3 -> modifyPatients(connection, scanner);
                            default -> System.out.println("Invalid choice! Please try again.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Exiting program. Goodbye!");
                        connection.close();
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hospital operations
    public static void modifyHospitals(Connection connection, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Hospital Table Operations ===");
            System.out.println("1. Add a new hospital");
            System.out.println("2. View all hospitals");
            System.out.println("3. Update a hospital");
            System.out.println("4. Delete a hospital");
            System.out.println("5. Go back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter hospital name: ");
                    String name = scanner.nextLine();
                    createHospital(connection, name);
                }
                case 2 -> viewHospitals(connection);
                case 3 -> {
                    System.out.print("Enter the ID of the hospital to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter new hospital name: ");
                    String newName = scanner.nextLine();
                    updateHospital(connection, id, newName);
                }
                case 4 -> {
                    System.out.print("Enter the ID of the hospital to delete: ");
                    int id = scanner.nextInt();
                    deleteHospital(connection, id);
                }
                case 5 -> {
                    return; // Go back to the main menu
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Doctor operations
    public static void modifyDoctors(Connection connection, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Doctor Table Operations ===");
            System.out.println("1. Add a new doctor");
            System.out.println("2. View all doctors");
            System.out.println("3. Update a doctor");
            System.out.println("4. Delete a doctor");
            System.out.println("5. Go back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter doctor first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter doctor last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter doctor specialization: ");
                    String specialization = scanner.nextLine();
                    System.out.print("Enter doctor contact info: ");
                    String contactInfo = scanner.nextLine();
                    System.out.print("Enter hospital ID for this doctor: ");
                    int hospitalID = scanner.nextInt();
                    addDoctor(connection, firstName, lastName, specialization, contactInfo, hospitalID);
                }
                case 2 -> viewDoctors(connection);
                case 3 -> {
                    System.out.print("Enter the ID of the doctor to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter new first name: ");
                    String newFirstName = scanner.nextLine();
                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.nextLine();
                    System.out.print("Enter new specialization: ");
                    String newSpecialization = scanner.nextLine();
                    System.out.print("Enter new contact info: ");
                    String newContactInfo = scanner.nextLine();
                    System.out.print("Enter new hospital ID for this doctor: ");
                    int newHospitalID = scanner.nextInt();
                    updateDoctor(connection, id, newFirstName, newLastName, newSpecialization, newContactInfo, newHospitalID);
                }
                case 4 -> {
                    System.out.print("Enter the ID of the doctor to delete: ");
                    int id = scanner.nextInt();
                    deleteDoctor(connection, id);
                }
                case 5 -> {
                    return; // Go back to the main menu
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Patient operations
    public static void modifyPatients(Connection connection, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Patient Table Operations ===");
            System.out.println("1. Add a new patient");
            System.out.println("2. View all patients");
            System.out.println("3. Update a patient");
            System.out.println("4. Delete a patient");
            System.out.println("5. Go back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter patient first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter patient last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter patient date of birth (yyyy-mm-dd): ");
                    String dateOfBirth = scanner.nextLine();
                    System.out.print("Enter patient contact info: ");
                    String contactInfo = scanner.nextLine();
                    System.out.print("Enter patient address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter patient insurance details: ");
                    String insuranceDetails = scanner.nextLine();
                    addPatient(connection, firstName, lastName, dateOfBirth, contactInfo, address, insuranceDetails);
                }
                case 2 -> viewPatients(connection);
                case 3 -> {
                    System.out.print("Enter the ID of the patient to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter new first name: ");
                    String newFirstName = scanner.nextLine();
                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.nextLine();
                    System.out.print("Enter new date of birth (yyyy-mm-dd): ");
                    String newDateOfBirth = scanner.nextLine();
                    System.out.print("Enter new contact info: ");
                    String newContactInfo = scanner.nextLine();
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    System.out.print("Enter new insurance details: ");
                    String newInsuranceDetails = scanner.nextLine();
                    updatePatient(connection, id, newFirstName, newLastName, newDateOfBirth, newContactInfo, newAddress, newInsuranceDetails);
                }
                case 4 -> {
                    System.out.print("Enter the ID of the patient to delete: ");
                    int id = scanner.nextInt();
                    deletePatient(connection, id);
                }
                case 5 -> {
                    return; // Go back to the main menu
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
            // Hospital CRUD operations
            public static void createHospital(Connection connection, String name) {
                String sql = "INSERT INTO hospitals (HospitalName) VALUES (?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("A new hospital was inserted successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void viewHospitals(Connection connection) {
                String sql = "SELECT * FROM hospitals";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    System.out.println("\n=== Hospital List ===");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("HospitalID");
                        String name = resultSet.getString("HospitalName");
                        System.out.println("ID: " + id + ", Name: " + name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void updateHospital(Connection connection, int id, String newName) {
                String sql = "UPDATE hospitals SET HospitalName = ? WHERE HospitalID = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, newName);
                    preparedStatement.setInt(2, id);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Hospital with ID " + id + " was updated successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void deleteHospital(Connection connection, int id) {
                String sql = "DELETE FROM hospitals WHERE HospitalID = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Hospital with ID " + id + " was deleted successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Doctor CRUD operations
            public static void addDoctor(Connection connection, String firstName, String lastName, String specialization, String contactInfo, int hospitalID) {
                String sql = "INSERT INTO doctors (FirstName, LastName, Specialization, ContactInfo, HospitalID) VALUES (?, ?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, specialization);
                    preparedStatement.setString(4, contactInfo);
                    preparedStatement.setInt(5, hospitalID);
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("A new doctor was inserted successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void viewDoctors(Connection connection) {
                String sql = "SELECT * FROM doctors";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    System.out.println("\n=== Doctor List ===");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("DoctorID");
                        String firstName = resultSet.getString("FirstName");
                        String lastName = resultSet.getString("LastName");
                        String specialization = resultSet.getString("Specialization");
                        String contactInfo = resultSet.getString("ContactInfo");
                        System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Specialization: " + specialization + ", Contact Info: " + contactInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void updateDoctor(Connection connection, int id, String firstName, String lastName, String specialization, String contactInfo, int hospitalID) {
                String sql = "UPDATE doctors SET FirstName = ?, LastName = ?, Specialization = ?, ContactInfo = ?, HospitalID = ? WHERE DoctorID = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, specialization);
                    preparedStatement.setString(4, contactInfo);
                    preparedStatement.setInt(5, hospitalID);
                    preparedStatement.setInt(6, id);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Doctor with ID " + id + " was updated successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void deleteDoctor(Connection connection, int id) {
                String sql = "DELETE FROM doctors WHERE DoctorID = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Doctor with ID " + id + " was deleted successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    public static void addPatient(Connection connection, String firstName, String lastName, String dateOfBirth, String contactInfo, String address, String insuranceDetails) {
        String sql = "INSERT INTO patients (FirstName, LastName, DateOfBirth, ContactInfo, Address, InsuranceDetails) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, dateOfBirth);
            preparedStatement.setString(4, contactInfo);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, insuranceDetails);
            preparedStatement.executeUpdate();
            System.out.println("Patient added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewPatients(Connection connection) {
        String sql = "SELECT * FROM patients";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("PatientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String dateOfBirth = resultSet.getString("DateOfBirth");
                String contactInfo = resultSet.getString("ContactInfo");
                String address = resultSet.getString("Address");
                String insuranceDetails = resultSet.getString("InsuranceDetails");
                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Date of Birth: " + dateOfBirth + ", Contact Info: " + contactInfo + ", Address: " + address + ", Insurance: " + insuranceDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePatient(Connection connection, int id, String firstName, String lastName, String dateOfBirth, String contactInfo, String address, String insuranceDetails) {
        String sql = "UPDATE patients SET FirstName = ?, LastName = ?, DateOfBirth = ?, ContactInfo = ?, Address = ?, InsuranceDetails = ? WHERE PatientID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, dateOfBirth);
            preparedStatement.setString(4, contactInfo);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, insuranceDetails);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            System.out.println("Patient updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePatient(Connection connection, int id) {
        String sql = "DELETE FROM patients WHERE PatientID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Patient deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        }


