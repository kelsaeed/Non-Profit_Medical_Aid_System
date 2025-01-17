public class Hospital {
    // Private static instance of Hospital (initially null)
    private static Hospital instance = null;

    // Attributes of the Hospital class
    private String hospitalID;
    private String name;
//    private Address address;
    private String contactNumber;
    private int numberOfBeds;
//    private List<Department> departments = new ArrayList<>();
//    private List<PatientRecord> patientRecords = new ArrayList<>();
//    private List<Billing> billingRecords = new ArrayList<>();

    // Private constructor to prevent instantiation from outside
    public Hospital(String hospitalID, String name,  String contactNumber, int numberOfBeds) {
        this.hospitalID = hospitalID;
        this.name = name;
//        this.address = address;
        this.contactNumber = contactNumber;
        this.numberOfBeds = numberOfBeds;
    }

    // Public static method to provide access to the single instance of Hospital
    public static Hospital getInstance(String hospitalID, String name,  String contactNumber, int numberOfBeds) {
        if (instance == null) {
            instance = new Hospital(hospitalID, name, contactNumber, numberOfBeds);
        }
        return instance;
    }

    // Getters and Setters for accessing Hospital attributes
    public String getHospitalID() {
        return hospitalID;
    }

    public String getName() {
        return name;
    }

//    public Address getAddress() {
//        return address;
//    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

//    public List<Department> getDepartments() {
//        return departments;
//    }
//
//    public List<PatientRecord> getPatientRecords() {
//        return patientRecords;
//    }
//
//    public List<Billing> getBillingRecords() {
//        return billingRecords;
//    }
}
