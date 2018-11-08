/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Iterator;


/**
 *
 * @author katefrett
 */
public class PrintImpl {
    private static final DecimalFormat DF = new DecimalFormat("#.00");
    
    private FundManagerNode fundManagerNode;
    private LinkedList<StockTrade> stockTrades;
    private int stockTradeCount;

    /**
     * Default parameterless constructor
     */
    public PrintImpl() {
    }
    
    /**
     * Generates the report based upon the logged FundManagers and StockTrades 
     * passed in. The report will be written to the location specified in 
     * OUTPUT_FILENAME.
     * 
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl 
     */
    public void generateReport(FundManagerLogImpl fundManagerLogImpl, 
            StockTradeLogImpl stockTradeLogImpl,
            String outputFilename) {
        
        this.fundManagerNode = fundManagerLogImpl.getHead();
        this.stockTrades = stockTradeLogImpl.getStockTradeLog();
        this.stockTradeCount = stockTradeLogImpl.getNumStockTrades();
        
        String licenseNumber;
        
        int numberOfTradesForMgr = 0;
        double valueOfTradesForMgr = 0d;
        
        try {
            PrintWriter writer = new PrintWriter(
                    new FileWriter(outputFilename));
            
            System.out.println("Creating report...");
            
            while(this.fundManagerNode != null) {
                FundManager mgr = this.fundManagerNode.getFundManager();
                licenseNumber = mgr.getLicenseNumber();
                // write fund manager details
                writer.printf("%-10s %s, %s\n\n", 
                        mgr.getLicenseNumber(),
                        mgr.getLastName(), 
                        mgr.getFirstName());

                Iterator<StockTrade> iter = this.stockTrades.iterator();
                while(iter.hasNext()) {
                    StockTrade trade = iter.next();
                    if (trade != null && trade.getLicenseNumber().equals(licenseNumber)) {
                        // write the details of the trade
                        writer.printf("       %-10s   %-12s   %-10d   %-3s\n\n", 
                                trade.getSymbol(),
                                DF.format(trade.getPricePerShare()),
                                trade.getNumberOfShares(),
                                trade.isTaxable() ? "YES" : "NO");

                        // add this trade to the trade tallies for this mgr
                        numberOfTradesForMgr++;
                        valueOfTradesForMgr += 
                                StockTradeLogImpl.getStockTradeValue(trade);
                    }
                }

                // write summary details for mgr
                writer.printf("     Number of StockTrade Listings for "
                        + "FundManager: %d\n", numberOfTradesForMgr);
                writer.printf("     Total sales value of StockTrade "
                        + "Listings for FundManager %s:  $ %s\n\n", 
                        licenseNumber, DF.format(valueOfTradesForMgr));

                // reset values before next iteration of loop
                numberOfTradesForMgr = 0;
                valueOfTradesForMgr = 0d;
                
                // move on to the next FundManager 
                this.fundManagerNode = this.fundManagerNode.getNextNode();
            }
            
            // write summary details for report
            writer.printf("Total Number of StockTrade Listings for ALL "
                    + "FundManagers = %d\n", this.stockTradeCount);
            writer.printf("Total sales value of StockTrade Listings for ALL "
                    + "FundManagers = $ %s\n", 
                    DF.format(stockTradeLogImpl.totalStockTradeValue()));
            
            writer.close();
            
            System.out.printf("Report is located in file: %s\n", outputFilename);
        } catch (IOException ioe) {
            System.out.printf("Error writing to output file %s\n",
                    outputFilename);
        }
    }
}
