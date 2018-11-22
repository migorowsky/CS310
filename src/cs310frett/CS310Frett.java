/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author katefrett
 */
public class CS310Frett {
    private static final String INPUT_FILENAME = "./input/assn5input1.txt";
    private static final String SALES_REPORT_INPUT_FILENAME = 
            "./input/FundManagerRequests.txt";
    private static final String SALES_REPORT_OUTPUT_FILENAME = 
            "./output/assn5salesReport.txt";
    
    private static final String BROKER_LINE_IDENTIFIER = "BROKER";
    private static final String ADD_BROKER = "ADD";
    private static final String DELETE_BROKER = "DEL";
    
    private static final String TRADE_LINE_IDENTIFIER = "TRADE";
    private static final String BUY_SHARES = "BUY";
    private static final String SELL_SHARES = "SELL";
    
    private static FundManagerLogImpl fundManagerLogImpl = 
            new FundManagerLogImpl();
    private static StockTradeLogImpl stockTradeLogImpl = 
            new StockTradeLogImpl();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        processInputFile();
        fundManagerLogImpl.displayHash();
        stockTradeLogImpl.displayHash();
        createReport("clean");
    }
    
    /**
     * Reads and processes the contents of input file
     */
    public static void processInputFile() {
        boolean fileFound = true;
        File dataFile = new File(INPUT_FILENAME);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception for file " + e + "\n");
            fileFound = false;
        }
        
        if (fileFound) {
            System.out.printf("Reading data from file %s\n", INPUT_FILENAME);
            
            String nextLine;
            int lineCounter = 0;
            while(fileScanner.hasNextLine()) {
                nextLine = fileScanner.nextLine();

                if (!nextLine.equals("")) {
                    String [] lineValues = nextLine.split(",");
                    if (lineValues[0].equals(BROKER_LINE_IDENTIFIER)) {
                        processBrokerLine(lineValues);
                    } else if (lineValues[0].equals(TRADE_LINE_IDENTIFIER)) {
                        processTradeLine(lineValues);
                    } else {
                        System.out.printf("'%s' is an unknown line type to "
                                    + "process.\n", lineValues[0]);
                    }
                } 
                lineCounter++;
            }

            fileScanner.close();
            
            System.out.printf("Done reading file. %d lines read.\n\n", 
                    lineCounter);
        }
    }
    
    /**
     * Processes a string array representing a line of text from an input file, 
     * which contains data for a broker (FundManager)
     * 
     * @param lineValues 
     */
    private static void processBrokerLine(String[] lineValues) {
        String brokerOperation = lineValues[1];
        // handle unknown broker actions
        if (!brokerOperation.equals(ADD_BROKER) 
                && !brokerOperation.equals(DELETE_BROKER)) {
            System.out.printf("'%s' is an unknown operation to "
                    + "apply to a broker.\n", brokerOperation);
        } else {
            // instantiate the broker
            FundManager fundManager = new FundManager(lineValues);
            
            // Add/Remove the broker to the log
            if (lineValues[1].equals(ADD_BROKER)) {
                addFundManager(fundManager);
            } 
        }
    }
    
    /**
     * Add a FundManager to the log.  Per the week 5 requirements, there is no
     * need to validate the input.
     * 
     * @param fundManager 
     */
    private static void addFundManager(FundManager fundManager) {
        String licenseNumber = fundManager.getLicenseNumber();
        if (fundManagerLogImpl.find(licenseNumber) == null) {
            fundManagerLogImpl.add(fundManager);
            String logMessage = String.format("  ADDED: FundManager with license "
                    + "%s", licenseNumber);
            System.out.println(logMessage);
        } else {
            System.out.printf("    ADD ERROR: FundManager with licenseNumber '%s' is not "
                    + "unique and will not be added to the log.\n ",
                    licenseNumber);
        }
    }
    
    /**
     * Processes a string array representing a line of text from an input file, 
     * which contains data for a trade (StockTrade)
     * 
     * @param lineValues 
     */
    private static void processTradeLine(String[] lineValues) {
        String tradeOperation = lineValues[1];
        if (!tradeOperation.equals(BUY_SHARES) 
                && !tradeOperation.equals(SELL_SHARES)) {
            System.out.printf("'%s' is an unknown operation to "
                    + "apply to a trade.\n", tradeOperation);
        } else {
            // instantiate the trade
            StockTrade stockTrade = new StockTrade(lineValues);

            // add the trade to the log
            if (tradeOperation.equals(BUY_SHARES)) {
                buyStock(stockTrade);
            } 
        }
    }
    
    /** 
     * Add a StockTrade to the log if it is not already present in the log.
     */
    private static void buyStock(StockTrade stockTrade) {
        String licenseNumber = stockTrade.getLicenseNumber();
        String symbol = stockTrade.getSymbol();
        
        boolean licenseNumberExists = 
                fundManagerLogImpl.find(licenseNumber) != null;
        boolean stockSymbolExists = 
                stockTradeLogImpl.find(symbol) != null;
        
        if (licenseNumberExists && !stockSymbolExists) {
            // LicenseNumber is present in the FundManagerLog, and stock symbol 
            // is not already in the StockTradeLog - proceed with adding stock 
            // trade to log
            stockTradeLogImpl.add(stockTrade);
            String logMessage = String.format("  ADDED: StockTrade with symbol %s listed by "
                    + "FundManager %s", symbol, licenseNumber);
            System.out.println(logMessage);
        } else {
            if (!licenseNumberExists) {
                System.out.printf("    ADD ERROR: StockTrade with Stock symbol "
                        + "%s has FundManager with license %s,\n" 
                        + "               but there is no such FundManager "
                        + "license in the FundManager log.\n"
                        + "               StockTrade %s will NOT be added to "
                        + "StockTrade log.\n", 
                        symbol, licenseNumber, symbol);
            }
            if (stockSymbolExists) {
                System.out.printf("    ADD ERROR: StockTrade with Stock symbol "
                        + "%s is already present in the StockTrade log.\n"
                        + "               StockTrade %s will NOT be added to "
                        + "StockTrade log.\n", 
                        symbol, symbol);
            }   
        }
    }
    
    /**
     * Creates the report from the fundManagerLogImpl and StockTradeLogImpl in 
     * a specified location (file)
     * @param reportType 
     */
    private static void createReport(String reportType) {
        System.out.println("\nCreating " + reportType + " report...\n");
        
        PrintImpl printImpl = new PrintImpl(fundManagerLogImpl, 
                stockTradeLogImpl);
        
        printImpl.generateReport(SALES_REPORT_INPUT_FILENAME, 
                SALES_REPORT_OUTPUT_FILENAME);
        
    }
}
