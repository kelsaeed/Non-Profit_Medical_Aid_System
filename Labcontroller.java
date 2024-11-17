import java.util.HashMap;
import java.util.Map;

public class LabController {

    private Map<String, LabTest> labTests = new HashMap<>();

    // Add a new test to the lab
    public void addTest(LabTest test) {
        labTests.put(test.getTestID(), test);
    }

    // Conduct a test for a patient
    public String conductTest(String labTestID, String patientID) {
        LabTest test = labTests.get(labTestID);
        if (test != null) {
            // Simulate conducting the test and providing a result
            test.setTestResult("Completed");
            return "Test for patient " + patientID + " has been completed. Result: " + test.getTestResult();
        }
        return "Test not found!";
    }

    // For testing or getting all tests (optional)
    public Map<String, LabTest> getAllTests() {
        return labTests;
    }
}
