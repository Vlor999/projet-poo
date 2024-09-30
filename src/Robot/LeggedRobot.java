package Robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class LeggedRobot extends Robot
{
    private String file = "images/p5.png";
    public LeggedRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 10, 1, Integer.MAX_VALUE, 0, -1, travelSpeed);
    }
    public LeggedRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 30 / 3.6);
    }

    @Override
    public String getType()
    {
        return "LeggedRobot";
    }
    @Override
    public double getSpecialSpeed(TypeLand type) {
        double normalSpeed = this.getTravelSpeed();
        switch (type) {
            case STONE:
                return 10;
            case WATER:
                return 0;
            default:
                return normalSpeed;
        }
    }

    @Override
    public String getFile()
    {
        return this.file;
    }

}
