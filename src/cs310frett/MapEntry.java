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
public class MapEntry {
    private int key;
    private StockTradeNode value;

    /**
     * Fully parameterized constructor
     */
    public MapEntry(int key, StockTradeNode value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * Default parameterless constructor
     */
    public MapEntry() {}

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public StockTradeNode getValue() {
        return value;
    }

    public void setValue(StockTradeNode value) {
        this.value = value;
    }
    
    
}
