package Robot;

import io.Data;
import map.Box;

public class CaterpillarRobot extends Robot
{
    public CaterpillarRobot(Data mapData, Box currentCase, int spilledVolume, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed)
    {
        super(mapData, currentCase, spilledVolume, spillTime, fillingType, fillingTime, tankCapacity, travelSpeed);
    }
    public CaterpillarRobot(Data mapData, Box currentCase)
    {
        super(mapData, currentCase, 100, 8, 1, 5, 2000, 60);
    }

    public Box getPositionCaterpillarRobot()
    {
        return this.getPositionRobot();
    }


}
