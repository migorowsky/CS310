/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author katefrett
 */
public class StockTradeLogImpl {
    private static int MAX_TRADES = 1000;
    
    private LinkedList<StockTrade> stockTradeLog = new LinkedList<>();
    private int numStockTrades;

    /**
     * Default parameterless constructor
     */
    public StockTradeLogImpl() {
        this.numStockTrades = 0;
    }

    /** 
     * Data accessor for the private member variable stockTradeLog
     * @return 
     */
    public LinkedList<StockTrade> getStockTradeLog() {
        return stockTradeLog;
    }
    
    /**
     * Displays each element in the collection, after displaying a header
     */
    public void traverseDisplay() {
        System.out.println("StockTrade Log:");
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        
        while(iter.hasNext()) {
            StockTrade trade = iter.next();
            System.out.println("     " + trade.toString());
        }
    }
    
    /**
     * Removes any StockTrade items having invalid stockSymbols
     * from the collection
     */
    public void cleanUp() {
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        
        while(iter.hasNext()) {
            StockTrade trade = iter.next();
            if (!trade.symbolIsValid()) {
                System.out.println("Invalid symbol for stockTrade " + 
                        trade.getSymbol() + " -- Deleting stockTrade from log");
                iter.remove();
            }
        }
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
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        
        while(iter.hasNext()) {
            StockTrade trade = iter.next();
            if (trade.getLicenseNumber()
                    .equals(licenseNumber)) {
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
        boolean success = false;
        
        if (this.stockTradeLog.size() < MAX_TRADES) {
            this.stockTradeLog.add(stockTrade);
            numStockTrades++;
            success = true;
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
        boolean itemRemoved = false;
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        while (iter.hasNext()) {
            StockTrade trade = iter.next();
            if (trade.getLicenseNumber()
                    .equals(licenseNumber)) {
                iter.remove();
                this.numStockTrades--;
                itemRemoved = true;
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
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        while (!itemRemoved && iter.hasNext()) {
            StockTrade trade = iter.next();
            if (trade.getSymbol()
                    .equals(stockSymbol)) {
                iter.remove();
                this.numStockTrades--;
                itemRemoved = true;
            }
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
        
        boolean found = false;
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        while (!found && iter.hasNext()) {
            StockTrade trade = iter.next();
            if (trade.getSymbol()
                    .equals(stockSymbol)) {
                found = true;
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
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        while (iter.hasNext()) {
            StockTrade trade = iter.next();
            totalValue += getStockTradeValue(trade);
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
        
        Iterator<StockTrade> iter = this.stockTradeLog.iterator();
        while (iter.hasNext()) {
            StockTrade trade = iter.next();
            if (trade.getLicenseNumber()
                    .equals(licenseNumber)) {
                totalValue =+ getStockTradeValue(trade);
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
