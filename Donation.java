import java.util.*;

public abstract class Donation {
    public String donorName;
    public int donorAge;

    // Template method defining the donation process
    public final void processDonation() {
        takeDonorInfo();
        collectDonationDetails();
        validateDonation();
        confirmationMessage();
    }

    // Collect donor information
    public final void takeDonorInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter donor's name: ");
        donorName = scanner.nextLine();
        System.out.print("Enter donor's age: ");
        donorAge = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    }

    // Thank the donor
    public final void confirmationMessage() {
        System.out.println("Thank you for your generous donation, " + donorName + "!");
    }

    // Abstract methods to be implemented by subclasses
    public abstract void collectDonationDetails();
    public abstract void validateDonation();
}
