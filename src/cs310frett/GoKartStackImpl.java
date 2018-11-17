/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.lang.IndexOutOfBoundsException;

/**
 *
 * @author katefrett
 */
public class GoKartStackImpl {
    int[] goKartStack;
    int top = -1;
    
    /**
     * Default parameterless constructor
     */
    public GoKartStackImpl() {}

    /**
     * Data accessor for private field top
     * @return 
     */
    public int getTop() {
        return top;
    }
    
    public int getKartNumberAtIndex(int index) {
        int kartNumber = -1;
        try {
            kartNumber = goKartStack[index];
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println("There is no gokart at index " + index);
        }
        return kartNumber;
    }
    
    /**
     * Initialize the goKartsStack with all the elements from the 
     * goKarts array passed in.
     * @param goKarts 
     */
    public void setGoKarts(int[] goKarts) {
        this.goKartStack = new int[goKarts.length];
        for (int kartNumber : goKarts) {
            this.push(kartNumber);
        }
    }
    
    /**
     * Add a goKart to the top of the stack
     * @param goKartNumber 
     */
    public void push(int goKartNumber) {
        if (this.top < this.goKartStack.length) {        
            this.top++;                                
            this.goKartStack[top] = goKartNumber;       
        }
        else {
            System.out.println ("The go kart stack is full");
        }
    }
    
    /**
     * Determine if the stack is empty
     * @return 
     */
    public boolean isEmpty() {     
        boolean isEmpty = false;
        
        if (this.top < 0) {
            isEmpty = true;
        }
        
        return isEmpty;
    }
    
    /**
     * Determine if the stack is full
     * @return 
     */
    public boolean isFull() {     
        boolean isFull = false;
        
        if (this.top == this.goKartStack.length - 1) {
            isFull = true;
        }
        
        return isFull;
    }
    
    /**
     * Remove the top goKart from the stack
     * @return 
     */
    public int pop () {
        int goKartNumber = -1;

        if (!this.isEmpty()) {                      
            goKartNumber = this.goKartStack[top];      
            top--;                              
        }
        else {
            System.out.println ("the go kart stack is empty");
        }
        
        return goKartNumber;
    }  
}
