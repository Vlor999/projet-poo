package Robot;

import enumerator.TypeLand;
import io.Data;
import map.Box;

public class Drone extends Robot
{
    private String file = "images/p3.png";

    public Drone(Data mapData, Box currentCase, int travelSpeed)
    {
        super(mapData, currentCase, 1000, 30, 0, 30, 10000, travelSpeed);
    }
    
    public Drone(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 100);
    }

    @Override
    public int getSpecialSpeed(TypeLand type)
    {
        return this.getTravelSpeed();
    }

    @Override
    public String getType()
    {
        return "Drone";
    }

    public String getFile()
    {
        return file;
    }

}