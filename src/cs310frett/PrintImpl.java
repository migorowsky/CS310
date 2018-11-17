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
import java.util.Map;


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
    
    public void generateGokartUsageReport(GoKartUsageImpl gokartUsage,
            GoKartStackImpl basicGokartStack,
            GoKartStackImpl racingGokartStack,
            BrokerQueueImpl topBrokerQueue,
            BrokerQueueImpl standardBrokerQueue,
            String outputFilename) {
        
        try {
            PrintWriter writer = new PrintWriter(
                    new FileWriter(outputFilename));
            
            System.out.println("Creating gokart usage report...");
            
            writer.println("GO-KART USAGE REPORT");
            
            // populate usage section
            Iterator iter = gokartUsage.getGoKartAssignments()
                    .entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry kvPair = (Map.Entry)iter.next();
                int cartNumber = (Integer)kvPair.getKey();
                FundManager mgr = (FundManager)kvPair.getValue();
                writer.printf("%s %s is using go-kart number %d\n", 
                        mgr.getFirstName(), 
                        mgr.getLastName(), 
                        cartNumber);
            }
            
            // populate Available Gokarts section
            writer.println("AVAILABLE GO-KARTS");
            populateAvailableGokartsSection("basic", basicGokartStack, writer);
            populateAvailableGokartsSection("racing", racingGokartStack, writer);
            
            // populate Top Broker queue section
            populateBrokerQueueSection("top", topBrokerQueue, writer);

            // populate Standard Broker queue section
            populateBrokerQueueSection("standard", standardBrokerQueue, writer);
            
            writer.close();
            
            System.out.printf("Gokart Usage Report is located in file: %s\n\n", outputFilename);
        } catch (IOException ioe) {
            System.out.printf("Error writing to output file %s\n\n",
                    outputFilename);
        }
    }
    
    /**
     * Prints the available gokart section to the file for a given gokart type.
     * @param type
     * @param gokartStack
     * @param writer 
     */
    private void populateAvailableGokartsSection(String type, 
            GoKartStackImpl gokartStack, PrintWriter writer) {
        writer.printf("   %s GO-KARTS\n", type.toUpperCase());
        if (gokartStack.isEmpty()) {
            writer.printf("      No %s go-karts are available\n", 
                    type.toLowerCase());
        } else {
            for (int i = gokartStack.getTop(); i >= 0; i--) {
                int kartNumber = gokartStack.getKartNumberAtIndex(i);
                writer.printf("      %s go-kart number %d is "
                        + "available\n", toTitleCase(type), kartNumber);
            }
        }
    }
    
    /**
     * Returns the input string formatted into title case, with the first 
     * character capitalized.
     * @param input
     * @return 
     */
    private String toTitleCase(String input) {
        String output = input;
        if (!input.equals(null) && !input.equals("")) {
            StringBuilder sb = new StringBuilder();
            boolean convertNext = true;
            for (char c : input.toCharArray()) {
                if (convertNext) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
                convertNext = false;
            }
            output = sb.toString();
        }
        
        return output;
    }
    
    /**
     * Prints the broker queue details for the provided type to the file.
     * @param type
     * @param brokerQueue
     * @param writer 
     */
    private void populateBrokerQueueSection(String type, 
            BrokerQueueImpl brokerQueue, PrintWriter writer) {
        writer.printf("%s BROKER QUEUE\n", type.toUpperCase());
        if (brokerQueue.getFrontNode() == null) {
            writer.printf("There are no %s brokers waiting\n", 
                    type.toLowerCase());
        } else {
            FundManagerNode fundManagerNode = brokerQueue.getFrontNode();
            while(fundManagerNode != null) {
                FundManager mgr = fundManagerNode.getFundManager();
                writer.printf("%s %s is waiting\n", 
                        mgr.getFirstName(), 
                        mgr.getLastName());

                // move on to the next FundManager 
                fundManagerNode = fundManagerNode.getNextNode();
            }
        }
    }
}
