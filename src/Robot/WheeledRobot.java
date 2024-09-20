package Robot;
import io.Data;
import map.Case;

public class WheeledRobot extends Robot
{
    public WheeledRobot(Data mapData, Case currentCase, int spilledVolume, int spillTime, 
                int fillingType, int fillingTime, int tankCapacity,int travelSpeed)
    {
        super(mapData, currentCase, spilledVolume, spillTime, fillingType, fillingTime, tankCapacity, travelSpeed);
    }
    public WheeledRobot(Data mapData, Case currentCase)
    {
        super(mapData, currentCase, 100, 5, 1, 10, 5000, 80);
    }

    public Case getPositionWheeledRobot()
    {
        return this.getPositionRobot();
    }

}



    
