/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author katefrett
 */
public class StockTradeLogImpl {
    private TreeMap<String, StockTrade> treeMap;

    /**
     * Default parameterless constructor
     */
    public StockTradeLogImpl() {
        this.treeMap = new TreeMap<>();
    }

    /** 
     * Data accessor for the private member variable treeMap
     * @return 
     */
    public TreeMap<String, StockTrade> getStockTradeLog() {
        return treeMap;
    }
    
    /**
     * Traverses the treeMap in an "in-order" manner and outputs 
     * the contents of the treeMap to stdout
     */
    public void traverseDisplay() {
        System.out.println("\nStockTrade List:");
        
        // according to the internet, inorder traversal is the only method of
        // traversal that is allowed for the Java TreeMap data structure, and 
        // it is the default iteration method
        // reference: 
        // https://stackoverflow.com/questions/47386727/traversals-for-java-treemap
        
        for (Map.Entry<String, StockTrade> e : this.treeMap.entrySet()) {
            System.out.println("   " + e.getKey());
        }   
    }
    
    /**
     * add StockTrade to treeMap 
     * @param stockTrade
     * @return 
     */
    public void add(StockTrade stockTrade) {
        this.treeMap.put(stockTrade.getSymbol(), stockTrade);
    }
    
    /**
     * Find StockTrade in treeMap by the stock symbol. If trade is not present 
     * in the treeMap, null is returned.
     * @param symbol
     * @return 
     */
    public StockTrade find(String symbol) {
        return this.treeMap.get(symbol);
    }
}
