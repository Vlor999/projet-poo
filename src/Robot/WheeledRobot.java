package Robot;
import enumerator.TypeLand;
import io.Data;
import map.Box;

public class WheeledRobot extends Robot
{

    private String file = "images/p6.png";

    public WheeledRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 20, 5, 1, 10, 5000, travelSpeed);
    }

    public WheeledRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 80 / 3.6);
    }

    @Override
    public String getType()
    {
        return "WheeledRobot";
    }

    @Override
    public double getSpecialSpeed(TypeLand type) {
        double normalSpeed = this.travelSpeed;
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

    @Override
    public String getFile()
    {
        return this.file;
    }
}



    
