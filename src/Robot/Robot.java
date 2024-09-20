package Robot;

import enumerator.*;
import io.Data;
import map.Case;

public abstract class Robot {
    // Position of the robot 
    private int myXPosition;
    private int myYPosition;
    
    // Water tank capacity and volume already spilled (in liters)
    private int tankCapacity;
    private int spillVolumePerTimes;
    
    // Travel speed (in km/h)
    private int travelSpeed;
    
    // Array of terrains the robot can navigate
    private TypeLand[] availableLandTypes;
    
    // Filling type and time (0: on case, 1: adjacent, -1: not required)
    private int fillingType;
    private int fillingTime; // in minutes
    
    // Time to spill the tank (in seconds)
    private int spillTime;
    
    // Current terrain type robot is on
    private Case currentCase;
    
    // The number of robots created
    private static int robotCount = 0;

    /**
     * Constructor to initialize a Robot object.
     * 
     * @param maxX                       The maximum x-coordinate of the grid
     * @param maxY                       The maximum y-coordinate of the grid
     * @param startX                     Initial myXPosition-coordinate
     * @param startY                     Initial myYPosition-coordinate
     * @param tankCapacity               Tank capacity in liters
     * @param spillVolumePerTimes      Volume spilled per times in liters
     * @param fillingType                Filling type (0: on case, 1: adjacent, -1: not required)
     * @param fillingTime                Time to fill the tank in minutes
     * @param spillTime                  Time to spill the tank in seconds
     * @param travelSpeed                Speed of the robot in km/h
     * @param currentCase                Terrain type on which the robot starts
     */
    public Robot(Data mapData, Case currentCase, int spillVolumePerTimes, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed) 
    {
        
        int startX = currentCase.getRow();
        int startY = currentCase.getColumne();

        int maxX = mapData.getRows();
        int maxY = mapData.getColumns();
        if (startX >= maxX || startX < 0 || startY >= maxY || startY < 0) {
            throw new IllegalArgumentException("Invalid coordinates for the robot's starting position.");
        }

        this.myXPosition = startX;
        this.myYPosition = startY;
        this.tankCapacity = tankCapacity;
        this.spillVolumePerTimes = spillVolumePerTimes;
        this.fillingType = fillingType;
        this.fillingTime = fillingTime;
        this.spillTime = spillTime;
        this.travelSpeed = travelSpeed;
        this.currentCase = currentCase;
        robotCount++;
    }
    
    /**
     * Gets the current position of the robot.
     * @return A Case object representing the robot's current position.
     */
    public Case getPositionRobot() {
        return this.currentCase;
    }
    
    /**
     * Sets the robot's position to a new case with a deep copy.
     * @param newCase The new case where the robot should move to.
     */
    public void setPositionRobot(Case newCase) {
        this.currentCase = new Case(newCase.getRow(), newCase.getColumne(), newCase.getNature());
    }

    /**
     * Gets the tank capacity of the robot.
     * @return The tank capacity of the robot in liters.
     */
    public int getTankCapacity() {
        return this.tankCapacity;
    }

    /**
     * getSpilledVolume
     * @param volume
     */
    public int getSpillVolumePerTimes() {
        return this.spillVolumePerTimes;
    }

    public String toString()
    {
        return "Drone info: \n" 
        + "\t * " + this.getPositionRobot() + 
        "\n\t * Spill volume per times: " + this.getSpillVolumePerTimes()
        + "\n\t * Tank capacity: " + this.getTankCapacity();
    }

    public static int getRobotCount() {
        return robotCount;
    }


}
