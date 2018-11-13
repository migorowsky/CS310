/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * @author katefrett
 */
public class GoKartUsageImpl {
    
    Map<Integer, FundManager> goKartAssignments;

    public GoKartUsageImpl() {
        this.goKartAssignments = new HashMap<>();
    }

    public Map<Integer, FundManager> getGoKartAssignments() {
        return goKartAssignments;
    }
    
    public void checkoutGoKart(int goKartNumber, FundManager fundManager) {        
        if (!goKartIsAvailable(goKartNumber)) {
            System.out.println("ERROR: GoKart " + goKartNumber +
                    " is already in use.");
        } else {
            this.goKartAssignments.put(goKartNumber, fundManager);
        }
    }
    
    public void returnGoKart(int goKartNumber) {
        if (goKartIsAvailable(goKartNumber)) {
            System.out.println("ERROR: GoKart " + goKartNumber +
                    " is not in use.");
        } else {
            this.goKartAssignments.remove(goKartNumber);
        }
    }
    
    private boolean goKartIsAvailable(int goKartNumber) {
        boolean goKartIsAssigned = this.goKartAssignments
                .containsKey(goKartNumber);
        return !goKartIsAssigned;
    }
            
    /**
     * Looks up the kart number that a fundManager has been assigned to.
     * @param fundManager
     * @return 
     */
    public int getGokartNumber(FundManager fundManager) {
        Iterator iter = this.goKartAssignments.entrySet().iterator();
        int kartNumber = -1;
        
        while (iter.hasNext()) {
            Map.Entry kvPair = (Map.Entry)iter.next();
            
            FundManager mgr = (FundManager)kvPair.getValue();
            if (mgr.equals(fundManager)) {
                kartNumber = (Integer)kvPair.getKey();
            }
        }
        
        return kartNumber;
    }
}
