package Robot;

import enumerator.TypeLand;
import io.Data;
import map.Box;

public class CaterpillarRobot extends Robot
{
    private String file = "images/p4.png";
    public CaterpillarRobot(Data mapData, Box currentCase, int travelSpeed)
    {
        super(mapData, currentCase, 100, 8, 1, 5, 2000, travelSpeed);
    }
    public CaterpillarRobot(Data mapData, Box currentCase)
    {this(mapData, currentCase, 60);}

    @Override
    public String getType()
    { return "CaterpillarRobot";}
    @Override
    public int getSpecialSpeed(TypeLand type) {
        int normalSpeed = this.getTravelSpeed();
        switch (type) {
            case STONE:
                return 0;
            case WATER:
                return 0;
            case FOREST:
                return (int) (normalSpeed * 0.5);
            default:
                return normalSpeed;
        }
    }
    public String getFile()
    {
        return this.file;
    }

}
