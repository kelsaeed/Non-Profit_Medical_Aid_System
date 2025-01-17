public class Doctor implements Observer {
    private final int id;
    private final String name;

    public Doctor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for Doctor ID: " + id + " (" + name + ") - " + message);
    }
}
