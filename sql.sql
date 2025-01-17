-- Create the database
CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

-- Create Doctor table
CREATE TABLE IF NOT EXISTS Doctor (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

-- Create Patient table with the new "Record" column
CREATE TABLE IF NOT EXISTS Patient (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    BillingType VARCHAR(50),
    Services TEXT,
    Record VARCHAR(255)  -- New column added to store patient record details
);

-- Create DoctorPatient table (many-to-many relationship between Doctor and Patient)
CREATE TABLE IF NOT EXISTS DoctorPatient (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    DoctorID INT NOT NULL,
    PatientID INT NOT NULL,
    FOREIGN KEY (DoctorID) REFERENCES Doctor(ID),
    FOREIGN KEY (PatientID) REFERENCES Patient(ID)
);

-- Create Medication table (linked to Patient)
CREATE TABLE IF NOT EXISTS Medication (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Dosage VARCHAR(255),
    Frequency VARCHAR(255),
    SideEffects TEXT,
    FOREIGN KEY (PatientID) REFERENCES Patient(ID) ON DELETE CASCADE
);

-- Create Appointment table (linked to Doctor and Patient)
CREATE TABLE IF NOT EXISTS Appointment (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    DoctorID INT NOT NULL,
    PatientID INT NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (DoctorID) REFERENCES Doctor(ID) ON DELETE CASCADE,
    FOREIGN KEY (PatientID) REFERENCES Patient(ID) ON DELETE CASCADE
);
