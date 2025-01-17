import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDatabase implements PatientCollection {
    private final List<Patient> patients = new ArrayList<>();
    private final Connection connection;

    public PatientDatabase(Connection connection) {
        this.connection = connection;
        loadPatientsFromDB();
    }

    private void loadPatientsFromDB() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Patient")) {
            while (rs.next()) {
                patients.add(new Patient(rs.getInt("ID"), rs.getString("Name")));
            }
        } catch (SQLException e) {
            System.err.println("Error loading patients from DB: " + e.getMessage());
        }
    }

    @Override
    public PatientIterator createIterator() {
        return new PatientDatabaseIterator(patients);
    }
}
