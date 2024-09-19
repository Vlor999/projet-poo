package Robot;

import enumerator.*;
import map.Case;

public abstract class Robot {
    // Position of the robot
    private int x;
    private int y;
    
    // Water tank capacity and volume already spilled (in liters)
    private int tankCapacity;
    private int spilledVolume;
    
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
    
    /**
     * Constructor to initialize a Robot object.
     * 
     * @param maxX               The maximum x-coordinate of the grid
     * @param maxY               The maximum y-coordinate of the grid
     * @param startX             Initial x-coordinate
     * @param startY             Initial y-coordinate
     * @param tankCapacity       Tank capacity in liters
     * @param spilledVolume      Volume already spilled in liters
     * @param fillingType        Filling type (0: on case, 1: adjacent, -1: not required)
     * @param fillingTime        Time to fill the tank in minutes
     * @param spillTime          Time to spill the tank in seconds
     * @param travelSpeed        Speed of the robot in km/h
     * @param currentCase     Terrain type on which the robot starts
     */
    public Robot(int maxX, int maxY, int startX, int startY, int tankCapacity, int spilledVolume, 
                 int fillingType, int fillingTime, int spillTime, int travelSpeed, Case currentCase) {
        
        if (startX >= maxX || startX < 0 || startY >= maxY || startY < 0) {
            throw new IllegalArgumentException("Invalid coordinates for the robot's starting position.");
        }

        this.x = startX;
        this.y = startY;
        this.tankCapacity = tankCapacity;
        this.spilledVolume = spilledVolume;
        this.fillingType = fillingType;
        this.fillingTime = fillingTime;
        this.spillTime = spillTime;
        this.travelSpeed = travelSpeed;
        this.currentCase = currentCase;
    }
    
    /**
     * Gets the current position of the robot.
     * 
     * @return A Case object representing the robot's current position.
     */
    public Case getPosition() {
        return currentCase;
    }
    
    /**
     * Sets the robot's position to a new case with a deep copy.
     * 
     * @param newCase The new case where the robot should move to.
     */
    public void setPosition(Case newCase) {
        this.currentCase = new Case(newCase.getRow(), newCase.getColumne(), newCase.getNature());
    }
    
    /**
     * Spill a specified volume of water.
     * 
     * @param volume The volume of water to spill (in liters).
     */
    public void spillOut(int volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("The volume to spill must be greater than 0.");
        }
        
        if (this.tankCapacity >= volume) {
            this.tankCapacity -= volume;
            this.spilledVolume += volume;
        } else {
            throw new IllegalArgumentException("The volume to spill exceeds the tank capacity.");
        }
    }
}
