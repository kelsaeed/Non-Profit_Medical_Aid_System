// Doctor.java
public class Doctor implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Doctor received appointment update: " + message);
    }
}
