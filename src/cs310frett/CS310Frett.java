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
    private static final String INPUT_FILENAME = "./input/assn4input1.txt";
    private static final String CLEAN_REPORT_FILENAME = 
            "./output/assn4cleanReport.txt";
    
    private static final String BROKER_LINE_IDENTIFIER = "BROKER";
    private static final String ADD_BROKER = "ADD";
    private static final String DELETE_BROKER = "DEL";
    
    private static final String TRADE_LINE_IDENTIFIER = "TRADE";
    private static final String BUY_SHARES = "BUY";
    private static final String SELL_SHARES = "SELL";
    
    private static final String REGARDLESS_OF_ERRORS = ", regardless of data errors.";
    
    private static FundManagerLogImpl fundManagerLogImpl = 
            new FundManagerLogImpl();
    private static StockTradeLogImpl stockTradeLogImpl = 
            new StockTradeLogImpl();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        processInputFile();
        printAuditTrail();
        cleanupLogs();
        createReport("clean");
    }
    
    /**
     * Prints the contents of the fundManager log and stockTrade log to stdout
     */
    private static void printAuditTrail() {
        fundManagerLogImpl.traverseDisplay();
        stockTradeLogImpl.traverseDisplay();
    }
    
    /**
     * Invokes the cleanUp methods on the fundManager log and stockTrade log
     */
    private static void cleanupLogs() {
        System.out.println("\nCleaning up fundManager and stockTrade logs...");
        fundManagerLogImpl.cleanUp(stockTradeLogImpl);
        stockTradeLogImpl.cleanUp();
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
            
            // validate properties on the broker
            boolean hasErrors = validateFundManager(fundManager);
            
            // Add/Remove the broker to the log
            if (lineValues[1].equals(ADD_BROKER)) {
                addFundManager(fundManager, hasErrors);
            } else if (lineValues[1].equals(DELETE_BROKER)) {
                removeFundManager(fundManager, hasErrors);
            } 
        }
    }
    
    /**
     * Add a FundManager to the log if it is not already present in the log.
     * 
     * @param fundManager 
     */
    private static void addFundManager(FundManager fundManager, 
            boolean hasErrors) {
        String licenseNumber = fundManager.getLicenseNumber();
        if (fundManagerLogImpl.isLicenseUnique(licenseNumber)) {
            fundManagerLogImpl.addFundManager(fundManager);
            String logMessage = String.format("  ADDED: FundManager with license "
                    + "%s", licenseNumber);
            if (hasErrors) {
                logMessage += REGARDLESS_OF_ERRORS;
            }
            System.out.println(logMessage);
        } else {
            System.out.printf("    ADD ERROR: FundManager with licenseNumber '%s' is not "
                    + "unique and will not be added to the log.\n ",
                    licenseNumber);
        }
    }
    
    /**
     * Remove a FundManager from the log if it present in the log.
     * 
     * @param fundManager 
     */
    private static void removeFundManager(FundManager fundManager, boolean hasErrors) {
        String licenseNumber = fundManager.getLicenseNumber();
        if (!fundManagerLogImpl.isLicenseUnique(
                    licenseNumber)) {
            fundManagerLogImpl.removeFundManager(licenseNumber);
            String logMessage = String.format("  DELETED: FundManager with "
                    + "licenseNumber %s has been removed from the FundManager "
                    + "log", licenseNumber);
            if (hasErrors) {
                logMessage += REGARDLESS_OF_ERRORS;
            }
            System.out.println(logMessage);
            System.out.println("           All FundManager's stocks will also"
                    + " be removed from the StockTrade log.");
            stockTradeLogImpl.removeStockTradesByFundManager(licenseNumber);
        } else {
            System.out.printf("    DEL ERROR: FundManager with license %s is "
                    + "not present in the FundManager log, so it cannot be "
                    + "deleted.\n", licenseNumber);
        }
    }

    /**
     * Validates the attributes set on the fundManager.
     * 
     * @param fundManager 
     */
    private static boolean validateFundManager(FundManager fundManager) {
        boolean hasErrors = false;
        
        if (!fundManager.licenseNumberIsValid() 
                || !fundManager.departmentNumberIsValid()) {
            
            hasErrors = true;
            String baseError = getBaseMgrValidationError(
                    fundManager.getLicenseNumber());
            
            if (!fundManager.licenseNumberIsValid()) {
                System.out.printf("%s license\n", baseError);
            }
            if (!fundManager.departmentNumberIsValid()) {
                System.out.printf("%s department %s\n", 
                        baseError, fundManager.getDepartmentNumber());
            }
        } 
        
        return hasErrors;
    }
    
    /**
     * Generates the base FundManager validation error message
     * 
     * @param license
     * @return 
     */
    private static String getBaseMgrValidationError(String license) {
        return String.format("    ERROR: FundManager with license %s has an "
                + "invalid", license);
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
            
            // validate attributes on the trade
            boolean hasErrors = validateStockTrade(stockTrade);

            // add/remove the trade to the log
            if (tradeOperation.equals(BUY_SHARES)) {
                buyStock(stockTrade, hasErrors);
            } else if (tradeOperation.equals(SELL_SHARES)) {
                sellStock(stockTrade, hasErrors);
            } 
        }
    }
    
    /** 
     * Add a StockTrade to the log if it is not already present in the log.
     */
    private static void buyStock(StockTrade stockTrade, boolean hasErrors) {
        String licenseNumber = stockTrade.getLicenseNumber();
        String symbol = stockTrade.getSymbol();
        
        boolean licenseNumberExists = 
                !fundManagerLogImpl.isLicenseUnique(licenseNumber);
        boolean stockSymbolExists = 
                !stockTradeLogImpl.isStockSymbolUnique(symbol);
        
        if (licenseNumberExists && !stockSymbolExists) {
            // LicenseNumber is present in the FundManagerLog, and stock symbol 
            // is not already in the StockTradeLog - proceed with adding stock 
            // trade to log
            stockTradeLogImpl.addStockTrade(stockTrade);
            String logMessage = String.format("  ADDED: StockTrade with symbol %s listed by "
                    + "FundManager %s", symbol, licenseNumber);
            if (hasErrors) {
                logMessage += REGARDLESS_OF_ERRORS;
            }
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
     * Add a StockTrade to the log if it is not already present in the log.
     */
    private static void sellStock(StockTrade stockTrade, boolean hasErrors) {
        String symbol = stockTrade.getSymbol();
        
        if (!stockTradeLogImpl.isStockSymbolUnique(symbol)) {
            // stockSymbol is present in log - proceed with deletion
            stockTradeLogImpl.removeStockTrade(symbol);
            String logMessage = String.format("  DELETED: StockTrade with symbol %s",
                    symbol);
            if (hasErrors) {
                logMessage += REGARDLESS_OF_ERRORS;
            }
            System.out.println(logMessage);
        } else {
            System.out.printf("    DEL ERROR: StockTrade with symbol %s is not "
                    + "present in the StockTrade log, so it cannot be deleted.",
                    symbol); 
        }
    }
    
    /**
     * Creates the report from the fundManagerLogImpl and StockTradeLogImpl in 
     * a specified location (file)
     * @param reportType 
     */
    private static void createReport(String reportType) {
        System.out.println("\nCreating " + reportType + " report...\n");
        
        PrintImpl printImpl = new PrintImpl();
        
        printImpl.generateReport(fundManagerLogImpl, stockTradeLogImpl, 
                     CLEAN_REPORT_FILENAME);
        
    }
    
    /**
     * Validates the attributes set on the fundManager.
     * 
     * @param stockTrade 
     */
    private static boolean validateStockTrade(StockTrade stockTrade) {
        boolean hasErrors = false;
        if (!stockTrade.symbolIsValid() 
                || !stockTrade.pricePerShareIsValid() 
                || !stockTrade.numberOfSharesIsValid()) {
            
            hasErrors = true;
            String baseError = getBaseStockValidationError(
                    stockTrade.getSymbol());
            
            if (!stockTrade.symbolIsValid()) {
                System.out.printf("%s symbol.\n", baseError);
            }
            if (!stockTrade.pricePerShareIsValid()) {
                System.out.printf("%s price per share %f", 
                        baseError, stockTrade.getPricePerShare());
            }
            if (!stockTrade.numberOfSharesIsValid()) {
                System.out.printf("%s number of shares %d",
                        baseError, stockTrade.getNumberOfShares());
            }
        } 
        return hasErrors;
    }
    
    /**
     * Generates the base StockTrade validation error message
     * 
     * @param stockSymbol
     * @return 
     */
    private static String getBaseStockValidationError(String stockSymbol) {
        return String.format("    ERROR: StockTrade with Stock symbol %s "
                        + "has an invalid", stockSymbol);
    }
}
