package Robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class WheeledRobot extends Robot
{

    private String file = "Images/p2.png";

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
    @Override
    public int getSpecialSpeed(TypeLand type) {
        int normalSpeed = this.getTravelSpeed();
        switch (type) {
            case STONE:
                return 0;
            case WATER:
                return 0;
            case FOREST:
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



    
