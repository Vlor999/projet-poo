package simulation;
import io.*;
import robot.*;



public class FillUp extends Evenement{

    private final Robot robot;
    public FillUp(long date, Robot r){
        super(date);
        this.robot = r;
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
        double increaseSpeed = 0; // to keep a good timing during the simulation
        if (increaseSpeed < 0){
            increaseSpeed = 0;
        }
        r.setCurrentVolume(r.getCurrentVolume() + ((r.getTankCapacity() + increaseSpeed)) / r.getFillingTime());
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

    @Override
    public void execute()
    {
        if (Data.getIsVerbose())
        {
            System.out.println("Filling up the tank.");
        }
        fillUpRobot(this.robot);
    }

    @Override
    public String toString(){
        return "[" + this.getDate() + "] Rechargement du robot en cours\n";
    }
}