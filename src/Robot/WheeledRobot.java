package Robot;
import io.Data;
import map.Box;

public class WheeledRobot extends Robot
{
    public WheeledRobot(Data mapData, Box currentCase, int travelSpeed)
    {
        super(mapData, currentCase, 100, 5, 1, 10, 5000, travelSpeed);
    }
    public WheeledRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 80);
    }

    @Override
    public String getType()
    {
        return "WheeledRobot";
    }

}



    
