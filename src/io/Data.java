package io;

/*
 * This class is used to store the data of the map.
 */
public class Data {
    // Number of rows in the map
    private int numberOfRows;
    
    // Number of columns in the map
    private int numberOfColumns;
    
    // Size of each case (in meters)
    private double caseSize;

    // Prints more information during the simulation to resolve problems
    private static boolean isVerbose = false;
    /**
     * Constructor for the Data class.
     * @param rows        The number of rows in the map.
     * @param columns     The number of columns in the map.
     * @param caseSize    The size of each case in meters.
     */
    public Data(int rows, int columns, double caseSize) {
        this.numberOfRows = rows;
        this.numberOfColumns = columns;
        this.caseSize = caseSize;
    }

    /**
     * Get the number of columns in the map.
     * @return The number of columns.
     */
    public int getColumns() {
        return this.numberOfColumns;
    }

    public static boolean getIsVerbose(){
        return isVerbose;
    }
    /**
     * Get the number of rows in the map.
     * @return The number of rows.
     */
    public int getRows() {
        return this.numberOfRows;
    }

    /**
     * Get the size of each case in meters.
     * @return The size of each case.
     */
    public double getCaseSize() {
        return this.caseSize;
    }

    @Override
    /**
     * Return the datas as a String
     * @return String values of the datas
     */
    public String toString()
    {
        return "Number of columns : " + this.numberOfColumns + "\nNumber of rows : " 
        +  this.numberOfRows + "\nCase size : " + this.caseSize;
    }

    public static void setIsVerbose(boolean b) {
        isVerbose = b;
    }

}
