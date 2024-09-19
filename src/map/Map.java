package map;

import io.*;
import Robot.Robot;
import enumerator.*;

public class Map {
    // 2D array representing the current map made up of Case objects
    private Case[][] currentMap;
    
    // Data object containing map metadata (e.g., number of rows and columns)
    private Data dataMap;

    /**
     * Constructor for the Map class.
     * 
     * @param dataMap The Data object containing the map's dimensions and other relevant information.
     */
    public Map(Data dataMap) {
        this.dataMap = dataMap;
        // Initialize the map based on the number of rows and columns from the Data object
        this.currentMap = new Case[dataMap.getRows()][dataMap.getColumns()];
    }

    /**
     * Set a specific case on the map at the given row and column.
     * 
     * @param row The row index where the case should be placed.
     * @param column The column index where the case should be placed.
     * @param currentCase The Case object to place on the map.
     */
    public void setMapValue(Case currentCase) {
        // Validate row and column indices
        int row = currentCase.getRow();
        int column = currentCase.getColumne();
        if (row < 0 || row >= dataMap.getRows() || column < 0 || column >= dataMap.getColumns()) {
            throw new IllegalArgumentException("Invalid row or column index. Must be within the map bounds.");
        }
        this.currentMap[row][column] = currentCase;
    }

    public TypeLand getTypeLand(int row, int column)
    {
        return this.currentMap[row][column].getNature();
    }

    public String lineString()
    {
        String line = "";
        for (int c = 0; c < this.dataMap.getColumns() *  2; c += 2)
        {
            line += "+-";
        }
        line += "+";
        return line;
    } 

    /**
     * Return the String that represente the map at the begenning
     * 
     * @return String of the map
     */
    public String toString()
    {
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        String myData = "Datas about the map : \n" + this.dataMap.toString();
        String myMap = "\nMap : \n";
        String lineOf = this.lineString();
        myMap += lineOf + '\n';
        for (int l = 0; l < this.dataMap.getRows(); l +=1)
        {
            for (int c = 0; c < this.dataMap.getColumns(); c +=1)
            {
                myMap += "|";
                boolean fire = this.currentMap[l][c].getFire();
                boolean robot = this.currentMap[l][c].getRobot();
                if (fire)
                {
                    myMap += RED;
                }
                else if (robot)
                {
                    myMap += GREEN;
                }
                myMap += this.getTypeLand(l, c);
                if (fire || robot)
                {
                    myMap += RESET;
                }
            }
            myMap += "|\n";
            myMap += lineOf + '\n';
        }
        return myData + myMap;
    }

    /**
     * Set the fire on the map
     * 
     * @param row
     * @param column
     * @param isFire
     */
    public void setFire(int row, int column, boolean isFire, int intensity)
    {
        this.currentMap[row][column].setFire(isFire);
        this.currentMap[row][column].setIntensity(intensity);
    }


    /**
     * Set the robot on the map
     * 
     * @param row
     * @param column
     * @param isRobot
     */
    public void setRobot(int row, int column, boolean isRobot)
    {
        this.currentMap[row][column].setRobot(isRobot);
    }
}