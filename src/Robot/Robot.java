package Robot;

import io.Data;
import io.Draw;
import map.AStar;
import map.Box;
import map.Map;

import java.util.List;

import enumerator.TypeLand;
import fire.Fire;
import gui.GUISimulator;
import gui.Simulable;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Robot implements Simulable{
    
    private Iterator<Box> boxIterator;
    private List<Box> boxList;
    private GUISimulator gui;
    private boolean guiSet;

    // Water tank capacity and volume already spilled (in liters)
    private int tankCapacity;
    private int spillVolumePerTimes;
    private int currentVolume;
    
    // Travel speed (in km/h)
    private int travelSpeed;

    
    // Filling type and time (0: on case, 1: adjacent, -1: not required)
    private int fillingType;
    private int fillingTime; // in minutes
    
    // Time to spill the tank (in seconds)
    private int spillTime;
    
    // Current terrain type robot is on
    private Box currentCase;
    public Box initBox;
    
    // The number of robots created
    private static int robotCount = 0;
    private static List<Robot> listRobots = new ArrayList<>();

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
        this.initBox = new Box(currentCase.getRow(), currentCase.getColumn(), currentCase.getNature());
        this.currentVolume = 0;
        robotCount++;
        listRobots.add(this);
        this.guiSet = false;
    }

    public void setGui(GUISimulator gui)
    {
        this.gui = gui;
        this.guiSet = true;
    }

    public Iterator<Box> getBoxIterator()
    {
        return this.boxIterator;
    }

    public static void setGuiRobots(GUISimulator gui)
    {
        for(Robot robot: listRobots)
        {
            robot.setGui(gui);
            gui.setSimulable(robot);	
            if (robot.getType().equals("LeggedRobot") || robot.currentVolume > 0 )
            {
                System.out.println("LeggedRobot");
                robot.setIterator(Fire.getListFireBox());
            }
            else
            {
                robot.setIterator(Map.getListWater());
            }
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
    public static Robot stringToRobot(String type, Data mapData, Box currentCase, int travelSpeed) {
        switch (type.toUpperCase()) {
            // toUpperCase to be sure that all the letters are in uppercase and that there is no miss match
            case "CHENILLES":
                if (travelSpeed < 0)
                {
                    travelSpeed = 80;
                }
                return new CaterpillarRobot(mapData, currentCase, travelSpeed);
            case "DRONE":
                if (travelSpeed < 0)
                {
                    travelSpeed = 150;
                }
                return new Drone(mapData, currentCase, travelSpeed);
            case "PATTES":
                if (travelSpeed < 0)
                {
                    travelSpeed = 30;
                }
                return new LeggedRobot(mapData, currentCase, travelSpeed);
            case "ROUES":
                if (travelSpeed < 0)
                {
                    travelSpeed = 50;
                }
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
        + "\n\t * Spill volume per times: " + this.getSpillVolumePerTimes()
        + "\n\t * Tank capacity: " + this.getTankCapacity()
        + "\n\t * Travel speed: " + this.getTravelSpeed();
    }
    
    /**
     * Getters for various robot properties.
     */
    public int getTankCapacity() { return this.tankCapacity; }
    
    public int getSpillVolumePerTimes() { return this.spillVolumePerTimes; }
    
    public int getTravelSpeed() { return this.travelSpeed; }

    public abstract int getSpecialSpeed(TypeLand type);
    
    public int getFillingType() { return fillingType;}
    
    public int getFillingTime() { return this.fillingTime; }

    public int getSpillTime() { return this.spillTime; }
    
    public Box getPositionRobot() { return this.currentCase;}
    
    public static int getRobotCount() { return robotCount; }

    public static List<Robot> getListRobots() { return new ArrayList<>(listRobots); }
    
    public abstract String getType();

    /**
     * Sets the robot's position to a new case with a deep copy.
     * @param newCase The new case where the robot should move to.
     */
    public void setPositionRobot(Box newCase) 
    {
        this.currentCase = new Box(newCase.getRow(), newCase.getColumn(), newCase.getNature());
    }

    public static void removeRobot(Robot robot) { listRobots.remove(robot); }

    public static void clearRobots() { listRobots.clear(); }

    public static String showAllRobots() {
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
     * Will make the movement of the robot 
     * @param TargetCase the box he aims 
     */
    public void MoveRobot(Box TargetCase){

        AStar astar = new AStar();
        List<Box> trajet = astar.aStarSearch(Map.getCurrentMap(),this,TargetCase);
        for (Box elem : trajet){
            this.setPositionRobot(elem);
        }
    }

    public static Robot getRobotPostion(int row, int col)
    {
        for (Robot robot : listRobots)
        {
            if (robot.getPositionRobot().getRow() == row && robot.getPositionRobot().getColumn() == col)
            {
                return robot;
            }
        }
        return null;
    }

    public abstract String getFile();

    public void setIterator(List<Box> list) {
        AStar aStar = new AStar();
        List<Box> bestWay = aStar.findBestWayTo(this, list);
        System.out.println("Best way : ");
        for(Box b:bestWay)
        {
            System.out.println(b.toString(1));
        }
        this.boxIterator = bestWay.iterator();
    }
    
    public void setCurrentVolume(int volume)
    {
        this.currentVolume -= volume;
    }

    @Override
    public void next()
    {
        for (Robot robot : listRobots)
        {
            if (robot.boxIterator.hasNext())
            {
                robot.setPositionRobot(robot.boxIterator.next());
            }
            else
            {
                System.out.println(robot.getType() + " : the robot has reached the water source");
                if (robot.getType().equals("LeggedRobot") || robot.currentVolume > 0 )
                {
                    robot.setIterator(Fire.getListFireBox());
                    Fire f = Fire.getClosestFire(robot.getPositionRobot());
                    try
                    {
                        f.decreaseIntensity(robot.spillVolumePerTimes, robot);
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println("No more fires");
                    }
                }
                else
                {
                    robot.setIterator(Map.getListWater());
                    robot.currentVolume = robot.tankCapacity;
                }
            }
            Draw.drawMap(gui);
        }
    }
    
    @Override
    public void restart()
    {
        for(Robot robot : listRobots)
        {
            robot.currentCase = robot.initBox;
            if (robot.getType().equals("LeggedRobot") || robot.currentVolume > 0 )
            {
                robot.setIterator(Fire.getListFireBox());
                
            }
            else
            {
                robot.setIterator(Map.getListWater());
            }
        }
        
        Fire.setListFires();
        System.out.println("Liste mémoire des feux : " + Fire.getListFiresMemory().size());
        System.out.println("Liste des feux : " + Fire.getListFires().size());
        Draw.drawMap(gui);
    }   
}

