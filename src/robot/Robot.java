package robot;

import enumerator.*;
import fire.Fire;
import gui.GUISimulator;
import io.Data;
import io.Draw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import map.Box;
import map.Map;
import simulation.*;


public abstract class Robot{
    
    // This is the iterator that will allow the robot to move and gui is the graphical interface
    protected Iterator<Box> boxIterator = Collections.emptyIterator();
    protected GUISimulator gui;

    // Water tank capacity and volume already spilled (in liters)
    protected int tankCapacity;
    protected double spillVolumePerTimes;
    protected int currentVolume;
    
    // Travel speed (in m/s)
    protected double travelSpeed;

    // the file to display the robot
    protected String[] files = new String[3];
    
    // Filling type and time (0: on case, 1: adjacent, Integer.MAX_VALUE: not required)
    protected int fillingType;
    protected int fillingTime; // in seconds
    protected boolean endFill = false;
    
    // Time to spill the tank (in seconds)
    protected int spillTime;
    
    // Current terrain type robot is on
    protected Box currentCase;
    protected final Box initBox;
    
    // The number of robots created
    protected static int robotCount = 0;
    private final static List<Robot> listRobots = new ArrayList<>();
    protected boolean isUseless = false;

    protected static boolean endNext = false;

    /**
     * Constructor   
     * to initialize a Robot object.
     * 
     * @param mapData                    Data object containing map metadata
     * @param tankCapacity               Tank capacity in liters
     * @param quantityPerTimes           Volume spilled per times in liters
     * @param fillingType                Filling type (0: on case, 1: adjacent, -1: not required)
     * @param fillingTime                Time to fill the tank in minutes
     * @param spillTime                  Time to spill the tank in seconds
     * @param travelSpeed                Speed of the robot in km/h
     * @param currentCase                Terrain type on which the robot starts
     */
    public Robot(Data mapData, Box currentCase, double quantityPerTimes, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,double travelSpeed) 
    {
        validatePosition(currentCase, mapData);

        this.tankCapacity = tankCapacity;
        this.fillingType = fillingType;
        this.fillingTime = fillingTime * 60; // Convert minutes to seconds
        this.spillTime = spillTime;
        this.travelSpeed = travelSpeed;
        this.currentCase = currentCase;
        this.initBox = new Box(currentCase.getRow(), currentCase.getColumn(), currentCase.getNature());
        this.currentVolume = 0;
        // We are currently using the second as the time unit so we have to know the time needed to spill the tank
        if(!(this instanceof CaptainRobot))
        {
            listRobots.add(this);
            this.spillVolumePerTimes = quantityPerTimes / this.spillTime;
            robotCount++;
            this.boxIterator = Collections.emptyIterator();
        }
    }

