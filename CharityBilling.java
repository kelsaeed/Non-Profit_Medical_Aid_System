// CharityBilling.java
public class CharityBilling implements Observer {
    @Override
    public void update(String message) {
        System.out.println("CharityBilling received billing update: " + message);
    }
}
