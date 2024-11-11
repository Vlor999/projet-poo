package simulation;
import fire.*;
import robot.*;

public class DecreaseIntensity extends Evenement{
    private final Robot robot;
    private final Fire fire;
    private final boolean action;
    public DecreaseIntensity(long date, Robot r, Fire f, boolean action){
        super(date);
        this.robot = r;
        this.fire = f;
        this.action = action;
    }

    /**
     * Increase the intensity of the fire if the robot is close enough and has enough water
     * @param r the robot
     * @return result boolean if the fire is extinguished
     */
    public static boolean decreaseIntensity(Robot r, Fire f, boolean  action){
        if (!action)
        {
            return false;
        }
        boolean result = false;
        if (r.getPositionRobot().distanceTo(f.getCurrentPosition()) <= Integer.min(r.getFillingType(), 1) && (r.getCurrentVolume() > 0 || LeggedRobot.isLegged(r)))
        {
            //if the volume is under the spillVolumePerTimes, we spill everything
            // constant to go a little bit faster in the Simulation
            // double vol = (1+absolu(r.getFillingTime()/r.getSpillingTime()))*Double.min(r.getSpillVolumePerTimes(), r.getCurrentVolume()); // the volume of water that the robot can spill
            double vol = r.getQuantityPerTimes();
            if (f.getIntensity() - vol <= 0){
                result = true;
                f.setIntensity(f.getInitValues()); // reset the intensity
                Fire.removeFire(f); // remove the fire from the list
            }
            else{
                f.setIntensity(f.getIntensity() - (int)vol); // decrease the intensity of the fire if not enough water
            }
            r.setCurrentVolume(r.getCurrentVolume() - vol); // decrease the volume of water of the robot
        }
        return result;
    }


    @Override
    public String toString(){
        return "[" + this.getDate() + "] Extinction du feu en cours\n";
    }

    @Override
    public void execute()
    {
        if (this.fire == null)
        {
            return;
        }
        if (DecreaseIntensity.decreaseIntensity(this.robot, this.fire, this.action)) {
            Robot.adjustRobotsDirection();
        }
    }

}