// InsuranceBilling.java
public class InsuranceBilling implements Observer {
    @Override
    public void update(String message) {
        System.out.println("InsuranceBilling received billing update: " + message);
    }
}
