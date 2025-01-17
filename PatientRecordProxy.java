import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRecordProxy implements PatientRecord {
    private PatientRecordService realService;
    private Connection connection;

    public PatientRecordProxy(PatientRecordService service) {
        this.realService = service;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    private boolean checkAccess(int doctorId, int patientId) {
        String query = "SELECT * FROM doctorpatient WHERE doctorID = ? AND patientID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check access", e);
        }
    }

    @Override
    public String viewRecord(int patientId) {
        throw new UnsupportedOperationException("Access must be verified first");
    }

    public String viewRecord(int doctorId, int patientId) {
        if (checkAccess(doctorId, patientId)) {
            return realService.viewRecord(patientId);
        } else {
            return "Invalid access. This doctor is not authorized to see this patient record";
        }
    }
}
