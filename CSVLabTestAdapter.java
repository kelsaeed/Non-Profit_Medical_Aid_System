import java.util.ArrayList;
import java.util.List;

public class CSVLabTestAdapter implements LabTestAdapter {
    private ExternalCSVLabSource csvSource;

    public CSVLabTestAdapter(ExternalCSVLabSource csvSource) {
        this.csvSource = csvSource;
    }

    @Override
    public List<LabTest> getLabTests() {
        List<String[]> rawData = csvSource.getCSVData();
        List<LabTest> labTests = new ArrayList<>();
        for (String[] row : rawData) {
            labTests.add(new LabTest(row[0], row[1]));
        }
        return labTests;
    }
}
