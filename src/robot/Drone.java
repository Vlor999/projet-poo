package robot;

import enumerator.TypeLand;
import io.Data;
import map.Box;

public class Drone extends Robot
{
    public Drone(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 10000, 30, 0, 30, 10000, travelSpeed);
        this.file = "images/Robot_Drone.png";
    }
    
    public Drone(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 100 / 3.6); // Conversion from km/h to m/s
    }

    @Override
    /**
     * Get the special speed of the robot depending on the type of land but for the drone no need to change the speed
     * @param type the type of land
     */
    public double getSpecialSpeed(TypeLand type)
    {
        double normalSpeed = this.travelSpeed;
        return normalSpeed;
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}