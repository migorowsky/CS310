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
public class FundManagerNode {
    private FundManager fundManager;
    private FundManagerNode nextNode;

    /**
     * Default parameterless constructor
     */
    public FundManagerNode() {
        this(null, null);
    }
    
    /**
     * Constructor which calls the constructor that accepts a fundManager 
     * and nextNode, providing null for the nextNode parameter
     * 
     * @param fundManager 
     */
    public FundManagerNode(FundManager fundManager) {
        this(fundManager, null);
    }
    
    /**
     * Constructor that accepts all the values to populate all the private 
     * member variables
     * 
     * @param fundManager
     * @param nextNode 
     */
    public FundManagerNode(FundManager fundManager, FundManagerNode nextNode) {
        this.fundManager = fundManager;
        this.nextNode = nextNode;
    }

    public FundManager getFundManager() {
        return fundManager;
    }

    public void setFundManager(FundManager fundManager) {
        this.fundManager = fundManager;
    }

    public FundManagerNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(FundManagerNode nextNode) {
        this.nextNode = nextNode;
    }
    
    
}
