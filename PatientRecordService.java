import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRecordService implements PatientRecord {
    private Connection connection;

    public PatientRecordService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @Override
    public String viewRecord(int patientId) {
        String record = null;
        String query = "SELECT record FROM patient WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                record = resultSet.getString("record");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch patient record", e);
        }
        return "This patient record is" +": " + record;
    }
}
