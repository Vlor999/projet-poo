package Robot;
import io.Data;
import map.Box;

public class LeggedRobot extends Robot
{
    public LeggedRobot(Data mapData, Box currentCase, int spilledVolume, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed)
    {
        super(mapData, currentCase, spilledVolume, spillTime, fillingType, fillingTime, tankCapacity, travelSpeed);
    }
    public LeggedRobot(Data mapData, Box currentCase)
    {
        super(mapData, currentCase, 10, 1, -1, 0, -1, 30);
    }

    public Box getPositionLeggedRobot()
    {
        return this.getPositionRobot();
    }

}
