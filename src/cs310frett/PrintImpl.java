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
}
