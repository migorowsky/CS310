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
    private FundManagerNode head;

    /**
     * Default parameterless constructor
     */
    public FundManagerLogImpl() {
         this.head = null;
    }
    
    /**
     * Sets the head member variable to the value passed in.
     * 
     * @param newHead 
     */
    public void setHead(FundManagerNode newHead) {
        this.head = newHead;
    }
    
    /** 
     * Data accessor for the private member variable head.
     * @return 
     */
    public FundManagerNode getHead() {
        return this.head;
    }
    
    /**
     * Displays each element in the collection, after displaying a header
     */
    public void traverseDisplay() {
        System.out.println("FundManager Log:");
        
        FundManagerNode current = this.head;
        while (current != null) {
            System.out.println("     " + current.getFundManager().toString());
            current = current.getNextNode();
        }
    }
    
    /**
     * Removes any FundManager items having invalid licenseNumbers
     * from the collection
     */
    public void cleanUp() {
        FundManagerNode currentNode = this.head;
        
        while (currentNode != null) {
            FundManager fundManager = currentNode.getFundManager();
            if (!fundManager.licenseNumberIsValid()) {
                removeFundManager(fundManager.getLicenseNumber());
            }
            currentNode = currentNode.getNextNode();
        }
    }
    
    /**
     * add FundManager object to ordered list and return true if successful
     * 
     * @param obj
     * @return 
     */
    public boolean addFundManager(FundManager fundManager) { 
        boolean success = false;
        
        FundManagerNode newNode = new FundManagerNode(fundManager);
       
        if (this.head == null) {
            // handle empty collection
            head = newNode;
            success = true;
        } else if (newNode.getFundManager().getLicenseNumber()
                .compareTo(this.head.getFundManager().getLicenseNumber()) < 0) {
            // handle a node that belongs before the head node
            newNode.setNextNode(head);
            this.head = newNode;
            success = true;
        } else {
            // handle a new node that needs to be inserted in the middle/end 
            // of the collection
            FundManagerNode previous = this.head;
            FundManagerNode current = this.head.getNextNode();
            while (current != null && 
                    newNode.getFundManager().getLicenseNumber()
                            .compareTo(this.head.getFundManager()
                                    .getLicenseNumber()) >= 0) {
                previous = current;
                current = current.getNextNode();
            }
            newNode.setNextNode(current);
            previous.setNextNode(newNode);
            success = false;
        }
        
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
        boolean success = false;
        
        if (this.head != null) {
            SearchResult searchResult = findNodeByLicenseNumber(licenseNumber);
            
            if (searchResult.isFound()) {
                if (searchResult.getPrevious() == null) {
                    // handle removal of head node
                    if (searchResult.getCurrent().getNextNode() == null) {
                        // linked list only contains one element, and it's the 
                        // item to be removed
                        this.head = null;
                    } else {
                        // the new head node should be the one after the 
                        // current head node
                        this.head = searchResult.getCurrent().getNextNode();
                    }
                } else {
                    // handle removal of all other nodes
                    searchResult.getPrevious().setNextNode(
                            searchResult.getCurrent().getNextNode());
                }
            }
            else {
                // no matching licenseNumber was found in the log, so 
                // nothing could be deleted
                success = false; 
            } 
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
        
        SearchResult searchResult = findNodeByLicenseNumber(licenseNumber);
        isUnique = !searchResult.isFound();
        
        return isUnique;
    }
    
    /**
     * Method that searches through the log to find the FundManagerNode with a 
     * specific license number.  
     * @param licenseNumber
     * @return SearchResult object containing the current node (the one with the 
     *         licenseNumber matching the search criteria) and the previous node
     */
    private SearchResult findNodeByLicenseNumber(String licenseNumber) {
        boolean found = false;
        
        FundManagerNode previous = null;
        FundManagerNode current = this.head;   
        
        while (!found && current != null) {
            if (current.getFundManager().getLicenseNumber()
                    .equals(licenseNumber)) {
                found = true;
            } else {
                previous = current;
                current = current.getNextNode();
            }
        }
        
        SearchResult searchResult;
        if (found) {
            searchResult = new SearchResult(found, previous, current);
        } else {
            searchResult = new SearchResult(found);
        }
        
        return searchResult;
    }
    
    /**
     * Private class used to encapsulate the results of searching through the 
     * FundManagerLog so they can be easily passed between methods.
     */
    private class SearchResult {
        boolean found;
        FundManagerNode previous;
        FundManagerNode current;

        public SearchResult(boolean found, FundManagerNode previous, FundManagerNode current) {
            this.found = found;
            this.previous = previous;
            this.current = current;
        }

        public SearchResult(boolean found) {
            this(found, null, null);
        }

        public SearchResult() {
        }

        public boolean isFound() {
            return found;
        }

        public void setFound(boolean found) {
            this.found = found;
        }

        public FundManagerNode getPrevious() {
            return previous;
        }

        public void setPrevious(FundManagerNode previous) {
            this.previous = previous;
        }

        public FundManagerNode getCurrent() {
            return current;
        }

        public void setCurrent(FundManagerNode current) {
            this.current = current;
        }        
    }
}
