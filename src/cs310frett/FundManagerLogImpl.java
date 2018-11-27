/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

/**
 *
 * @author katefrett
 */
public class FundManagerLogImpl {
    protected static final int STARTING_SIZE = 19;
    private FundManager[] hashTable;

    /**
     * Default parameterless constructor
     */
    public FundManagerLogImpl() {
         this.hashTable = new FundManager[STARTING_SIZE];
    }
    
    /**
     * Displays the contents of the hashmap that underlies the 
     * FundManagerLogImpl, including what index each data point is located at
     */
    public void displayHash() {
        System.out.println("\nFundManager Hash Table:");
        for (int i = 0; i < this.hashTable.length; i++) {
            // build the contents of the line
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("     Index %d ", i));
            FundManager mgr = this.hashTable[i];
            if (mgr != null) {
                sb.append(String.format("contains FundManager %s, %s %s",
                        mgr.getLicenseNumber(), 
                        mgr.getFirstName(), 
                        mgr.getLastName()));
            } else {
                sb.append("is empty");
            }
            
            // write the message to stdout
            System.out.println(sb.toString());
        }
    }
    
    /**
     * Adds the FundManager to the hashTable.  If there is a collision, it's 
     * resolved by linear probing.  Returns boolean true/false indicating 
     * whether the call was successful or not.
     * @param mgr
     * @return 
     */
    public boolean add(FundManager mgr) {
        boolean inserted = false;
        int index = mgr.hashCode() % STARTING_SIZE;
        
        FundManager mgrAtAddress = this.hashTable[index];
        
        // attempt to insert value at address indicated by hash
        if (mgrAtAddress == null) {
            this.hashTable[index] = mgr;
        } else {
            // Collision occurred - address at hash is occupied.  Use linear 
            // probing to find the next empty address.
            int i = 1;
            int idx = 0;
            while (i < this.hashTable.length && !inserted) {
                idx = index + i;

                // be careful of exceeding the bounds of the array, but 
                // ensure we can do wraparounds when we reach the end of 
                // the array 
                if (idx >= this.hashTable.length) {
                    idx = idx - this.hashTable.length;
                }
                if (this.hashTable[idx] == null) {
                    // if we hit a null address, then insert the new FundManager 
                    this.hashTable[idx] = mgr;
                    inserted = true;
                } else {
                    // address not null, so cannot insert value here.  
                    // Increment counter and continue looping.
                    i++;
                }
            }
        }
        
        return inserted;
    }
    
    /**
     * Find FundManager in hashMap by the license number. If fundManager is not 
     * present in the hashmap, null is returned.
     * @param licenseNumber
     * @return 
     */
    public FundManager find(String licenseNumber) {
        FundManager foundMgr = null;
        
        int index = FundManager.generateHashFromLicenseNumber(licenseNumber) 
                % STARTING_SIZE;
        
        FundManager mgr = this.hashTable[index];
        if (mgr != null ) {
            if (mgr.getLicenseNumber().equals(licenseNumber)) {
                // FundManager found at address indicated by hash
                foundMgr = mgr;
            } else {
                // Collision occurred upon insert.  Use linear probing to find
                // the matching fund manager if it exists in the collection.
                boolean cont = true;
                int i = 1;
                int idx = 0;
                while (i < this.hashTable.length && cont) {
                    idx = index + i;
                    
                    // be careful of exceeding the bounds of the array, but 
                    // ensure we can do wraparounds when we reach the end of 
                    // the array 
                    if (idx >= this.hashTable.length) {
                        idx = idx - this.hashTable.length;
                    }
                    if (this.hashTable[idx] == null) {
                        // if we hit a null address, then the licenseNumber is 
                        // not in the hashTable.  Exit the loop & return null.
                        cont = false;
                    } else if (this.hashTable[idx].getLicenseNumber().equals(licenseNumber)){
                        // if the address is not null and the license matches, 
                        // we found the requested value. Exit the loop * return 
                        // matching value.
                        cont = false;
                        foundMgr = this.hashTable[idx];
                    } else {
                        // no match - increment counter and continue iterating
                        i++;
                    }
                }
            }
        }
        
        return foundMgr;
    }
}
