package Robot;

import io.Data;
import map.Box;

public class Drone extends Robot
{
    
    public Drone(Data mapData, Box currentCase, int travelSpeed)
    {
        super(mapData, currentCase, 1000, 30, 0, 30, 10000, travelSpeed);
    }
    
    public Drone(Data mapData, Box currentCase)
    {
        // Default values for a drone
        // spilledVolume = 10000 because throw everything in one time
        this(mapData, currentCase, 100);
    }

    public Box getPositionDrone()
    {
        return this.getPositionRobot();
    }

    @Override
    public String getType()
    {
        return "Drone";
    }

}