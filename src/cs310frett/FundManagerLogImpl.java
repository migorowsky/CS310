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
    // the Binary Search Tree root element
    private FundManagerNode bstRoot = null;

    /**
     * Default parameterless constructor
     */
    public FundManagerLogImpl() {}
    
    /**
     * Entry point for callers who want to print the contents of the BST
     */
    public void traverseDisplay() {
        System.out.println("\nFundManager List:");
        inorderTraverse(this.bstRoot);
    }
    
    /**
     * Recursively traverses the BST in an "in-order" manner and outputs 
     * the contents of the BST to stdout
     * @param root 
     */
    private void inorderTraverse(FundManagerNode root){
        // if tree is empty, do nothing
        if (root != null) {
            // else inorder traverse the left subtree
            inorderTraverse(root.getLeftChild());
        
            // visit root
            FundManager mgr = root.getFundManager();
            System.out.println(String.format("   FundManager %s, %s %s",
                mgr.getLicenseNumber(), 
                mgr.getFirstName(), 
                mgr.getLastName()));
            
            // inorder traverse the right subtree
            inorderTraverse(root.getRightChild());
        }
    }
    
    /**
     * Entry point for callers who want to insert a record but do not know the 
     * root node of the BST.
     * @param mgr
     * @return 
     */
    public void add(FundManager mgr) {
        this.bstRoot = this.add(this.bstRoot, mgr);
    }
    
    /**
     * Adds the FundManager to the binary search tree using recursion.  
     * 
     * @param mgr
     * @return 
     */
    private FundManagerNode add(FundManagerNode currentRoot, FundManager mgr) {        
        
        if (currentRoot == null) {
            FundManagerNode newNode = new FundManagerNode(mgr);
            currentRoot = newNode;
        }
        else {
            int compareResult = mgr.getLicenseNumber()
                    .compareTo(currentRoot.getFundManager().getLicenseNumber());
            
            // licenseNumber is less than the value on the currentRoot node
            if (compareResult < 0) {    
                if (currentRoot.getLeftChild() != null) { 
                    add(currentRoot.getLeftChild(), mgr);
                } 
                else {
                    FundManagerNode newNode = new FundManagerNode(mgr);
                    currentRoot.setLeftChild(newNode); 
                }
            } 
            
            // licenseNumber is greater than the value on the currentRoot node
            if (compareResult > 0) {
                if (currentRoot.getRightChild() != null) {
                    add(currentRoot.getRightChild(), mgr);
                } 
                else {
                    FundManagerNode newNode = new FundManagerNode(mgr);
                    currentRoot.setRightChild(newNode);  
                }
            }
            
            // licenseNumber equals the value on the currentRoot node - it's a
            // duplicate and cannot be inserted
            if (compareResult == 0) {
                // no-op
            }
        }   
        
        return currentRoot;
    }
    
    /**
     * Entry point for callers who want to find a record but do not know the 
     * root node of the BST.
     * 
     * @param licenseNumber
     * @return 
     */
    public FundManager find(String licenseNumber) {
        return find(this.bstRoot, licenseNumber);
    }
    
    /**
     * Find FundManager in the binary search tree by the license number using 
     * recursion.
     * @param licenseNumber
     * @return 
     */
    public FundManager find(FundManagerNode currentRoot, String licenseNumber) {
        FundManager foundMgr = null;
        
        if (currentRoot != null) {
            int compareResult = licenseNumber
                    .compareTo(currentRoot.getFundManager().getLicenseNumber());
            
            // found matching node
            if (compareResult == 0) {
                foundMgr = currentRoot.getFundManager();
            }
            
            // licenseNumber is less than the value on the currentRoot node. 
            // Traverse left child.
            if (compareResult < 0) {    
                foundMgr = find(currentRoot.getLeftChild(), licenseNumber); 
            } 
            
            // licenseNumber is greater than the value on the currentRoot node. 
            // Traverse right child.
            if (compareResult > 0) {
                foundMgr = find(currentRoot.getRightChild(), licenseNumber);
            }
        }   
        
        return foundMgr;
    }
}
