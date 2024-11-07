package simulation;
import fire.*;
import robot.*;

public class DecreaseIntensity extends Evenement{
    public DecreaseIntensity(long date){
        super(date);
    }

    /**
     * Increase the intensity of the fire if the robot is close enough and has enough water
     * @param r the robot
     * @return result boolean if the fire is extinguished
     */
    public static boolean decreaseIntensity(Robot r, Fire f){

        boolean result = false;
        if (r.getPositionRobot().distanceTo(f.getCurrentPosition()) <= Integer.min(r.getFillingType(), 1) && (r.getCurrentVolume() > 0 || r instanceof LeggedRobot))
        {
            //if the volume is under the spillVolumePerTimes, we spill everything
            // constant to go a little bit faster in the Simulation
            // double vol = (1+absolu(r.getFillingTime()/r.getSpillingTime()))*Double.min(r.getSpillVolumePerTimes(), r.getCurrentVolume()); // the volume of water that the robot can spill
            double vol = r.getQuantityPerTimes();
            if (r instanceof LeggedRobot)
            {
                vol = 10;// constant to go a little bit faster in the Simulation
            }
            if (f.getIntensity() - vol <= 0){
                result = true;
                f.setIntensity(f.getInitValues()); // reset the intensity
                Fire.removeFire(f); // remove the fire from the list
                if (r instanceof Drone)
                {
                    r.setCurrentVolume(0); // spend everything in one round for the drone
                }
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
        return "Extinction du feu en cours";
    }

}