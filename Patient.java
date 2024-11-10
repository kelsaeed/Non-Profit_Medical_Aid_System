// Patient.java
public class Patient implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Patient received appointment update: " + message);
    }
}
