package simulation;
import robot.*;
import simulation.*;
import io.*;

public class FillUp extends Evenement{

    public FillUp(long date){
        super(date);
    }

    /**
     * Fill up the tank of the robot. There is some parameters to know the simulation accurate
     */
    public static void fillUpRobot(Robot r)
    {
        if (Data.getIsVerbose())
        {
            System.out.println("Filling up the tank.");
            System.out.println(r);
        }
        double increaseSpeed = r.getFillingTime()/r.getSpillingTime() - 1.5; // to keep a good timing during the simulation
        if (increaseSpeed < 0){
            increaseSpeed = 1;
        }
        r.setCurrentVolume(r.getCurrentVolume() + (r.getTankCapacity() * (1+increaseSpeed)) / r.getFillingTime());
        if (r.getCurrentVolume() >= r.getTankCapacity())
        {
            r.setCurrentVolume(r.getTankCapacity());
            r.setEndFill(true);
        }
        else
        {
            r.setEndFill(false);
        }
    }
}