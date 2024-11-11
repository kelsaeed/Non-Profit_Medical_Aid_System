// CharityBilling.java
public class CharityBilling implements Observer,BillingStrategy {
    @Override
    public void update(String message) {
        System.out.println("CharityBilling received billing update: " + message);
    }

    @Override
    public void calculateBill() {
        System.out.println("CharityBilling Billing applied.");
    };

}
