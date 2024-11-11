public class CashBilling implements  BillingStrategy{
    @Override
    public void calculateBill() {
        System.out.println("Cash Billing applied.");
    }
}
