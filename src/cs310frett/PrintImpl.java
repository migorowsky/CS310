/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


/**
 *
 * @author katefrett
 */
public class PrintImpl {
//    private static final DecimalFormat DF = new DecimalFormat("#.00");
    
    private FundManagerLogImpl fundManagerLogImpl;
    private StockTradeLogImpl stockTradeLogImpl;

    /**
     * Default parameterless constructor
     */
    public PrintImpl() {
    }
    
    /**
     * constructor that sets the private member variables for the fundManager 
     * and stockTrade logs
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl 
     */
    public PrintImpl(FundManagerLogImpl fundManagerLogImpl, 
            StockTradeLogImpl stockTradeLogImpl) {
        this.fundManagerLogImpl = fundManagerLogImpl;
        this.stockTradeLogImpl = stockTradeLogImpl;
    }
    
    /**
     * Generates the report based upon the logged FundManagers and StockTrades 
     * passed in. The report will be written to the location specified in 
     * OUTPUT_FILENAME.
     * 
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl 
     */
    public void generateReport(String inputFileName,
            String outputFilename) {
        
        try {
            boolean inputFileFound = true;
            
            Scanner fileScanner = null;

            try {
                File dataFile = new File(inputFileName);
                fileScanner = new Scanner(dataFile);
            } catch (FileNotFoundException e) {
                System.out.println("File not found exception for file " + e + "\n");
                inputFileFound = false;
            }

            if (inputFileFound) {
                PrintWriter writer = new PrintWriter(
                    new FileWriter(outputFilename));
            
                System.out.println("Creating report...");
            
                String nextLine;
                while(fileScanner.hasNextLine()) {
                    nextLine = fileScanner.nextLine();

                    if (!nextLine.equals("")) {
                        String [] lineValues = nextLine.split(" ");
                        writeTaxableTradeDetailsToFile(writer, lineValues);
                    } 
                }

                writer.close();
                fileScanner.close();
            }   
        } catch (IOException ioe) {
            System.out.printf("Error writing to output file %s\n",
                    outputFilename);
        }   
    }
        
    private void writeTaxableTradeDetailsToFile(PrintWriter writer, 
            String[] input) {
        
        FundManager mgr = this.fundManagerLogImpl.find(input[0]);
        if (mgr != null) {
            writer.printf("FundManager %s, %s %s\n", 
                    mgr.getLicenseNumber(),
                    mgr.getFirstName(), 
                    mgr.getLastName());

            StockTrade trade = null;
            for (int i = 1; i < input.length; i++ ){
                trade = this.stockTradeLogImpl.find(input[i]);
                if (trade != null) {
                    writer.printf("     StockTrade %s is %s\n", 
                        trade.getSymbol(),
                        trade.isTaxable() ? "TAXABLE" : "NOT TAXABLE");
                }
            }        
        }
    }
    
//    public void generateGokartUsageReport(GoKartUsageImpl gokartUsage,
//            GoKartStackImpl basicGokartStack,
//            GoKartStackImpl racingGokartStack,
//            BrokerQueueImpl topBrokerQueue,
//            BrokerQueueImpl standardBrokerQueue,
//            String outputFilename) {
//        
//        try {
//            PrintWriter writer = new PrintWriter(
//                    new FileWriter(outputFilename));
//            
//            System.out.println("Creating gokart usage report...");
//            
//            writer.println("GO-KART USAGE REPORT");
//            
//            // populate usage section
//            Iterator iter = gokartUsage.getGoKartAssignments()
//                    .entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry kvPair = (Map.Entry)iter.next();
//                int cartNumber = (Integer)kvPair.getKey();
//                FundManager mgr = (FundManager)kvPair.getValue();
//                writer.printf("%s %s is using go-kart number %d\n", 
//                        mgr.getFirstName(), 
//                        mgr.getLastName(), 
//                        cartNumber);
//            }
//            
//            // populate Available Gokarts section
//            writer.println("AVAILABLE GO-KARTS");
//            populateAvailableGokartsSection("basic", basicGokartStack, writer);
//            populateAvailableGokartsSection("racing", racingGokartStack, writer);
//            
//            // populate Top Broker queue section
//            populateBrokerQueueSection("top", topBrokerQueue, writer);
//
//            // populate Standard Broker queue section
//            populateBrokerQueueSection("standard", standardBrokerQueue, writer);
//            
//            writer.close();
//            
//            System.out.printf("Gokart Usage Report is located in file: %s\n\n", outputFilename);
//        } catch (IOException ioe) {
//            System.out.printf("Error writing to output file %s\n\n",
//                    outputFilename);
//        }
//    }
//    
//    /**
//     * Prints the available gokart section to the file for a given gokart type.
//     * @param type
//     * @param gokartStack
//     * @param writer 
//     */
//    private void populateAvailableGokartsSection(String type, 
//            GoKartStackImpl gokartStack, PrintWriter writer) {
//        writer.printf("   %s GO-KARTS\n", type.toUpperCase());
//        if (gokartStack.isEmpty()) {
//            writer.printf("      No %s go-karts are available\n", 
//                    type.toLowerCase());
//        } else {
//            for (int i = gokartStack.getTop(); i >= 0; i--) {
//                int kartNumber = gokartStack.getKartNumberAtIndex(i);
//                writer.printf("      %s go-kart number %d is "
//                        + "available\n", toTitleCase(type), kartNumber);
//            }
//        }
//    }
//    
//    /**
//     * Returns the input string formatted into title case, with the first 
//     * character capitalized.
//     * @param input
//     * @return 
//     */
//    private String toTitleCase(String input) {
//        String output = input;
//        if (!input.equals(null) && !input.equals("")) {
//            StringBuilder sb = new StringBuilder();
//            boolean convertNext = true;
//            for (char c : input.toCharArray()) {
//                if (convertNext) {
//                    sb.append(Character.toUpperCase(c));
//                } else {
//                    sb.append(Character.toLowerCase(c));
//                }
//                convertNext = false;
//            }
//            output = sb.toString();
//        }
//        
//        return output;
//    }
//    
//    /**
//     * Prints the broker queue details for the provided type to the file.
//     * @param type
//     * @param brokerQueue
//     * @param writer 
//     */
//    private void populateBrokerQueueSection(String type, 
//            BrokerQueueImpl brokerQueue, PrintWriter writer) {
//        writer.printf("%s BROKER QUEUE\n", type.toUpperCase());
//        if (brokerQueue.getFrontNode() == null) {
//            writer.printf("There are no %s brokers waiting\n", 
//                    type.toLowerCase());
//        } else {
//            FundManagerNode fundManagerNode = brokerQueue.getFrontNode();
//            while(fundManagerNode != null) {
//                FundManager mgr = fundManagerNode.getFundManager();
//                writer.printf("%s %s is waiting\n", 
//                        mgr.getFirstName(), 
//                        mgr.getLastName());
//
//                // move on to the next FundManager 
//                fundManagerNode = fundManagerNode.getNextNode();
//            }
//        }
//    }
}