    /**
     * Set the robot as useless if there is no need to look for the closest fire or water. The robot is blocked.
     * @param isUseless
     * @return
     */
    public boolean setIsUseless(boolean isUseless)
    {
        this.isUseless = isUseless;
        return this.isUseless; 
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
            case "CHENILLES":
                if (travelSpeed <= 0 || travelSpeed > 80) {
                    travelSpeed = 60; // Default speed if over the limit
                }
                travelSpeed = travelSpeed / 3.6; // Conversion from km/h to m/s
                return new CaterpillarRobot(mapData, currentCase, travelSpeed);
            case "DRONE":
                if (travelSpeed <= 0 || travelSpeed > 150) {
                    travelSpeed = 100;
                }
                travelSpeed = travelSpeed / 3.6;
                return new Drone(mapData, currentCase, travelSpeed);
            case "PATTES":
                if (travelSpeed != 30) {
                    travelSpeed = 30;
                }
                travelSpeed = travelSpeed / 3.6;
                return new LeggedRobot(mapData, currentCase, travelSpeed);
            case "ROUES":
                if (travelSpeed <= 0) {
                    travelSpeed = 80; // no max value for the speed
                }
                travelSpeed = travelSpeed / 3.6;
                return new WheeledRobot(mapData, currentCase, travelSpeed);
            default:
                throw new IllegalArgumentException("Invalid type of robot.");
        }
    }

    @Override
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
        + "\n\t * Travel speed: " + this.travelSpeed + " m/s"
        + "\n\t * Current Tank Capacity: " + this.currentVolume + "L : witch represent  : " + 100 * this.currentVolume/this.tankCapacity + "%";
    }
    
    /**
     * Getters for various robot properties.
     */
    public abstract double getSpecialSpeed(TypeLand type);
    
    public abstract String getType();

    public int getFillingType() { return fillingType;}
    
    public Box getPositionRobot() { return this.currentCase;}
    
    public double getSpillVolumePerTimes() { return this.spillVolumePerTimes; }
    
    public String getFiles(int number){return files[number];} 

    public double getFillingTime() {return this.fillingTime;}

    public double getSpillingTime() {return this.spillTime;}

    public int getCurrentVolume(){return this.currentVolume;}

    public double getTankCapacity(){return this.tankCapacity;}

    public void setEndFill(boolean b){this.endFill = b;}

    public boolean getIsUseless(){ return this.isUseless;}

    public void setBoxIterator(Iterator<Box> I){ this.boxIterator = I;}

    /**
     * Sets the robot's position to a new case with a deep copy.
     * @param newCase The new case where the robot should move to.
     */
    public void setPositionRobot(Box newCase) 
    {
        this.currentCase = new Box(newCase.getRow(), newCase.getColumn(), newCase.getNature());
    }

    public static void clearRobots() { listRobots.clear(); } // Not used but may be usefull

    /**
     * Show all the robots
     * @return
     */
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

    
    /**
     * We are currently setting up the volume of the robot
     * @param volume
     */
    public void setCurrentVolume(double volume)
    {
        if (Data.getIsVerbose())
        {
            System.out.println("Curently spilling water.");
            System.out.println(this);
        }
        if (this.currentVolume + volume <= 0)
        {
            this.currentVolume = 0;
        }
        else
        {
            this.currentVolume = (int)volume;
        }
    }


    /**
     * Check if there is water around the robot and at a good distance. This last depends on the type of robot
     * @return boolean that says so
     */
    protected boolean waterAround() {
        int row = this.getPositionRobot().getRow();
        int col = this.getPositionRobot().getColumn();
        
        // Get map dimensions
        int maxRows = Map.getDataMap().getRows();
        int maxCols = Map.getDataMap().getColumns();
        
        // Check if the robot is a Drone
        if (this instanceof Drone) {
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
    

    /**
     * Nous voulions quelque chose qui fonctionne pour tous les robots. 
     * L'idée ici est de regarder à travers tous les robots qui peuvent bouger quel est le prochain mouvement à faire.
     * Une fois avoir trouvé pour tous les robots ce qu'ils doivent faire alors on les fait bouger.
     * Si un robot ne peut plus bouger alors on le met en inutile. 
     */
    public boolean nextOP() {
        if (endNext) {
            return true;
        }
        
        for (Robot robot : listRobots) {
            if (processNextRobotStep(robot)) {
                endNext = true;
                break;
            }
            Draw.drawMap(Simulateur.getGUI());
        }
        
        return endNext;
    }
    
    private boolean processNextRobotStep(Robot robot) {
        try {
            if (robot.boxIterator.hasNext()) {
                robot.setPositionRobot(robot.boxIterator.next());
            } else if (shouldExtinguishFire(robot)) {
                handleFireExtinguishing(robot);
            } else {
                handleWaterRefill(robot);
            }
        } catch (NullPointerException e) {
            return true; // Fin de la simulation en cas d'exception.
        }
        return false;
    }
    
    private boolean shouldExtinguishFire(Robot robot) {
        return robot instanceof LeggedRobot || (robot.currentVolume > 0 && robot.endFill);
    }
    
    private void handleFireExtinguishing(Robot robot) {
        SetIterator setIteratorObject = new SetIterator(Simulateur.getDateSimulation());
        SetIterator.setIterator(Fire.getListFireBox(), robot);
        Simulateur.ajouteEvenement(setIteratorObject);
        
        Fire fire = Fire.getClosestFire(robot.getPositionRobot());
        DecreaseIntensity d = new DecreaseIntensity(Simulateur.getDateSimulation());
        Simulateur.ajouteEvenement(d);
        
        if (DecreaseIntensity.decreaseIntensity(robot, fire)) {
            adjustRobotsDirection();
        }
    }
    
    private void adjustRobotsDirection() {
        for (Robot r : listRobots) {
            if (r.currentVolume >= 0) {
                SetIterator setIteratorObject = new SetIterator(Simulateur.getDateSimulation());
                if (r instanceof Drone && !Fire.isFire(r.getPositionRobot())) {
                    r.currentVolume = 0;
                    SetIterator.setIterator(Map.getListWater(), r);
                } else {
                    SetIterator.setIterator(Fire.getListFireBox(), r);
                }
                Simulateur.ajouteEvenement(setIteratorObject);
            }
        }
    }
    
    private void handleWaterRefill(Robot robot) {
        if (robot.waterAround()) {
            FillUp fillup = new FillUp(Simulateur.getDateSimulation());
            FillUp.fillUpRobot(robot);
            Simulateur.ajouteEvenement(fillup);
        } else {
            SetIterator setIteratorObject = new SetIterator(Simulateur.getDateSimulation());
            SetIterator.setIterator(Map.getListWater(), robot);
            Simulateur.ajouteEvenement(setIteratorObject);
        }
    }
    
    
    /**
     * Restart the simulation by putting all the robots at their initial position and reset the fire
     */
    public void restartOP()
    {
        endNext = false;
        SetListFires setFire = new SetListFires(Simulateur.getDateSimulation());
        SetListFires.setListFires();
        Simulateur.ajouteEvenement(setFire);
        for(Robot robot : listRobots)
        {
            robot.isUseless = false;
            robot.currentCase = robot.initBox;
            robot.currentVolume = 0;
            robot.boxIterator = Collections.emptyIterator();
        }
        Draw.restartDisplay(Simulateur.getGUI());
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

    public static List<Robot> getListRobots()
    {
        return listRobots;
    } 

    public static void resetAllRobots()
    {
        listRobots.clear();
        robotCount = 0;
    }
}
