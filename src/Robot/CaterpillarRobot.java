package Robot;

import io.Data;
import map.Case;

public class CaterpillarRobot extends Robot
{
    public CaterpillarRobot(Data mapData, Case currentCase, int spilledVolume, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed)
    {
        super(mapData, currentCase, spilledVolume, spillTime, fillingType, fillingTime, tankCapacity, travelSpeed);
    }
    public CaterpillarRobot(Data mapData, Case currentCase)
    {
        super(mapData, currentCase, 100, 8, 1, 5, 2000, 60);
    }

    public Case getPositionCaterpillarRobot()
    {
        return this.getPositionRobot();
    }


}
