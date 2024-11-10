// Nurse.java
public class Nurse implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Nurse received appointment update: " + message);
    }
}
