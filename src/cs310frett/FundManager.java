/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.Objects;

/**
 *
 * @author katefrett
 */
public class FundManager {
    private static final int LICENSE_NUMBER_LENGTH = 8;
    private static final int DEPARTMENT_NUMBER_LENGTH = 7;
    
    private String licenseNumber;
    private String firstName;
    private String lastName;
    private String departmentNumber;
    private double commissionRate;

    /**
     * Default parameterless constructor
     */
    public FundManager() {}
    
    /**
     * Constructor which accepts an array of attributes and sets the 
     * properties on a FundManager object from them.
     * 
     * @param attributes 
     */
    public FundManager(String[] attributes) {
        this.setLicenseNumber(attributes[2]);
        this.setFirstName(attributes[3]);
        this.setLastName(attributes[4]);
        this.setDepartmentNumber(attributes[5]);
        // protect against lines in the file with the last field missing
        if (attributes.length == 7) {
            try {
                double commissionRate = Double.parseDouble(attributes[6]);
                this.setCommissionRate(commissionRate);

            } catch (NumberFormatException nfe) {
                System.out.printf("    ERROR: '%s' is an invalid commission "
                        + "rate.  Setting commission rate to 0 for FundManager "
                        + "%s.\n", 
                        attributes[6], this.getLicenseNumber());
                // this defaults to 0.  Putting this here to make it explicit.
                this.setCommissionRate(0d); 
            }
        }
    }
            
    /**
     * Fully parameterized constructor 
     * 
     * @param licenseNumber
     * @param firstName
     * @param lastName
     * @param departmentNumber
     * @param commissionRate
     */
    public FundManager(String licenseNumber, String firstName, String lastName,
            String departmentNumber, double commissionRate) {
        this.licenseNumber = licenseNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentNumber = departmentNumber;
        this.commissionRate = commissionRate;
    }

    /**
     * Retrieve private member variable licenseNumber
     * 
     * @return
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Update the value of the private member variable licenseNumber
     * 
     * @param licenseNumber
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * Retrieve private member variable firstName
     * 
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Update the value of the private member variable firstName
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieve private member variable lastName
     * 
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Update the value of the private member variable lastName
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieve private member variable departmentNumber
     * 
     * @return
     */
    public String getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * Update the value of the private member variable departmentNumber
     * 
     * @param departmentNumber
     */
    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    /**
     * Retrieve private member variable commissionRate
     * 
     * @return
     */
    public double getCommissionRate() {
        return commissionRate;
    }

    /**
     * Update the value of the private member variable commissionRate
     * 
     * @param commissionRate
     */
    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    /**
     * Determine if the private member variable licenseNumber is valid.  Length 
     * must be LICENSE_NUMBER_LENGTH, the first 3 characters must be letters, 
     * and the last 5 characters must be digits. 
     * 
     * @return
     */
    public boolean licenseNumberIsValid() {
        // handle null values
        if (this.licenseNumber == null) {
            return false;
        }
        
        // validate that the licenseNumber has the required number of characters
        if (this.licenseNumber.length() != LICENSE_NUMBER_LENGTH) {
            return false;
        }
        
        char[] licenseCharacters = this.licenseNumber.toCharArray();
        // validate that the first 3 characters are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(licenseCharacters[i])){
                return false;
            }   
        }
        
        // validate that the last 5 characters are digits
        for (int i = 3; i < 8; i++) {
            if (!Character.isDigit(licenseCharacters[i])){
                return false;
            }   
        }
        
        // if the method has not already returned a value, all validation passes
        return true;
    }
    
    /**
     * Determine if the private member variable departmentNumber is valid.  The 
     * valid format is ###-###.
     * 
     * @return
     */
    public boolean departmentNumberIsValid() {
        // handle null values
        if (this.departmentNumber == null) {
            return false;
        }
        
        // validate that the departmentNumber has the required number of characters
        if (this.departmentNumber.length() != DEPARTMENT_NUMBER_LENGTH) {
            return false;
        }
        
        char[] deptCharacters = this.departmentNumber.toCharArray();
        
        // validate that the '-' char is in the middle of the string
        if (deptCharacters[3] != '-') {
            return false;
        }
        
        // validate that the first 3 characters are 1, 2, or 3
        for (int i = 0; i < 3; i++) {
            if (deptCharacters[i] != '1' 
                    && deptCharacters[i] != '2' 
                    && deptCharacters[i] != '3'){
                return false;
            }   
        }
        
        // validate that the last 3 characters are digits
        for (int i = 4; i < 7; i++) {
            if (!Character.isDigit(deptCharacters[i])){
                return false;
            }   
        }
        
        // if the method has not already returned a value, all validation passes
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FundManager other = (FundManager) obj;
        if (Double.doubleToLongBits(this.commissionRate) !=
                Double.doubleToLongBits(other.commissionRate)) {
            return false;
        }
        if (!Objects.equals(this.licenseNumber, other.licenseNumber)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.departmentNumber, other.departmentNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FundManager{" + "licenseNumber=" + licenseNumber + 
                ", firstName=" + firstName + 
                ", lastName=" + lastName +
                ", departmentNumber=" + departmentNumber + 
                ", commissionRate=" + commissionRate + '}';
    }

    /**
     * Calculates the hash code of the FundManager using the modulo-division 
     * method.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        if (this.licenseNumber != null && this.licenseNumber != "") {
            hash = generateHashFromLicenseNumber(this.licenseNumber);
        }
        return hash;
    }
    
    protected static int generateHashFromLicenseNumber(String licenseNumber) {
        // extract the last 5 digit characters from the license number
        int hash = Integer.valueOf(licenseNumber.substring(3, 7));
        hash = hash % FundManagerLogImpl.STARTING_SIZE;
        return hash;
    }
}
