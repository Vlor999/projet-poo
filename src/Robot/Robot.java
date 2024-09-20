package Robot;

import io.Data;
import map.Box;
import java.util.List;
import java.util.ArrayList;

public abstract class Robot {
    
    // Water tank capacity and volume already spilled (in liters)
    private int tankCapacity;
    private int spillVolumePerTimes;
    
    // Travel speed (in km/h)
    private int travelSpeed;

    
    // Filling type and time (0: on case, 1: adjacent, -1: not required)
    private int fillingType;
    private int fillingTime; // in minutes
    
    // Time to spill the tank (in seconds)
    private int spillTime;
    
    // Current terrain type robot is on
    private Box currentCase;
    
    // The number of robots created
    private static int robotCount = 0;
    private static List<Robot> listRobots = new ArrayList<>();

    /**
     * Constructor to initialize a Robot object.
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
    public Robot(Data mapData, Box currentCase, int spillVolumePerTimes, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed) 
    {
        validatePosition(currentCase, mapData);

        this.tankCapacity = tankCapacity;
        this.spillVolumePerTimes = spillVolumePerTimes;
        this.fillingType = fillingType;
        this.fillingTime = fillingTime;
        this.spillTime = spillTime;
        this.travelSpeed = travelSpeed;
        this.currentCase = currentCase;
        robotCount++;
        listRobots.add(this);
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
     *
     * @param type        String indicating the type of robot
     * @param mapData     Data object containing map metadata
     * @param currentCase Terrain type on which the robot starts
     * @param travelSpeed Speed of the robot
     * @return Robot instance
     */
    public static Robot stringToRobot(String type, Data mapData, Box currentCase, int travelSpeed) {
        switch (type.toUpperCase()) {
            case "CHENILLES":
                return new CaterpillarRobot(mapData, currentCase, validTravelSpeed(travelSpeed, 80));
            case "DRONE":
                return new Drone(mapData, currentCase, validTravelSpeed(travelSpeed, 150));
            case "PATTES":
                return new LeggedRobot(mapData, currentCase, 30);
            case "ROUES":
                return new WheeledRobot(mapData, currentCase, travelSpeed > 0 ? travelSpeed : 50);
            default:
                throw new IllegalArgumentException("Invalid type of robot.");
        }
    }

    /**
     * Validates and caps the travel speed to ensure it does not exceed maximum speed.
     * 
     * @param speed     The travel speed to validate
     * @param maxSpeed  The maximum speed allowed
     * @return The valid travel speed
     */
    private static int validTravelSpeed(int speed, int maxSpeed) {
        return Math.min(Math.max(speed, 0), maxSpeed);
    }

    /**
     * Returns a string representation of the robot's information.
     * 
     * @return A string containing the robot's information.
     */
    public String toString()
    {
        return  this.getType() + " info: \n" 
        + "\t * Current Position: \n" + this.getPositionRobot().toString(2)
        + "\n\t * Spill volume per times: " + this.getSpillVolumePerTimes()
        + "\n\t * Tank capacity: " + this.getTankCapacity()
        + "\n\t * Travel speed: " + this.getTravelSpeed();
    }
    
    /**
     * Getters for various robot properties.
     */

    
    public int getTankCapacity() { return tankCapacity; }
    
    public int getSpillVolumePerTimes() { return spillVolumePerTimes; }
    
    public int getTravelSpeed() { return travelSpeed; }
    
    public int getFillingType() { return fillingType; }
    
    public int getFillingTime() { return fillingTime; }

    public int getSpillTime() { return spillTime; }
    
    /**
     * Gets the current position of the robot.
     *
     * @return A Box object representing the robot's current position.
     */
    public Box getPositionRobot() { return currentCase;}
    
    /**
     * Sets the robot's position to a new case with a deep copy.
     * @param newCase The new case where the robot should move to.
     */
    public void setPositionRobot(Box newCase) 
    {
        this.currentCase = new Box(newCase.getRow(), newCase.getColumn(), newCase.getNature());
    }

    /**
     * Static methods to manage the list of robots.
     */
    public static int getRobotCount() { return robotCount; }

    public static List<Robot> getListRobots() { return new ArrayList<>(listRobots); }

    public static void removeRobot(Robot robot) { listRobots.remove(robot); }

    public static void clearRobots() { listRobots.clear(); }

    public static String showAllRobots() {
        String result = "";
        for (Robot robot : listRobots) {
            result += robot.toString() + "\n";
        }
        return result;
    }

    public abstract String getType();
}
