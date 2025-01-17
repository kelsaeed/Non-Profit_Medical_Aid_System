import java.util.List;

public class PatientDatabaseIterator implements PatientIterator {
    private final List<Patient> patients;
    private int currentIndex = 0;

    public PatientDatabaseIterator(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < patients.size();
    }

    @Override
    public Patient next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more patients available.");
        }
        return patients.get(currentIndex++);
    }
}
