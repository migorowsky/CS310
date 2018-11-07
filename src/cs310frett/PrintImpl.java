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
import java.util.ArrayList;


/**
 *
 * @author katefrett
 */
public class PrintImpl {
    private static final String OUTPUT_FILENAME = "./output/assn2report.txt";
    private static final DecimalFormat DF = new DecimalFormat("#.00");
    
    private ArrayList<FundManager> fundManagers;
    private StockTrade[] stockTrades;
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
            StockTradeLogImpl stockTradeLogImpl) {
        
        this.fundManagers = fundManagerLogImpl.getFundManagerLog();
        this.stockTrades = stockTradeLogImpl.getStockTradeArray();
        this.stockTradeCount = stockTradeLogImpl.getNumStockTrades();
        
        String licenseNumber;
        
        int numberOfTradesForMgr = 0;
        double valueOfTradesForMgr = 0d;
        
        try {
            PrintWriter writer = new PrintWriter(
                    new FileWriter(OUTPUT_FILENAME));

            
            System.out.println("Creating report...");
            
            for (FundManager mgr : this.fundManagers) {
                licenseNumber = mgr.getLicenseNumber();
                // write fund manager details
                writer.printf("%-10s %s, %s\n\n", 
                        mgr.getLicenseNumber(),
                        mgr.getLastName(), 
                        mgr.getFirstName());

                for (StockTrade trade : this.stockTrades) {
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
            }
            
            // write summary details for report
            writer.printf("Total Number of StockTrade Listings for ALL "
                    + "FundManagers = %d\n", this.stockTradeCount);
            writer.printf("Total sales value of StockTrade Listings for ALL "
                    + "FundManagers = $ %s\n", 
                    DF.format(stockTradeLogImpl.totalStockTradeValue()));
            
            writer.close();
            
            System.out.printf("Report is located in file: %s\n", OUTPUT_FILENAME);
        } catch (IOException ioe) {
            System.out.printf("Error writing to output file %s\n",
                    OUTPUT_FILENAME);
        }
    }
}
