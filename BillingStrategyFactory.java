public class BillingStrategyFactory {
    public static BillingStrategy getBillingStrategy(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Billing type cannot be null or empty.");
        }

        if (type.equalsIgnoreCase("cash")) {
            return new CashBilling();
        } else if (type.equalsIgnoreCase("insurance")) {
            return new InsuranceBilling();
        } else if (type.equalsIgnoreCase("charity")) {
            return new CharityBilling();
        } else {
            System.err.println("Unknown billing type requested: " + type);
            throw new IllegalArgumentException("Unknown billing type: " + type);
        }
    }
}
