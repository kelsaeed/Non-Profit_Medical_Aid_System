// InsuranceBilling.java
public class InsuranceBilling implements Observer,BillingStrategy {
    @Override
    public void update(String message) {
        System.out.println("InsuranceBilling received billing update: " + message);
    }

    @Override
    public void calculateBill() {
        System.out.println("Insurance Billing applied.");
    }
}
