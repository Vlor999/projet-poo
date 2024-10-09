package robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class WheeledRobot extends Robot
{
    public WheeledRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 20, 5, 1, 10, 5000, travelSpeed);
        this.file = "images/Robot_Wheeled.png";
    }

    public WheeledRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 80 / 3.6); // Conversion from km/h to m/s
    }

    @Override
    public String getType()
    {
        return "WheeledRobot";
    }

    @Override
    /**
     * Get the special speed of the robot depending on the type of land. If 0 the robot can't move
     * @param type the type of land
     */
    public double getSpecialSpeed(TypeLand type) {
        double normalSpeed = this.travelSpeed;
        switch (type) {
            case STONE:
                return 0;
            case WATER:
                return 0;
            case FOREST:
                return 0;
            default:
                return normalSpeed;
        }
    }
}



    
