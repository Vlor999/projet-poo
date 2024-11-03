package map;
import enumerator.*;
import fire.*;
import io.*;
import java.util.ArrayList;
import java.util.List;
import robot.Robot;



public class Map {
    private final static String RED = "\u001B[31m";
    private final static String GREEN = "\u001B[32m";
    private final static String RESET = "\u001B[0m";
    private final static String BLUE = "\u001B[34m";
    private static Box[][] currentMap;
    private static Data dataMap;
    private final static List<Box> listWater = new ArrayList<>();

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
        resetAllWater(); // Reset the list of water
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
        Fire.createFire(currentMap[row][column], intensity);
    }

    /**
     * Will show a line of the map as a terminal image. Could be useful for debugging and tu use less energy.
     * @return a line of delimitations 
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
    @Override
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

    /**Prints the whole map
     * @return the map 
      */
    public static String showMap()
    {
        return new Map().toString();
    }

    /**Prints the list of water boxes
     * @return the list of water boxes
     */
    public static String showListWater()
    {
        String txt = "List of water : \n";
        for (Box box : listWater)
        {
            txt += "Water at : \n" + box.toString(1) + "\n";
        }
        return txt;
    }

    /**Check if a coordinate is a water box 
     * @param row the coordinate y
     * @param column the coordinate x 
     * @return if the related box is water 
     */
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

    //Reset all the list of water 
    public static void resetAllWater()
    {
        listWater.clear();
    }

    //reset all the data initiated
    public static void resetAllDatas()
    {
        resetAllWater();
        Fire.resetAllFires();
        Robot.resetAllRobots();
    }
}
