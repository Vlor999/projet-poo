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
        this(mapData, currentCase, 100);
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}