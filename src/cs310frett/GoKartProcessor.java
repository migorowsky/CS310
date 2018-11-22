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
public class GoKartProcessor {
    
//    private static final String GOKART_FILE_1 = "./input/gokartInfo1.txt";
//    private static final String GOKART_FILE_2 = "./input/gokartInfo2.txt";
//    private static final String GOKART_USAGE_REPORT_FILENAME = 
//            "./output/gokartUsageReport.txt";
//    
//    private static final String REQUEST_GOKART = "REQUEST";
//    private static final String RETURN_GOKART = "RETURN";
//    
//    private static final int[] BASIC_GO_KART_NUMBERS = new int[] {1, 2, 3, 4};
//    private static final int[] RACING_GO_KART_NUMBERS = new int[] {5, 6, 7};
//    private static final double TOP_BROKER_LOWER_LIMIT = 5000000d;
//    
//        private static GoKartStackImpl basicGokartStack = new GoKartStackImpl();
//    private static GoKartStackImpl racingGokartStack = new GoKartStackImpl();
//    
//    private static GoKartUsageImpl gokartUsage = new GoKartUsageImpl();
//    private static BrokerQueueImpl topBrokerQueue = new BrokerQueueImpl();
//    private static BrokerQueueImpl standardBrokerQueue = new BrokerQueueImpl();
//    
//    private static FundManagerLogImpl fundManagerLogImpl;
//    private static StockTradeLogImpl stockTradeLogImpl;
//    private static PrintImpl printImpl = new PrintImpl();
//    
//    /**
//     * Entry point - handles all processing of the gokart file
//     * @param fundManagerLog
//     * @param stockTradeLog 
//     */
//    public static void processGokartData(FundManagerLogImpl fundManagerLog, 
//            StockTradeLogImpl stockTradeLog) {
//        fundManagerLogImpl = fundManagerLog;
//        stockTradeLogImpl = stockTradeLog;
//        populateGokartStacks();
////        processGoKartFile(GOKART_FILE_1);
//        processGoKartFile(GOKART_FILE_2);
//        createGokartUsageReport();
//    }
//    
//    /**
//     * Instantiates the gokart stacks with numbered gokarts as defined by the 
//     * requirements
//     */
//    private static void populateGokartStacks() {
//        basicGokartStack.setGoKarts(BASIC_GO_KART_NUMBERS);
//        racingGokartStack.setGoKarts(RACING_GO_KART_NUMBERS);
//    }
//    
//    /**
//     * Generates the gokart usage report
//     */
//    private static void createGokartUsageReport() {
//        printImpl.generateGokartUsageReport(gokartUsage, 
//                basicGokartStack, 
//                racingGokartStack,
//                topBrokerQueue,
//                standardBrokerQueue,
//                GOKART_USAGE_REPORT_FILENAME);
//    }
//    
//    /**
//     * Reads and processes the contents of gokart input file
//     */
//    public static void processGoKartFile(String fileName) {
//        boolean fileFound = true;
//        File dataFile = new File(fileName);
//        Scanner fileScanner = null;
//        try {
//            fileScanner = new Scanner(dataFile);
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found exception for file " + e + "\n");
//            fileFound = false;
//        }
//        
//        if (fileFound) {
//            System.out.printf("Reading data from file %s\n", fileName);
//            
//            String nextLine;
//            int lineCounter = 0;
//            while(fileScanner.hasNextLine()) {
//                nextLine = fileScanner.nextLine();
//
//                if (!nextLine.equals("")) {
//                    String [] lineValues = nextLine.split(" ");
//                    if (lineValues[0].equals(REQUEST_GOKART)) {
//                        String licenseNumber = lineValues[1];
//                        processGokartRequest(licenseNumber);
//                    } else if (lineValues[0].equals(RETURN_GOKART)) {
//                        String licenseNumber = lineValues[1];
//                        processGokartReturn(licenseNumber);
//                    } else {
//                        System.out.printf("'%s' is an unknown line type to "
//                                    + "process.\n", lineValues[0]);
//                    }
//                } 
//                lineCounter++;
//            }
//
//            fileScanner.close();
//            
//            System.out.printf("Done reading file " + fileName + ". %d lines read.\n\n", 
//                    lineCounter);
//        }
//    }
//    
//    /** 
//     * Handles a gokart request by assigning the broker to a gokart or 
//     * enqueueing them.
//     * @param licenseNumber 
//     */
//    private static void processGokartRequest(String licenseNumber) {
//        FundManager fundManager = fundManagerLogImpl.getFundManager(licenseNumber);
//        
//        if (fundManager != null) {
//            double value = stockTradeLogImpl.totalStockTradeValue(licenseNumber);
//            if (value >= TOP_BROKER_LOWER_LIMIT) {
//                handleTopBrokerGokartRequest(fundManager);
//            } else {
//                handleStandardBrokerGokartRequest(fundManager);
//            }
//        } else {
//            System.out.println("Unknown broker " + licenseNumber + " is not "
//                    + "allowed access to go-karts. Request ignored.");
//        }
//    }
//    
//    /**
//     * Assigns a top broker to a racing or basic gokart, or enqueues them if no 
//     * gokarts are available
//     * @param fundManager 
//     */
//    private static void handleTopBrokerGokartRequest(FundManager fundManager) {
//        int kartNumber = -1;
//                
//        // check racing stack first
//        if (!racingGokartStack.isEmpty()) {
//            kartNumber = racingGokartStack.pop();
//            gokartUsage.checkoutGoKart(kartNumber, fundManager);
//        } 
//        // check basic stack if no racing karts are available
//        else if(!basicGokartStack.isEmpty()) {
//            kartNumber = basicGokartStack.pop();
//            gokartUsage.checkoutGoKart(kartNumber, fundManager);
//        }
//        // put the broker in the queue if no basic karts are available
//        else {
//            topBrokerQueue.addToQueue(fundManager);
//        }
//
//        if (kartNumber >= 0) {
//            System.out.printf("Top broker %s %s has been assigned to %s "
//                    + "go-kart number %d\n", 
//                    fundManager.getFirstName(),
//                    fundManager.getLastName(),
//                    getKartType(kartNumber), 
//                    kartNumber);
//        } else {
//            System.out.printf("%s %s is waiting in top broker queue.\n",
//                    fundManager.getFirstName(), 
//                    fundManager.getLastName());
//        }
//    }
//    
//    /**
//     * Assigns a standard broker to a basic gokart, or enqueues them if no basic 
//     * karts are available
//     * @param fundManager 
//     */
//    private static void handleStandardBrokerGokartRequest(FundManager fundManager) {
//        int kartNumber = -1;
//                
//        // check basic stack only
//        if(!basicGokartStack.isEmpty()) {
//            kartNumber = basicGokartStack.pop();
//            gokartUsage.checkoutGoKart(kartNumber, fundManager);
//        }
//        // put the broker in the queue if no basic karts are available
//        else {
//            standardBrokerQueue.addToQueue(fundManager);
//        }
//
//        if (kartNumber >= 0) {
//            System.out.printf("Standard broker %s %s has been assigned to %s "
//                    + "go-kart number %d\n", 
//                    fundManager.getFirstName(),
//                    fundManager.getLastName(),
//                    getKartType(kartNumber), 
//                    kartNumber);
//        } else {
//            System.out.printf("%s %s is waiting in standard broker queue.\n",
//                    fundManager.getFirstName(), 
//                    fundManager.getLastName());
//        }
//    }
//    
//    /**
//     * Returns a gokart for a broker by licenseNumber, and then pulls a broker
//     * from the queue and assigns them to the newly available kart.
//     * @param licenseNumber 
//     */
//    private static void processGokartReturn(String licenseNumber) {
//        FundManager fundManager = fundManagerLogImpl.getFundManager(licenseNumber);
//        
//        if (fundManager != null) {
//            // Get the gokart that the fundManager was using
//            int kartNumber = gokartUsage.getGokartNumber(fundManager);
//            if (kartNumber < 0) {
//                System.out.printf("Broker %s has not been assigned to a gokart"
//                        + " yet, so they can't return the gokart\n", 
//                        licenseNumber);
//            } else {
//                String kartType = getKartType(kartNumber);
//
//                if (kartType.equals("unknown")) {
//                    // we should never end up here
//                    System.out.printf("Something went really wrong, since "
//                            + "broker %s %s is trying to return an unknown gokart "
//                            + "type.", fundManager.getFirstName(), 
//                            fundManager.getLastName());
//                }
//                // return the gokart and pull someone from the queue and assign 
//                // them to the newly available kart
//                else {
//                    if (kartType.equals("racing")) {
//                        racingGokartStack.push(kartNumber);
//                    } 
//                    else if (kartType.equals("basic")) {
//                        basicGokartStack.push(kartNumber);
//                    }
//                    gokartUsage.returnGoKart(kartNumber);
//                    System.out.printf("%s %s has returned go-kart number %d\n", 
//                            fundManager.getFirstName(), 
//                            fundManager.getLastName(), kartNumber);
//                    assignBrokerFromQueueToKart(kartType);
//                } 
//            }
//        }
//    }
//    
//    /**
//     * Assigns a broker in the queue to a newly-available kart
//     * @param kartType 
//     */
//    private static void assignBrokerFromQueueToKart(String kartType) {
//        // only check the top broker queue for racing karts
//        if (kartType.equals("racing") && !topBrokerQueue.isEmpty()) {
//            FundManager mgr = topBrokerQueue.frontNode.getFundManager();
//            int kartNumber = racingGokartStack.pop();
//            gokartUsage.checkoutGoKart(kartNumber, mgr);
//        } 
//        
//        else if (kartType.equals("basic")){
//            // check the top broker queue first, since they have priority
//            if (!topBrokerQueue.isEmpty()) {
//                FundManager mgr = topBrokerQueue.frontNode.getFundManager();
//                int kartNumber = basicGokartStack.pop();
//                gokartUsage.checkoutGoKart(kartNumber, mgr);
//            } 
//            // if no top brokers are queued, look at the standard brokers queue
//            else if (!standardBrokerQueue.isEmpty()) {
//                FundManager mgr = standardBrokerQueue.frontNode.getFundManager();
//                int kartNumber = basicGokartStack.pop();
//                gokartUsage.checkoutGoKart(kartNumber, mgr);
//            }
//        }        
//    }
//     
//    /**
//     * Determines whether the goKartNumber corresponds to a basic or racing
//     * gokart
//     * @param kartNumber
//     * @return 
//     */
//    private static String getKartType(int kartNumber) {
//        String kartType = "unknown";
//        boolean found = false;
//        
//        int i = 0;
//        while (!found && i < BASIC_GO_KART_NUMBERS.length) {
//            if (kartNumber == BASIC_GO_KART_NUMBERS[i]) {
//                found = true;
//            }
//            i++;
//        }
//        
//        if (found) {
//            kartType = "basic";
//        } else {
//            i = 0;
//            while (!found && i < RACING_GO_KART_NUMBERS.length) {
//                if (kartNumber == RACING_GO_KART_NUMBERS[i]) {
//                    found = true;
//                }
//                i++;
//            }
//            
//            if (found) {
//                kartType = "racing";
//            }
//        }
//        
//        return kartType;
//    }
}
