public class LabTest {
    private String testID;
    private String testName;
    private String testResult;

    public LabTest(String testID, String testName) {
        this.testID = testID;
        this.testName = testName;
        this.testResult = "Pending";
    }

    // Getters and Setters
    public String getTestID() {
        return testID;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
}
