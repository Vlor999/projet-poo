package map;

import io.*;


import Robot.Robot;
import enumerator.*;
import fire.*;

public class Map {
    // 2D array representing the current map made up of Case objects
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String RESET = "\u001B[0m";
    static Box[][] currentMap;
    
    // Data object containing map metadata (e.g., number of rows and columns)
    static Data dataMap;

    /**
     * Set a specific case on the map at the given row and column.
     * 
     * @param row The row index where the case should be placed.
     * @param column The column index where the case should be placed.
     * @param currentCase The Case object to place on the map.
     */
    public static void setMapValue(Box currentCase) {
        // Validate row and column indices
        int row = currentCase.getRow();
        int column = currentCase.getColumn();
        if (row < 0 || row >= dataMap.getRows() || column < 0 || column >= dataMap.getColumns()) {
            throw new IllegalArgumentException("Invalid row or column index. Must be within the map bounds.");
        }
        currentMap[row][column] = currentCase;
    }
    public static void preSetMap()
    {
        currentMap = new Box[dataMap.getRows()][dataMap.getColumns()];
    }

    public static TypeLand getTypeLand(int row, int column)
    {
        return currentMap[row][column].getNature();
    }

    public String lineString()
    {
        String line = "";
        for (int c = 0; c < dataMap.getColumns() *  2; c += 2)
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
        String myData = "Datas about the map : \n" + dataMap.toString();
        String myMap = "\nMap : \n";
        String lineOf = this.lineString();
        myMap += lineOf + '\n';
        for (int l = 0; l < dataMap.getRows(); l +=1)
        {
            for (int c = 0; c < dataMap.getColumns(); c +=1)
            {
                myMap += "|";
                boolean fire = Fire.isFire(l,c);
                boolean robot = Robot.isRobot(l,c);
                if (fire)
                {
                    myMap += RED;
                }
                else if (robot)
                {
                    myMap += GREEN;
                }
                myMap += getTypeLand(l, c);
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

    public static String showMap()
    {
        return new Map().toString();
    }

    /**
     * Set the fire on the map
     * 
     * @param row
     * @param column
     * @param intensity
     */
    public static void setFire(int row, int column, int intensity)
    {
        new Fire(currentMap[row][column], intensity);
    }

    public static Data getDataMap(){ return dataMap; }

    public static void setDataMap(Data dataMap) 
    { 
        Map.dataMap = dataMap; 
        preSetMap();
    }
}
