package robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class LeggedRobot extends Robot
{
    public LeggedRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 10, 1, Integer.MAX_VALUE, 0, -1, travelSpeed);
        this.files[0] = "images/Robot_Legged.png";
        this.files[1] = "images/Pacman_legged.png";
    }
    public LeggedRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 30 / 3.6); // Conversion from km/h to m/s
    }

    @Override
    /**
     * return the type of this robot
     */
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
