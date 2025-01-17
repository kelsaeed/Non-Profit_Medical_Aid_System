import java.util.ArrayList;
import java.util.List;

public class LabController {
    private List<LabTest> labTests;

    public LabController() {
        this.labTests = new ArrayList<>();
    }

    // Existing method to add a LabTest manually
    public void addLabTest(LabTest test) {
        labTests.add(test);
    }

    // New method to import LabTests via an adapter
    public void importLabTests(LabTestAdapter adapter) {
        List<LabTest> importedTests = adapter.getLabTests();
        labTests.addAll(importedTests);
    }

    public void conductTest(String testID) {
        for (LabTest test : labTests) {
            if (test.getTestID().equals(testID)) {
                test.setTestResult("Completed");
                return;
            }
        }
        System.out.println("Test not found!");
    }

    public List<LabTest> getLabTests() {
        return labTests;
    }
}
