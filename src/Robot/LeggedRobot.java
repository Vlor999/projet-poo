package Robot;
import io.Data;
import map.Box;

public class LeggedRobot extends Robot
{
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

}
