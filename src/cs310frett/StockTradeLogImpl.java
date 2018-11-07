/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.Arrays;

/**
 *
 * @author katefrett
 */
public class StockTradeLogImpl {
    private static int MAX_TRADES = 1000;
    
    private StockTrade[] stockTradeLog;
    private int numStockTrades;

    /**
     * Default parameterless constructor
     */
    public StockTradeLogImpl() {
        this.stockTradeLog = new StockTrade[0];
        this.numStockTrades = this.stockTradeLog.length;
    }
    
    /**
     * return the array attribute
     * @return 
     */
    public StockTrade[] getStockTradeArray() {
        return this.stockTradeLog;
    } 
    
    /**
     * return the count attribute
     * @return 
     */
    public int getNumStockTrades() {
        return this.numStockTrades;
    }
    
    /**
     * return count of StockTrade objects in log with specific FundManager license
     * @param licenseNumber
     * @return 
     */
    public int numberOfStockTrades(String licenseNumber) {
        int count = 0;
        
        for (int i = 0; i < this.stockTradeLog.length; i++) {
            if (this.stockTradeLog[i].getLicenseNumber()
                    .compareTo(licenseNumber) == 0) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * add StockTrade to log if there is room, and return true if successful
     * @param stockTrade
     * @return 
     */
    public boolean addStockTrade(StockTrade stockTrade) {
        boolean success = true;
        
        if (this.stockTradeLog.length >= MAX_TRADES) {
            success = false;
        } else {
            try {
                int index = this.numStockTrades;
                
                // Increase the array size by 1 
                this.stockTradeLog = Arrays.copyOf(this.stockTradeLog, 
                        this.stockTradeLog.length + 1);

                // Add the new stockTrade to the end of the array
                this.stockTradeLog[index] = stockTrade;

                // update the numStockTrades to reflect the new array size
                this.numStockTrades = this.stockTradeLog.length;
            } catch (Exception e) {
                success = false;
            }
        }
        
        return success;
    }

    /**
     * delete all StockTrade objects from log for FundManager with license
     * & return true if any StockTrade objects were deleted
     * @param licenseNumber
     * @return 
     */
    public boolean removeStockTradesByFundManager(String licenseNumber) {
        // TODO - revisit this method - it's not doing what it's supposed to.  
        // It should delete ALL stock trades for a licenseNumber, but it's only 
        // deleting one trade for a licenseNumber
        boolean itemRemoved = false;
        
        // Find elementToRemoveIndex of StockTrade with the appropriate 
        // licenseNumber
        boolean found = false;
        int elementToRemoveIndex = 0;
        while (elementToRemoveIndex < this.stockTradeLog.length) {
            if (this.stockTradeLog[elementToRemoveIndex].getLicenseNumber()
                    .equals(licenseNumber)) {
                removeItemAtIndex(elementToRemoveIndex);
                elementToRemoveIndex = 0;
            } else {
                elementToRemoveIndex++;  
            }
        }
        
        return itemRemoved;
    }
    
    /**
     * remove StockTrade with stock symbol from log
     * @param stockSymbol
     * @return 
     */
    public boolean removeStockTrade(String stockSymbol) {
        boolean itemRemoved = false;
        
        // Find elementToRemoveIndex of StockTrade with the appropriate 
        // stockSymbol
        boolean found = false;
        int elementToRemoveIndex = 0;
        while (!found && elementToRemoveIndex < this.stockTradeLog.length) {
            if (this.stockTradeLog[elementToRemoveIndex].getSymbol()
                    .equals(stockSymbol)) {
                found = true;
            } else {
              elementToRemoveIndex++;  
            }
        }
        
        if (found) {
            itemRemoved = removeItemAtIndex(elementToRemoveIndex);
        }
        
        return itemRemoved;
    }
    
    /**
     * Removes a StockTrade at a specified index from the stockTradeLog
     * @param elementToRemoveIndex
     * @return 
     */
    private boolean removeItemAtIndex(int elementToRemoveIndex) {
        boolean itemRemoved = false;
        
        try {
            // remove the StockTrade from the log:
            
            // copy the contents in the last array position to the position 
            // where the item to remove is located
            int indexOfLastElement = this.stockTradeLog.length - 1;
            this.stockTradeLog[elementToRemoveIndex] = 
                    this.stockTradeLog[indexOfLastElement];
            
            // shrink the array by 1, removing the last element in the array
            this.stockTradeLog = Arrays.copyOf(this.stockTradeLog, 
                        this.stockTradeLog.length -1);

            // update the numStockTrades to reflect the new array size
            this.numStockTrades = this.stockTradeLog.length;
            
            itemRemoved = true;
        } catch (Exception e) {
            itemRemoved = false;
        }
        
        return itemRemoved;
    }
    
    /**
     * test if StockTrade object with stockSymbol exists in log
     * @param stockSymbol
     * @return 
     */
    public boolean isStockSymbolUnique(String stockSymbol) {
        boolean isUnique = true;
        
        // find the appropriate elementToRemoveIndex at which the StockTrade
        // with the stockSymbol is located
        int index = 0;
        boolean found = false;
        while (!found && index < this.stockTradeLog.length) {
            if (this.stockTradeLog[index].getSymbol()
                    .compareTo(stockSymbol) == 0) {
                found = true;
            } else {
                index++;
            }
        }
        
        // if the stockSymbol exists in the log, it's not unique
        isUnique = !found;
        
        return isUnique;
    }
    
    /**
     * return sum of stock holdings
     * @return 
     */
    public double totalStockTradeValue() {
        double totalValue = 0;
        
        for (int i = 0; i < this.stockTradeLog.length; i++) {
            totalValue += getStockTradeValue(this.stockTradeLog[i]);
        }
        
        return totalValue;
    }
    
    /**
     * overloaded - return sum of asking prices for specific FundManager license
     * @param licenseNumber
     * @return 
     */
    public double totalStockTradeValue(String licenseNumber) {
        double totalValue = 0;
        
        for (int i = 0; i < this.stockTradeLog.length; i++) {
            if (this.stockTradeLog[i].getLicenseNumber()
                    .equals(licenseNumber)) {
                totalValue =+ getStockTradeValue(this.stockTradeLog[i]);
            }
        }
        
        return totalValue;
    }   
    
    /**
     * Calculate the value of the trade based upon the number of shares and the
     * price per share
     * 
     * @param stockTrade
     * @return 
     */
    public static double getStockTradeValue(StockTrade stockTrade) {
        double tradeValue = stockTrade.getPricePerShare() 
                * stockTrade.getNumberOfShares();
        
        return tradeValue;
    }
}
