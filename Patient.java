public class Patient implements Observer {
    private final int id;
    private final String name;

    public Patient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(String message) {
        System.out.println("Patient " + name + " (ID: " + id + ") notified: " + message);
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name;
    }
}
