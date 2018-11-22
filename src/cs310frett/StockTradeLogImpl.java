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
    
    protected static final int STARTING_SIZE = 17;
    private MapEntry[] hashMap;

//    private int numStockTrades;

    /**
     * Default parameterless constructor
     */
    public StockTradeLogImpl() {
//        this.numStockTrades = 0;
        this.hashMap = new MapEntry[STARTING_SIZE];
    }

    /** 
     * Data accessor for the private member variable hashMap
     * @return 
     */
    public MapEntry[] getStockTradeLog() {
        return hashMap;
    }
    
    /**
     * Displays the contents of the hashmap that underlies the 
     * StockTradeLogImpl, including what index each data point is located at
     */
    public void displayHash() {
        System.out.println("\nStockTrade Hash Table:");
    
        for (int i = 0; i < this.hashMap.length; i++) {
            // build the contents of the line
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("     Index %d ", i));
            MapEntry entry = this.hashMap[i];
            if (entry != null && entry.getValue() != null) {
                StockTradeNode node = entry.getValue();
                sb.append("contains StockTrades: ");
                // iterate through the linked list and append each 
                // Stock Trade's symbol to the line
                while (node != null) {
                    sb.append(String.format("%s ", 
                            node.getStockTrade().getSymbol()));
                    node = node.getNextNode();
                }
            } else {
                sb.append("is empty");
            }
            
            // write the message to stdout
            System.out.println(sb.toString());
        }
    }
    
    /**
     * add StockTrade to hashMap if there is room, and return true if successful
     * @param stockTrade
     * @return 
     */
    public boolean add(StockTrade stockTrade) {
        boolean success = false;
        
        int index = stockTrade.hashCode();
        
        MapEntry entry = this.hashMap[index];
        
        // TODO - come back to this
        if (entry == null) {
            // no data has been stored at this location yet.  Create a node 
            // and add it to the collection.
            StockTradeNode node = new StockTradeNode(stockTrade, null);
            // it seems redundant to store the array index in the MapEntry.key, 
            // but that's what the specifications require - is this correct?
            entry = new MapEntry(index, node);
            this.hashMap[index] = entry; 
            success = true;
        } else {
            // a stock trade already exists at this location.  Need to add this 
            // new StockTrade to the end of the linkedList that is stored at 
            // this index.
            StockTradeNode newNode = new StockTradeNode(stockTrade, null);
            StockTradeNode existingNode = entry.getValue();
            // traverse the linked list until we reach the node with no nextNode
            while (existingNode.getNextNode() != null) {
                existingNode = existingNode.getNextNode();
            }
            // set the nextNode for the last node in the 
            // linkedList to the newNode
            existingNode.setNextNode(newNode);
            success = true;
        }
        
        return success;
    }
    
    /**
     * Find StockTrade in hashMap by the stock symbol. If trade is not present 
     * in the hashmap, null is returned.
     * @param symbol
     * @return 
     */
    public StockTrade find(String symbol) {
        StockTrade foundTrade = null;
        
        int index = StockTrade.generateHashFromSymbol(symbol);
        
        MapEntry entry = this.hashMap[index];
        if (entry != null && entry.getValue() != null) {
            
            StockTradeNode nextNode = entry.getValue();
            boolean found = false;
            
            // traverse the linkedList stored at this index until the matching 
            // stockTrade is found, or we reach the end of the list
            while (nextNode != null && found != true) {
                StockTrade trade = nextNode.getStockTrade();
                if (trade.getSymbol().equals(symbol)) { 
                    // matching value was found - exit loop
                    foundTrade = trade;
                    found = true;
                } else {
                    // no match - move on to next element in linkedList
                    nextNode = nextNode.getNextNode();
                }
            }
        }
        
        return foundTrade;
    }
}
