package map;
import enumerator.*;
import fire.*;
import io.*;
import robot.Robot;

import java.util.ArrayList;
import java.util.List;


public class Map {
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String RESET = "\u001B[0m";
    static String BLUE = "\u001B[34m";

    static Box[][] currentMap;
    static Data dataMap;
    static List<Box> listWater = new ArrayList<>();

    /**
     * Set a specific case on the map at the given row and column.
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
        if (currentCase.getNature() == TypeLand.WATER)
        {
            listWater.add(currentCase);
        }
    }

    /**
     * Create a map with the given dimensions and set the default values for each case.
     */
    public static void preSetMap()
    {
        resetAll(); // Reset the list of water
        currentMap = new Box[dataMap.getRows()][dataMap.getColumns()];
    }

    /**
     * Set the data map
     * @param dataMap
     */
    public static void setDataMap(Data dataMap) 
    { 
        Map.dataMap = dataMap; 
        preSetMap();
    }

    /**
     * Set the fires on the map
     * @param row
     * @param column
     * @param intensity
     */
    public static void setFire(int row, int column, int intensity){
        new Fire(currentMap[row][column], intensity);
    }

    /**
     * Will show a line of the map as a terminal image. Could be useful for debugging and tu use less energy.
     * @return
     */
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
     * @return String of the map
     */
    public String toString()
    {
        int numberRows = dataMap.getRows();
        int numberColumns = dataMap.getColumns();
        String myData = "Datas about the map : \n" + dataMap.toString();
        String myMap = "\nMap : \n";
        String lineOff = this.lineString();
        myMap += lineOff + '\n';
        for (int c = numberColumns - 1; c >= 0; c -=1){
            for (int l = 0; l < numberRows; l +=1)
            {
                String txt = positionColored(l, c);
                myMap += txt; 
            }
            myMap += "|\n";
            myMap += lineOff + '\n';
        }
        return myData + myMap;
    }
    
    /**
     * Print some datas on the terminal about the map and add some colors to the terminal
     * @param row
     * @param column
     * @return
     */
    private final static String positionColored(int row, int column)
    {
        String txt = "|";
        boolean fire = Fire.isFire(row,column);
        boolean robot = Robot.isRobot(row,column);
        boolean water = Map.isWater(row,column);
        if (fire){
            txt += RED;
        }
        else if (robot){
            txt += GREEN;
        }
        else if (water){
            txt += BLUE;
        }
        txt += getTypeLand(row, column);
        if (fire || robot || water){
            txt += RESET;
        }
        txt += "";
        return txt;
    }

    public static String showMap()
    {
        return new Map().toString();
    }

    public static String showListWater()
    {
        String txt = "List of water : \n";
        for (Box box : listWater)
        {
            txt += "Water at : \n" + box.toString(1) + "\n";
        }
        return txt;
    }

    public static boolean isWater(int row, int column)
    {
        for (Box box : listWater)
        {
            if (box.getRow() == row && box.getColumn() == column)
            {
                return true;
            }
        }
        return false;
    }
    
    // Getters
    public static Data getDataMap() { return dataMap; }
    public static Box[][] getCurrentMap() { return currentMap; }
    public static TypeLand getTypeLand(int row, int column){ return currentMap[row][column].getNature();}
    public static List<Box> getListWater() { return listWater; }
    public static List<Fire> getListFire() { return Fire.getListFires(); }
    public static List<Box> getListBoxFire()
    {
        List<Box> listBoxFire = new ArrayList<>();
        List<Fire> listFire = getListFire();
        for (Fire currentFire: listFire)
        {
            listBoxFire.add(currentFire.getCurrentPosition());
        }
        return listBoxFire;
    }
    public static void resetAll()
    {
        listWater.clear();
    }

    public static void resetAllDatas()
    {
        resetAll();
        Fire.resetAllFires();
        Robot.resetAllRobots();
    }
}
