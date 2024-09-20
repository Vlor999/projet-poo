package Robot;

import io.Data;
import map.Box;

public class CaterpillarRobot extends Robot
{
    public CaterpillarRobot(Data mapData, Box currentCase, int travelSpeed)
    {
        super(mapData, currentCase, 100, 8, 1, 5, 2000, travelSpeed);
    }
    public CaterpillarRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 60);
    }

    public Box getPositionCaterpillarRobot()
    {
        return this.getPositionRobot();
    }

    @Override
    public String getType()
    {
        return "CaterpillarRobot";
    }

}
