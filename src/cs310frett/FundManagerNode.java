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
    private FundManagerNode leftChild;
    private FundManagerNode rightChild;

    /**
     * Default parameterless constructor
     */
    public FundManagerNode() {
        this(null, null, null);
    }
    
    /**
     * Constructor which calls the constructor that accepts a fundManager 
     * and left and right child nodes, providing null for the left and right
     * child parameters.
     * 
     * @param fundManager 
     */
    public FundManagerNode(FundManager fundManager) {
        this(fundManager, null, null);
    }
    
   /**
    * Constructor that accepts all the values to populate all the private 
    * member variables
    * 
    * @param fundManager
    * @param leftChild
    * @param rightChild 
    */
    public FundManagerNode(FundManager fundManager, 
            FundManagerNode leftChild, 
            FundManagerNode rightChild) {
        this.fundManager = fundManager;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public FundManager getFundManager() {
        return fundManager;
    }

    public void setFundManager(FundManager fundManager) {
        this.fundManager = fundManager;
    }

    public FundManagerNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(FundManagerNode leftChild) {
        this.leftChild = leftChild;
    }

    public FundManagerNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(FundManagerNode rightChild) {
        this.rightChild = rightChild;
    }
}
