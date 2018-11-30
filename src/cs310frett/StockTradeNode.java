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
public class StockTradeNode {
    private StockTrade stockTrade;
    private StockTradeNode nextNode;

    /**
     * Default parameterless constructor
     */
    public StockTradeNode() {
        this(null, null);
    }
    
    /**
     * Constructor which calls the constructor that accepts a stockTrade 
     * and nextNode, providing null for the nextNode parameter
     * 
     * @param stockTrade 
     */
    public StockTradeNode(StockTrade stockTrade) {
        this(stockTrade, null);
    }
    
    /**
     * Constructor that accepts all the values to populate all the private 
     * member variables
     * 
     * @param stockTrade
     * @param nextNode 
     */
    public StockTradeNode(StockTrade stockTrade, StockTradeNode nextNode) {
        this.stockTrade = stockTrade;
        this.nextNode = nextNode;
    }

    public StockTrade getStockTrade() {
        return stockTrade;
    }

    public void setStockTrade(StockTrade stockTrade) {
        this.stockTrade = stockTrade;
    }

    public StockTradeNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(StockTradeNode nextNode) {
        this.nextNode = nextNode;
    }
}
