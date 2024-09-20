package Robot;

import io.Data;
import map.Box;

public class Drone extends Robot
{

    public Drone(Data mapData, Box currentCase, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed)
    {
        super(mapData, currentCase, tankCapacity, spillTime, fillingType, fillingTime, tankCapacity, travelSpeed);
    }
    public Drone(Data mapData, Box currentCase)
    {
        // Default values for a drone
        // spilledVolume = 10000 because throw everything in one time
        super(mapData, currentCase, 10000, 30, 0, 30, 10000, 100);
    }

    public Box getPositionDrone()
    {
        return this.getPositionRobot();
    }


}