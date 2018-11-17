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
public class BrokerQueueImpl {
    FundManagerNode frontNode;
    FundManagerNode rearNode;

    /**
     * Default parameterless constructor
     */
    public BrokerQueueImpl() {
        frontNode = null;
        rearNode = null;
    }

    /**
     * Data accessor for private member variable frontNode
     * @return 
     */
    public FundManagerNode getFrontNode() {
        return frontNode;
    }

    /**
     * Add the fundManager to the end/rear of the queue
     * @param fundManager 
     */
    public void addToQueue (FundManager fundManager) {
        FundManagerNode newNode = new FundManagerNode(fundManager); 
        if (rearNode != null) {
            rearNode.setNextNode(newNode);
        } 
        else {
            frontNode = newNode;
        }
        rearNode = newNode;
    }

    /** 
     * Remove the fundManager from the front of the queue
     * @return 
     */
    private FundManager removeFromQueue () {
        FundManager fundManager = null;
        if (frontNode != null) {
            fundManager = frontNode.getFundManager();
            frontNode = frontNode.getNextNode();
        } 
        else {
            System.out.println("Queue was empty - cannot return FundManager");
        }
        return fundManager;
    }

    /**
     * Determine whether the queue is empty
     * @return 
     */
    public boolean isEmpty() {
        boolean isEmpty = false;
        
        if (frontNode == null) {
            isEmpty = true;
        }
        
        return isEmpty;
    }

    /**
     * Determine whether the queue is full.  The only way the queue is full is 
     * if there is an OOM error when trying to create a new node.
     * @return 
     */
    private boolean isFull() {
        boolean isFull = false;
        
        try {
            FundManagerNode newNode = new FundManagerNode(); 
        } catch (OutOfMemoryError oom) {
            System.out.println("ERROR: Out of memory. Cannot add new "
                    + "FundManager to the queue.");
            isFull = true;
        }
        
        return isFull;
    }
    
    /**
     * Calculate how many FundManagers are in the queue
     * @return 
     */
    private int calculateNumberOfNodes() {
        int count = 0;
        
        FundManagerNode currNode = frontNode;
        while (currNode != null) {
            count++;
            currNode = currNode.getNextNode();
        }
        
        return count;
    }

    /**
     * Print the contents of the queue, from front to rear.
     */
    private void printQueue() {
        if (!isEmpty()) {
            int count = 1;
            FundManagerNode currNode = frontNode;
            count = calculateNumberOfNodes();

            if (count > 1) {
                System.out.println("The queue contains " + count + " FundManagers");
            } 
            else {
                System.out.println ("The queue contains 1 FundManager");
            }

            int node = 0;
            while (currNode != null) {
                System.out.println ("node[" + node + "] = " 
                                     + currNode.getFundManager().toString());
                node++;
                currNode = currNode.getNextNode();
            }
            System.out.println();
        } 
        else {
            System.out.println ("Queue is empty");
        }
    }
}
