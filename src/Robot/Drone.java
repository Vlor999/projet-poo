package Robot;

import io.Data;
import map.Case;

public class Drone extends Robot
{

    public Drone(Data mapData, Case currentCase, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed)
    {
        super(mapData, currentCase, tankCapacity, spillTime, fillingType, fillingTime, tankCapacity, travelSpeed);
    }
    public Drone(Data mapData, Case currentCase)
    {
        // Default values for a drone
        // spilledVolume = 10000 because throw everything in one time
        super(mapData, currentCase, 10000, 30, 0, 30, 10000, 100);
    }

    public Case getPositionDrone()
    {
        return this.getPositionRobot();
    }


}