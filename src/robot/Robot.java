package robot;

import io.Data;
import io.Draw;
import map.AStar;
import map.Box;
import map.Map;

import java.util.List;

import enumerator.*;
import fire.Fire;
import gui.GUISimulator;
import gui.Simulable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class Robot implements Simulable{
    
    private Iterator<Box> boxIterator;
    private GUISimulator gui;

    // Water tank capacity and volume already spilled (in liters)
    private int tankCapacity;
    private double spillVolumePerTimes;
    private int currentVolume;
    
    // Travel speed (in m/s)
    protected double travelSpeed;

    // the file to display the robot
    protected String file;
    
    // Filling type and time (0: on case, 1: adjacent, Integer.MAX_VALUE: not required)
    private int fillingType;
    private int fillingTime; // in seconds
    private boolean endFill = false;
    
    // Time to spill the tank (in seconds)
    private int spillTime;
    
    // Current terrain type robot is on
    private Box currentCase;
    public Box initBox;
    
    // The number of robots created
    private static int robotCount = 0;
    private static List<Robot> listRobots = new ArrayList<>();
    private boolean isUseless = false;

    public static boolean endNext = false;


    /**
     * Constructor   
     * to initialize a Robot object.
     * 
     * @param mapData                    Data object containing map metadata
     * @param tankCapacity               Tank capacity in liters
     * @param spillVolumePerTimes        Volume spilled per times in liters
     * @param fillingType                Filling type (0: on case, 1: adjacent, -1: not required)
     * @param fillingTime                Time to fill the tank in minutes
     * @param spillTime                  Time to spill the tank in seconds
     * @param travelSpeed                Speed of the robot in km/h
     * @param currentCase                Terrain type on which the robot starts
     */
    public Robot(Data mapData, Box currentCase, double spillVolumePerTimes, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,double travelSpeed) 
    {
        validatePosition(currentCase, mapData);

        this.tankCapacity = tankCapacity;
        this.spillVolumePerTimes = spillVolumePerTimes;
        this.fillingType = fillingType;
        this.fillingTime = fillingTime * 60;
        this.spillTime = spillTime;
        this.travelSpeed = travelSpeed;
        this.currentCase = currentCase;
        this.initBox = new Box(currentCase.getRow(), currentCase.getColumn(), currentCase.getNature());
        this.currentVolume = 0;
        robotCount++;
        listRobots.add(this);
    }

    public boolean setIsUseless(boolean isUseless)
    {
        return this.isUseless = isUseless;
    }

    public boolean getIsUseless()
    {
        return this.isUseless;
    }

    public void setGui(GUISimulator gui)
    {
        this.gui = gui;
    }

    public static void setGuiRobots(GUISimulator gui)
    {
        for(Robot robot: listRobots)
        {
            robot.setGui(gui);
            gui.setSimulable(robot);	
            robot.boxIterator = Collections.emptyIterator();
        }
    }

    /**
     * Validates if the current position of the robot is within the map boundaries.
     */
    private void validatePosition(Box currentCase, Data mapData) {
        int startX = currentCase.getRow();
        int startY = currentCase.getColumn();
        if (startX < 0 || startY < 0 || startX >= mapData.getRows() || startY >= mapData.getColumns()) {
            throw new IllegalArgumentException("Invalid coordinates for the robot's starting position.");
        }
    }

    /**
     * Converts a string to a specific type of Robot.
     * @param type        String indicating the type of robot
     * @param mapData     Data object containing map metadata
     * @param currentCase Terrain type on which the robot starts
     * @param travelSpeed Speed of the robot
     * @return Robot instance
     */
    public static Robot stringToRobot(String type, Data mapData, Box currentCase, double travelSpeed) {
        switch (type.toUpperCase()) {
            // toUpperCase to be sure that all the letters are in uppercase and that there is no miss match
            // Convert km/h -> m/s
            case "CHENILLES":
                if (travelSpeed <= 0 || travelSpeed > 80)
                {
                    travelSpeed = 60;
                }
                travelSpeed = travelSpeed * 1000 / 3600;
                return new CaterpillarRobot(mapData, currentCase, travelSpeed);
            case "DRONE":
                if (travelSpeed <= 0 || travelSpeed > 150)
                {
                    travelSpeed = 100;
                }
                travelSpeed = travelSpeed * 1000 / 3600;
                return new Drone(mapData, currentCase, travelSpeed);
            case "PATTES": // the speed must be 30 and can't be changed 
                if (travelSpeed != 30)
                {
                    travelSpeed = 30;
                }
                travelSpeed = travelSpeed * 1000 / 3600;
                return new LeggedRobot(mapData, currentCase, travelSpeed);
            case "ROUES":
                if (travelSpeed <= 0)
                {
                    travelSpeed = 80;
                }
                travelSpeed = travelSpeed * 1000 / 3600;
                return new WheeledRobot(mapData, currentCase, travelSpeed);
            default:
                throw new IllegalArgumentException("Invalid type of robot.");
        }
    }

    /**
     * Returns a string representation of the robot's information.
     * @return A string containing the robot's information.
     */
    public String toString()
    {
        return  this.getType() + " info:" 
        + "\n\t * Current Position: \n" + this.getPositionRobot().toString(2)
        + "\n\t * Spill volume per times: " + this.spillVolumePerTimes + " L/s"
        + "\n\t * Tank capacity: " + this.tankCapacity + " L"
        + "\n\t * Travel speed: " + this.travelSpeed + " m/s";
    }
    
    /**
     * Getters for various robot properties.
     */
    
    public abstract double getSpecialSpeed(TypeLand type);
    
    public abstract String getType();

    public int getFillingType() { return fillingType;}
    
    public int getFillingTime() { return this.fillingTime; }
    
    public Box getPositionRobot() { return this.currentCase;}
    
    public double getSpillVolumePerTimes() { return this.spillVolumePerTimes; }
    
    public static List<Robot> getListRobots() { return new ArrayList<>(listRobots); }

    public String getFile(){return this.file;} 

    /**
     * Sets the robot's position to a new case with a deep copy.
     * @param newCase The new case where the robot should move to.
     */
    public void setPositionRobot(Box newCase) 
    {
        this.currentCase = new Box(newCase.getRow(), newCase.getColumn(), newCase.getNature());
    }

    public static void clearRobots() { listRobots.clear(); } // Not used but may be usefull

    public static String showAllRobots() {
        // Not used but may be usefull with the verbose mode
        String result = "Number of robots: " + robotCount + "\n";
        for (Robot robot : listRobots) {
            result += robot.toString() + "\n";
        }
        return result;
    }

    /**
     * Will say if at the position there is a robot
     * @param row The row index where the robot should be placed.
     * @param column The column index where the robot should be placed.
     * @return boolean
     */
    public static boolean isRobot(int row, int column)
    {
        for (Robot robot : listRobots)
        {
            if (robot.getPositionRobot().getRow() == row && robot.getPositionRobot().getColumn() == column)
            {
                return true;
            }
        }
        return false;
    }

    public void setIterator(List<Box> list) {
        AStar aStar = new AStar();
        List<Box> l = aStar.findBestWayTo(this, list);
        this.boxIterator = l.iterator();
    }
    
    public void setCurrentVolume(double volume)
    {
        if (this.currentVolume + volume <= 0)
        {
            this.currentVolume = 0;
        }
        else
        {
            this.currentVolume += volume;
        }
    }

    public int getCurrentVolume()
    {
        return this.currentVolume;
    }

    public void fillUp()
    {
        // The time to fill the tank is really slow so we incresed it. But if we want the real speed put 0 to increseSpeed.
        double increaseSpeed = (double)(tankCapacity)/2;
        this.currentVolume += (this.tankCapacity+increaseSpeed) / this.fillingTime;
        if (this.currentVolume >= this.tankCapacity)
        {
            this.currentVolume = this.tankCapacity;
            this.endFill = true;
        }
        else
        {
            this.endFill = false;
        }
    }

    private boolean waterAround() {
        int row = this.getPositionRobot().getRow();
        int col = this.getPositionRobot().getColumn();
        
        // Get map dimensions
        int maxRows = Map.getDataMap().getRows();
        int maxCols = Map.getDataMap().getColumns();
        
        // Check if the robot is a Drone
        if (this.getType().equals("Drone")) {
            return Map.getTypeLand(row, col).getValueTerrain() == TypeLand.WATER.getValueTerrain();
        } else {
            // Define the relative positions to check (down, up, right, left)
            int[][] directions = {
                Direction.SOUTH.getDirection(),  // down
                Direction.NORTH.getDirection(),  // up
                Direction.EAST.getDirection(),   // right
                Direction.WEST.getDirection()    // left
            };

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                // Check boundaries and water terrain
                if (newRow >= 0 && newRow < maxRows && newCol >= 0 && newCol < maxCols &&
                    Map.getTypeLand(newRow, newCol).getValueTerrain() == TypeLand.WATER.getValueTerrain()) {
                    return true;
                }
            }
            return false;
        }
    }
    

    @Override
    public void next()
    {
        boolean resultFire;
        if (endNext)
        {
            Draw.end(gui);
            return;
        }
        for (Robot robot : listRobots)
        {
            if (robot.boxIterator.hasNext()) // Ne fais pas attention si le feu est éteint ou pas 
            {
                robot.setPositionRobot(robot.boxIterator.next());
            }
            else
            {
                if (robot.getType().equals("LeggedRobot") || (robot.currentVolume > 0 && robot.endFill))
                {
                    robot.setIterator(Fire.getListFireBox());
                    Fire f = Fire.getClosestFire(robot.getPositionRobot());
                    try
                    {
                        resultFire = f.decreaseIntensity(robot);
                        if (resultFire){ // on doit changer leurs directions
                            for (Robot r : listRobots){
                                if (robot.currentVolume > 0)
                                {
                                    r.setIterator(Fire.getListFireBox());
                                }
                            }
                        }
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println("No more fires");
                        endNext = true;
                        break;
                    }
                }
                else
                {
                    if (robot.waterAround())
                    {
                        robot.fillUp();
                    }
                    else
                    {
                        robot.setIterator(Map.getListWater());
                    }
                }
            }
            Draw.drawMap(gui);
        }
    }
    
    @Override
    public void restart()
    {
        endNext = false;
        Fire.setListFires();
        for(Robot robot : listRobots)
        {
            robot.isUseless = false;
            robot.currentCase = robot.initBox;
            robot.currentVolume = 0;
            robot.boxIterator = Collections.emptyIterator();
        }
        Draw.drawMap(gui);
    }   

    public static List<Robot> getListRobotsBox(Box box)
    {
        List<Robot> list = new ArrayList<>();
        for (Robot robot : listRobots)
        {
            if (robot.getPositionRobot().equals(box))
            {
                list.add(robot);
            }
        }
        return list;
    }
}
