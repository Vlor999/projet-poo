package robot;

import enumerator.TypeLand;
import io.Data;
import map.Box;

public class CaterpillarRobot extends Robot
{
    public CaterpillarRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 100, 8, 1, 5, 2000, travelSpeed);
        this.file = "images/Robot_Caterpillar.png";
    }
    public CaterpillarRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 60 / 3.6); // Conversion from km/h to m/s
    }

    @Override
    public String getType()
    { 
        return "Caterpillar";
    }

    @Override
    /**
     * Get the special speed of the robot depending on the type of land. If 0 the robot can't move
     * @param type
     * @return the speed of the robot on this type of land
     */
    public double getSpecialSpeed(TypeLand type) {
        double normalSpeed = this.travelSpeed;
        switch (type) {
            case STONE:
                return 0;
            case WATER:
                return 0;
            case FOREST:
                return (normalSpeed * 0.5);
            default:
                return normalSpeed;
        }
    }
}
