/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.ArrayList;

/**
 *
 * @author katefrett
 */
public class FundManagerLogImpl {
    private ArrayList<FundManager> fundManagerLog;

    /**
     * Default parameterless constructor
     */
    public FundManagerLogImpl() {
         this.fundManagerLog = new ArrayList<>();
    }
    
    /**
     * Retrieve private member variable fundManagerLog
     * 
     * @return
     */
    public ArrayList<FundManager> getFundManagerLog() {
        return this.fundManagerLog;
    }
    
    /**
     * add FundManager object to ordered list and return true if successful
     * 
     * @param obj
     * @return 
     */
    public boolean addFundManager(FundManager fundManager) { 
        boolean success = true;
        
        // find the appropriate index at which the fundManager should 
        // be inserted into the log 
        int index = 0;
        boolean found = false;
        while (!found && index < this.fundManagerLog.size()) {
            if (this.fundManagerLog.get(index).getLicenseNumber()
                    .compareTo(fundManager.getLicenseNumber()) > 0) {
                found = true;
            } else {
                index++;
            }
        }
        
        // insert the fundManager at the appropriate index
        this.fundManagerLog.add(index, fundManager);
        
        // TODO - under what conditions could this method fail?  When should false be returned?
        return success;
    }
    
    /**
     * remove FundManager with specific license from list and 
     * return true if successful
     * 
     * @param license
     * @return 
     */
    public boolean removeFundManager (String licenseNumber) {
        boolean success = true;
        
        // find the appropriate index at which the fundManager to be deleted 
        // is located
        int index = 0;
        boolean found = false;
        while (!found && index < this.fundManagerLog.size()) {
            if (this.fundManagerLog.get(index).getLicenseNumber()
                    .compareTo(licenseNumber) == 0) {
                found = true;
            } else {
                index++;
            }
        }
        
        if (!found) {
            // no matching licenseNumber was found in the log, so 
            // nothing could be deleted
            success = false; 
        } else {
            this.fundManagerLog.remove(index);
        }
        
        return success;
    }
    
    /**
     * test if FundManager with specific license exists in log
     * 
     * @param license
     * @return 
     */
    public boolean isLicenseUnique(String licenseNumber) {
        boolean isUnique = true;
        
        // find the appropriate index at which the fundManager
        // with the licenseNumber is located
        int index = 0;
        boolean found = false;
        while (!found && index < this.fundManagerLog.size()) {
            if (this.fundManagerLog.get(index).getLicenseNumber()
                    .equals(licenseNumber)) {
                found = true;
            } else {
                index++;
            }
        }
        
        // if the licenseNumber exists in the log, it's not unique
        isUnique = !found;
        
        return isUnique;
    }
}
