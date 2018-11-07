/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310frett;

import java.util.Objects;

/**
 *
 * @author katefrett
 */
public class StockTrade {
    private static final double MAX_PRICE_PER_SHARE = 1000d;
    private static final int MAX_NUM_OF_SHARES = 100000;
    private static final String TAXABLE = "Y";

    private String symbol;
    private double pricePerShare;
    private int numberOfShares;
    private String licenseNumber;
    private boolean taxable;

    /**
     * Default parameterless constructor
     */
    public StockTrade() {}
    
    /**
     * Constructor which accepts an array of attributes and sets the 
     * properties on a StockTrade object from them.
     * 
     * @param attributes 
     */
    public StockTrade(String[] attributes) {
        this.setSymbol(attributes[2]);
        try {
            double pricePerShare = Double.parseDouble(attributes[3]);
            this.setPricePerShare(pricePerShare);
        } catch (NumberFormatException nfe) {
            System.out.printf("    ERROR: '%s' is an invalid price per share. "
                    + "Setting the price per share to 0 for StockTrade with "
                    + "symbol %s.\n", 
                    attributes[3], this.getSymbol());
            // this defaults to 0.  Putting this here to make it explicit.
            this.setPricePerShare(0d); 
        }
        try {
            int numberOfShares = Integer.parseInt(attributes[4]);
            this.setNumberOfShares(numberOfShares);
        } catch (NumberFormatException nfe) {
            System.out.printf("    ERROR: '%s' is an invalid number of shares. "
                    + "Setting number of shares to 0 for StockTrade with symbol "
                    + "%s.\n", 
                    attributes[4], this.getSymbol());
            // this defaults to 0.  Putting this here to make it explicit.
            this.setNumberOfShares(0); 
        }
        this.setLicenseNumber(attributes[5]);
        
        // protect against lines in the file with the last field missing
        if (attributes.length == 7) {
            this.setTaxable(attributes[6]);
        } else {
            // this defaults to false.  Putting this here to make it explicit.
            this.setTaxable(false); 
        }
    }
    
    /**
     * Fully parameterized constructor 
     * 
     * @param symbol
     * @param pricePerShare
     * @param numberOfShares
     * @param licenseNumber
     * @param taxable
     */
    public StockTrade(String symbol, double pricePerShare, int numberOfShares, String licenseNumber, boolean taxable) {
        this.symbol = symbol;
        this.pricePerShare = pricePerShare;
        this.numberOfShares = numberOfShares;
        this.licenseNumber = licenseNumber;
        this.taxable = taxable;
    }

    /**
     * Retrieve private member variable symbol
     * 
     * @return
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Update the value of the private member variable symbol
     * 
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Retrieve private member variable pricePerShare
     * 
     * @return
     */
    public double getPricePerShare() {
        return pricePerShare;
    }

    /**
     * Update the value of the private member variable symbol
     * 
     * @param pricePerShare
     */
    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    /**
     * Retrieve private member variable numberOfShares
     * 
     * @return
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Update the value of the private member variable symbol
     * 
     * @param numberOfShares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Retrieve private member variable licenseNumber
     * 
     * @return
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Update the value of the private member variable symbol
     * 
     * @param licenseNumber
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * Retrieve private member variable taxable
     * 
     * @return
     */
    public boolean isTaxable() {
        return taxable;
    }

    /**
     * Update the value of the private member variable symbol
     * 
     * @param taxable
     */
    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
    
    /**
     * Overload of setTaxable that accepts a string and sets the boolean 
     * value accordingly.
     * @param taxable 
     */
    public void setTaxable(String taxable) {
        if (taxable.equalsIgnoreCase(TAXABLE)) {
            this.taxable = true;
        } else {
            this.taxable = false;
        }
    }
    
    /**
     * Determine if the private member variable symbol is valid.  Length must be 
     * 3 or 4 characters, and all characters must be uppercase.
     * 
     * @return
     */
    public boolean symbolIsValid() {
        // handle null values
        if (this.symbol == null) {
            return false;
        }
        
        // validate that the symbol has the required number of characters
        if (this.symbol.length() < 3 || this.symbol.length() > 4) {
            return false;
        }
        
        // validate that the symbol contains only uppercase characters
        if (this.symbol.toUpperCase() != this.symbol) {
            return false;
        }
        
        // if the method has not already returned a value, all validation passes
        return true;
    }
    
    /**
     * Determine validity of the value of pricePerShare.  It is valid if the 
     * pricePerShare is less than MAX_PRICE_PER_SHARE.
     * 
     * @return
     */
    public boolean pricePerShareIsValid() {        
        // validate that the price does not exceed the max price per share
        if (this.pricePerShare > MAX_PRICE_PER_SHARE) {
            return false;
        }
        
        // if the method has not already returned a value, all validation passes
        return true;
    }
    
    /**
     * Determine validity of the value of pricePerShare.  It is valid if the 
     * pricePerShare is less than MAX_NUM_OF_SHARES.
     * 
     * @return
     */
    public boolean numberOfSharesIsValid() {        
        // validate that the number of shares does not exceed the max number of
        // shares allowed in a trade
        if (this.numberOfShares > MAX_NUM_OF_SHARES) {
            return false;
        }
        
        // if the method has not already returned a value, all validation passes
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockTrade other = (StockTrade) obj;
        if (Double.doubleToLongBits(this.pricePerShare) != 
                Double.doubleToLongBits(other.pricePerShare)) {
            return false;
        }
        if (this.numberOfShares != other.numberOfShares) {
            return false;
        }
        if (this.taxable != other.taxable) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        if (!Objects.equals(this.licenseNumber, other.licenseNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StockTrade{" + "stockSymbol=" + symbol + 
                ", pricePerShare=" + pricePerShare + 
                ", numberOfShares=" + numberOfShares + 
                ", licenseNumber=" + licenseNumber + 
                ", taxable=" + taxable + '}';
    }
}
