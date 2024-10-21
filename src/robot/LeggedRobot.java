package robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class LeggedRobot extends Robot
{
    public LeggedRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 10, 1, Integer.MAX_VALUE, 0, -1, travelSpeed);
        this.file = "images/Robot_Legged.png";
    }
    public LeggedRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 30 / 3.6); // Conversion from km/h to m/s
    }

    @Override
    public String getType()
    {
        return "Legged";
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
                return 10;
            case WATER:
                return 0;
            default:
                return normalSpeed;
        }
    }

}
