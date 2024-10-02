package Robot;

import enumerator.TypeLand;
import io.Data;
import map.Box;

public class CaterpillarRobot extends Robot
{
    private String file = "images/p4.png";
    public CaterpillarRobot(Data mapData, Box currentCase, double travelSpeed)
    {
        super(mapData, currentCase, 12.5, 8, 1, 5, 2000, travelSpeed);
    }
    public CaterpillarRobot(Data mapData, Box currentCase)
    {
        this(mapData, currentCase, 60 / 3.6);
    }

    @Override
    public String getType()
    { return "CaterpillarRobot";}

    @Override
    public double getSpecialSpeed(TypeLand type) {
        double normalSpeed = this.travelSpeed;
        switch (type) {
            case STONE:
                return 0;
            case WATER:
                return 0;
            case FOREST:
                return (normalSpeed * 0.5);
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
