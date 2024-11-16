package robot;
import enumerator.TypeLand;
import io.*;
import map.*;

public class CaptainRobot extends Robot{
    /*
     * THis robot will just say to other robots what to do
     */
    public CaptainRobot(Data mapData, Box currentCase){
        super(mapData, currentCase, 0, 0, 0, 0, 0, 0);
    }

    public CaptainRobot()
    {
        this(Map.getDataMap(), Map.getCurrentMap()[0][0]);
    }

    @Override
    public String getType(){
        return "Captain";
    }

    @Override
    public double getSpecialSpeed(TypeLand type){
        return 0;
    }

    public boolean next()
    {
        return this.nextOP();
    }

    public static boolean isCaptain(Object o)
    {
        return o instanceof CaptainRobot;
    }

    public void restart()
    {
        this.restartOP();
    }
}
