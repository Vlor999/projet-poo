package robot;

import enumerator.TypeLand;
import fire.Fire;
import io.Data;
import io.Draw;
import map.*;



public class SuperRobot extends Robot {
    public SuperRobot(Data mapData){
        super(mapData, Map.getCurrentMap()[0][0], 0, 0, 0, 0, 0, 0);
        this.file = "images/Robot_Drone.png";
    }

    @Override
    public String getType()
    {
        return "SuperRobot";
    }

    public double getSpecialSpeed(TypeLand type) {
        return 0;
    }





}
