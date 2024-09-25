package Robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class LeggedRobot extends Robot
{
    private String file = "Images/p3.png";
    public LeggedRobot(Data mapData, Box currentCase, int travelSpeed)
    {
        super(mapData, currentCase, 10, 1, -1, 0, -1, travelSpeed);
    }
    public LeggedRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 30);
    }

    @Override
    public String getType()
    {
        return "LeggedRobot";
    }
    @Override
    public int getSpecialSpeed(TypeLand type) {
        int normalSpeed = this.getTravelSpeed();
        switch (type) {
            case STONE:
                return 10;
            case WATER:
                return 0;
            default:
                return normalSpeed;
        }
    }
    public String getFile()
    {
        return this.file;
    }

}
