import java.sql.*;
import java.util.*;

public class Facade {
    private final Connection connection;
    private final List<Observer> observers = new ArrayList<>();
    private final Map<Integer, List<Doctor>> patientDoctorMap = new HashMap<>();
    private PatientRecordProxy proxy;

    public Facade() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
            loadDoctorPatientRelationships(); // Load relationships on initialization
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        PatientRecordService service = new PatientRecordService();
        this.proxy = new PatientRecordProxy(service);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Modified notifyObservers to include patient-specific notification
    public void notifyObservers(int patientId, String message) {
        // Notify all generic observers (not specific to doctors)
        for (Observer observer : observers) {
            observer.update(message);
        }

        // Notify only the doctors assigned to the specific patient
        List<Doctor> assignedDoctors = patientDoctorMap.get(patientId);
        if (assignedDoctors != null) {
            for (Doctor doctor : assignedDoctors) {
                doctor.update(message);
            }
        }
    }
    private void loadDoctorPatientRelationships() {
        String query = "SELECT dp.PatientID, d.ID AS DoctorID, d.Name AS DoctorName " +
                "FROM DoctorPatient dp " +
                "JOIN Doctor d ON dp.DoctorID = d.ID";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int patientId = rs.getInt("PatientID");
                int doctorId = rs.getInt("DoctorID");
                String doctorName = rs.getString("DoctorName");

                // Create doctor instance and add to the map
                Doctor doctor = new Doctor(doctorId, doctorName);
                patientDoctorMap.computeIfAbsent(patientId, k -> new ArrayList<>()).add(doctor);
            }

        } catch (SQLException e) {
            System.err.println("Error loading doctor-patient relationships: " + e.getMessage());
        }
    }


    public void addDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Doctor (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Doctor added successfully with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
        }
    }

    public void assignDoctorToPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO DoctorPatient (DoctorID, PatientID) VALUES (?, ?)")) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, patientId);
            stmt.executeUpdate();

            Doctor doctor = getDoctorById(doctorId);
            if (doctor != null) {
                patientDoctorMap.computeIfAbsent(patientId, k -> new ArrayList<>()).add(doctor);
                System.out.println("Doctor assigned to patient successfully!");
            } else {
                System.out.println("Doctor not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error assigning doctor to patient: " + e.getMessage());
        }
    }

    private Doctor getDoctorById(int doctorId) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Doctor WHERE ID = ?")) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                return new Doctor(doctorId, name);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving doctor: " + e.getMessage());
        }
        return null;
    }

    public void addPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();

        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Patient (Name, BillingType, Services) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, "None");
            stmt.setString(3, "");
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                // Adding the new Patient as an observer with both ID and name
                addObserver(new Patient(id, name));
                System.out.println("Patient added successfully with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
        }
    }
    public void addServiceToAllPatients() {
        PatientDatabase patientDatabase = new PatientDatabase(connection);
        PatientIterator iterator = patientDatabase.createIterator();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a service to add to all patients:");
        System.out.println("1. Blood Test");
        System.out.println("2. CT Scan");
        System.out.println("3. ECG");
        int choice = scanner.nextInt();
        scanner.nextLine();

        MedicalAidProgram program = switch (choice) {
            case 1 -> new BloodTest(new BasicProgram());
            case 2 -> new CTScan(new BasicProgram());
            case 3 -> new ECG(new BasicProgram());
            default -> null;
        };

        if (program == null) {
            System.out.println("Invalid choice!");
            return;
        }

        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Patient SET Services = CONCAT(Services, ?) WHERE ID = ?")) {
                stmt.setString(1, " + " + program.getDetails());
                stmt.setInt(2, patient.getId());
                stmt.executeUpdate();
                System.out.println("Service added for patient: " + patient.getName());
                notifyObservers(patient.getId(), "Service added: " + program.getDetails());
            } catch (SQLException e) {
                System.err.println("Error updating patient services: " + e.getMessage());
            }
        }
    }

    public void updateBillingType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Select billing type:");
        System.out.println("1. Cash");
        System.out.println("2. Insurance");
        System.out.println("3. Charity");
        int choice = scanner.nextInt();

        String billingType;
        switch (choice) {
            case 1 -> billingType = "cash";
            case 2 -> billingType = "insurance";
            case 3 -> billingType = "charity";
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement("UPDATE Patient SET BillingType = ? WHERE ID = ?")) {
            stmt.setString(1, billingType);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            BillingStrategy strategy = BillingStrategyFactory.getBillingStrategy(billingType);
            strategy.calculateBill();
            notifyObservers(id, "Billing type updated to " + billingType + " for patient ID: " + id);
        } catch (SQLException e) {
            System.err.println("Error updating billing type: " + e.getMessage());
        }
    }

    public void addServiceToPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose a service to add:");
        System.out.println("1. Blood Test");
        System.out.println("2. CT Scan");
        System.out.println("3. ECG");
        int choice = scanner.nextInt();

        MedicalAidProgram program = switch (choice) {
            case 1 -> new BloodTest(new BasicProgram());
            case 2 -> new CTScan(new BasicProgram());
            case 3 -> new ECG(new BasicProgram());
            default -> null;
        };

        if (program == null) {
            System.out.println("Invalid choice!");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement("SELECT Services FROM Patient WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String existingServices = rs.getString("Services");
                if (existingServices != null && !existingServices.isEmpty()) {
                    if (!existingServices.contains("Basic Program")) {
                        existingServices = "Basic Program + " + existingServices;
                    }
                    existingServices += " + " + program.getDetails().replace("Basic Program + ", "");
                } else {
                    existingServices = program.getDetails();
                }

                try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE Patient SET Services = ? WHERE ID = ?")) {
                    updateStmt.setString(1, existingServices);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                    System.out.println("Service added successfully: " + program.getDetails());
                    notifyObservers(id, "Service added for patient ID: " + id);
                }
            } else {
                System.out.println("Patient not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding service: " + e.getMessage());
        }
    }

    public void viewPatientDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient ID: ");
        int id = scanner.nextInt();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Patient WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n=== Patient Details ===");
                System.out.println("ID: " + rs.getInt("ID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Billing Type: " + rs.getString("BillingType"));
                System.out.println("Services: " + rs.getString("Services"));
            } else {
                System.out.println("Patient not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving patient details: " + e.getMessage());
        }
    }

    public void scheduleAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor ID: ");
        int doctorID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter patient ID: ");
        int patientID = scanner.nextInt();
        scanner.nextLine();

        // Check if the appointment already exists
        String existingStatus = getExistingAppointmentStatus(doctorID, patientID);
        if (existingStatus != null) {
            System.out.println("Appointment already exists with the status: " + existingStatus);
            System.out.println("Proceed with the following actions:");
            System.out.println("1. Reschedule");
            System.out.println("2. Cancel");
            System.out.println("3. Mark as Paid");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> updateAppointmentStatus(doctorID, patientID, "Rescheduled");
                case 2 -> updateAppointmentStatus(doctorID, patientID, "Canceled");
                case 3 -> updateAppointmentStatus(doctorID, patientID, "Paid");
                default -> System.out.println("Invalid choice!");
            }
            return;
        }

        // If no existing appointment, proceed to create a new one
        System.out.println("Choose the initial status of the appointment:");
        System.out.println("1. Scheduled");
        System.out.println("2. Canceled");
        System.out.println("3. Paid");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String status = switch (choice) {
            case 1 -> "Scheduled";
            case 2 -> "Canceled";
            case 3 -> "Paid";
            default -> null;
        };

        if (status == null) {
            System.out.println("Invalid choice!");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Appointment (doctorID, patientID, Status) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, doctorID);
            stmt.setInt(2, patientID);
            stmt.setString(3, status);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int appointmentID = generatedKeys.getInt(1);
                    System.out.println("Appointment scheduled successfully with ID: " + appointmentID);
                    notifyObservers(patientID, "New appointment created: ID " + appointmentID + ", Status: " + status);
                } else {
                    System.out.println("Failed to retrieve appointment ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    private String getExistingAppointmentStatus(int doctorID, int patientID) {
        String query = "SELECT Status FROM Appointment WHERE doctorID = ? AND patientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, doctorID);
            stmt.setInt(2, patientID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Status");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking existing appointment: " + e.getMessage());
        }
        return null;
    }
    private void updateAppointmentStatus(int doctorID, int patientID, String newStatus) {
        String query = "UPDATE Appointment SET Status = ? WHERE doctorID = ? AND patientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, doctorID);
            stmt.setInt(3, patientID);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Appointment status updated to: " + newStatus);
                notifyObservers(patientID, "Appointment status updated to " + newStatus + " for doctor ID: " + doctorID);
            } else {
                System.out.println("Failed to update appointment status.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
    }
    public void addMedicationToPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter medication name: ");
        String name = scanner.nextLine();

        System.out.print("Enter dosage: ");
        String dosage = scanner.nextLine();

        System.out.print("Enter frequency: ");
        String frequency = scanner.nextLine();

        System.out.print("Enter side effects (comma-separated): ");
        String sideEffectsInput = scanner.nextLine();
        List<String> sideEffects = Arrays.asList(sideEffectsInput.split(","));

        // Insert the medication directly into the database
        String query = "INSERT INTO Medication (PatientID, Name, Dosage, Frequency, SideEffects) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            stmt.setString(2, name);
            stmt.setString(3, dosage);
            stmt.setString(4, frequency);
            stmt.setString(5, String.join(", ", sideEffects));
            stmt.executeUpdate();
            System.out.println("Medication added successfully for patient ID: " + patientId);
        } catch (SQLException e) {
            System.err.println("Error adding medication: " + e.getMessage());
        }

        notifyObservers(patientId, "Medication added for patient ID: " + patientId);
    }

    // New method to view medications for a patient
    public void viewMedicationsForPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();

        // Query to retrieve medications for the given patient
        String query = "SELECT * FROM Medication WHERE PatientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            // Check if there are any medications
            if (!rs.next()) {
                System.out.println("No medications found for patient ID: " + patientId);
                return;
            }

            // Create a MedicationView to display the medications
            MedicationView medicationView = new MedicationView();

            // Iterate through all medications for the patient
            do {
                String medicationID = rs.getString("ID");
                String name = rs.getString("Name");
                String dosage = rs.getString("Dosage");
                String frequency = rs.getString("Frequency");
                String sideEffects = rs.getString("SideEffects");

                List<String> sideEffectsList = Arrays.asList(sideEffects.split(", "));

                Medication medication = new Medication(medicationID, name, dosage, frequency, sideEffectsList);
                medicationView.displayMedicationDetails(medication);
            } while (rs.next());

        } catch (SQLException e) {
            System.err.println("Error retrieving medications: " + e.getMessage());
        }
    }

    public void ShowPatientRecord(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor ID: ");
        int doctorID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter patient ID: ");
        int patientID = scanner.nextInt();
        scanner.nextLine();

        String result = proxy.viewRecord(doctorID, patientID);
        System.out.println(result);
    }


    public static void donate() {
        Scanner scanner = new Scanner(System.in);

        // Display the donation options
        System.out.println("Please select your donation type:");
        System.out.println("1 - Money Donation");
        System.out.println("2 - Medical Equipment Donation");
        System.out.print("Enter your choice: ");

        // Get user input
        int choice = scanner.nextInt();

        // Process the donation based on user choice

        if (choice == 1) {
            Donation x = new MoneyDonation();
            x.processDonation();
        } else if (choice == 2) {
            Donation x = new MaterialDonation();
            x.processDonation();
        } else {
            System.out.println("Invalid choice! Please select 1 or 2.");
        }

        scanner.close();
    }


    public void viewDonations() {
        String query = "SELECT * FROM Donation";  // SQL query to select all donations

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            // Display the donation records
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String donorName = resultSet.getString("DonorName");
                String donationType = resultSet.getString("DonationType");
                String donationDetails = resultSet.getString("DonationDetails");
                double amount = resultSet.getDouble("Amount");
                Timestamp donationDate = resultSet.getTimestamp("DonationDate");

                // Displaying each donation's details
                System.out.println("Donation ID: " + id);
                System.out.println("Donor Name: " + donorName);
                System.out.println("Donation Type: " + donationType);
                System.out.println("Donation Details: " + donationDetails);
                System.out.println("Amount: " + amount);
                System.out.println("Donation Date: " + donationDate);
                System.out.println("-------------------------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving donations: " + e.getMessage());
        }
    }




}
