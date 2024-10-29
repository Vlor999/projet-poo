package fire;

import java.util.ArrayList;
import java.util.List;

import map.*;
import robot.LeggedRobot;
import robot.Robot;
import robot.Drone;

public class Fire 
{
    private Box currentPosition;
    private int intensity; // Represent the quantityy of water needed to extinguish the fire
    private static List<Fire> listFires = new ArrayList<>();
    private static List<Fire> listFiresMemory = new ArrayList<>(); // to keep the initial values of the fires
    private int initValues; // to keep the initial values of the fires
    private static int numberFire = 0;
    private static String[] files = {"images/fire1.png", "images/fire2.png", "images/fire3.png",  "images/fire4.png"};

    public Fire(Box currentPosition, int intensity){
        this.initValues = intensity;
        this.currentPosition = currentPosition;
        this.intensity = intensity;
        listFires.add(this);
        listFiresMemory.add(this); // to keep the initial values of the fires
        numberFire++;
    }

    public Box getCurrentPosition(){ return this.currentPosition; }


    private double absolu(double v){
        if (v<0){
            return -v;
        }
        return v;
    }
    /**
     * Increase the intensity of the fire if the robot is close enough and has enough water
     * @param r the robot
     * @return result boolean if the fire is extinguished
     */
    public boolean decreaseIntensity(Robot r){
        boolean result = false;
        if (r.getPositionRobot().distanceTo(this.currentPosition) <= Integer.min(r.getFillingType(), 1) && (r.getCurrentVolume() > 0 || r instanceof LeggedRobot))
        {
            //if the volume is under the spillVolumePerTimes, we spill everything
            // constant to go a little bit faster in the Simulation
            double vol = (1+absolu(r.getFillingTime()/r.getSpillingTime() - 1.5))*Double.min(r.getSpillVolumePerTimes(), r.getCurrentVolume()); // the volume of water that the robot can spill
            if (r instanceof LeggedRobot)
            {
                vol = 10*1.5;// constant to go a little bit faster in the Simulation
            }
            if (this.intensity - vol <= 0){
                result = true;
                this.intensity = this.initValues; // reset the intensity
                listFires.remove(this); // remove the fire from the list
                numberFire--; // decrease the number of fires
                if (r instanceof Drone)
                {
                    r.setCurrentVolume(-r.getCurrentVolume()); // spend everything in one round for the drone
                }
            }
            else{
                this.intensity -= vol; // decrease the intensity of the fire if not enough water
            }
            r.setCurrentVolume(-vol); // decrease the volume of water of the robot
        }
        return result;
    }

    public static String[] getFiles(){
        return files;
    }


    /**
     * Get the closest fire from a box
     * @param box
     * @return Fire
     */
    public static Fire getClosestFire(Box box){
        int minDistance = Integer.MAX_VALUE;
        Fire closestFire = null;
        for (Fire fire : listFires){
            int distance = Math.abs(fire.getCurrentPosition().getRow() - box.getRow()) + Math.abs(fire.getCurrentPosition().getColumn() - box.getColumn());
            if (distance < minDistance){
                minDistance = distance;
                closestFire = fire;
            }
        }
        return closestFire;
    }

    /**
     * Split the fire to the adjacent boxes if possible but not introduced onto the subject
     */
    public void splitFire(){
        int row = this.currentPosition.getRow();
        int column = this.currentPosition.getColumn();
        if (row - 1 >= 0){
            Map.setFire(row - 1, column, this.intensity);
        }
        if (row + 1 < Map.getDataMap().getRows()){
            Map.setFire(row + 1, column, this.intensity);
        }
        if (column - 1 >= 0){
            Map.setFire(row, column - 1, this.intensity);
        }
        if (column + 1 < Map.getDataMap().getColumns()){
            Map.setFire(row, column + 1, this.intensity);
        }
    }
    
    /**
     * Get the list of fires
     * @return List<Fire>
     */
    public static List<Fire> getListFires(){return listFires;}

    /**
     * Set the list of fires to know where to fires are
     */
    public static void setListFires()
    {
        List<Fire> listFiresToAdd = new ArrayList<>();
        int compteur = 0;
        for (Fire f : listFiresMemory)
        {
            listFiresToAdd.add(f);
            f.intensity = f.initValues; 
            compteur += 1;
        }
        listFires = listFiresToAdd;
        numberFire = compteur;
    }
    
    public static List<Fire> getListFiresMemory(){return listFiresMemory;}

    /**
     * Reset the list of fires for an other simulation
     */
    public static void resetListFires(){listFires.clear();}

    /**
     * Remove a fire from the list
     * @param fire
     */
    public void removeFire(Fire fire){listFires.remove(fire);}

    /**
     * Show the list of fires
     * @return String
     */
    public static String showListFires(){
        String listFiresString = "Number of fires : " + numberFire + "\n";
        for (Fire fire : listFires)
        {
            listFiresString += "Fire at position :\n" + fire.toString() + "\n"; 
        }
        return listFiresString;
    }

    @Override
    /**
     * toString methode
     * @return String
     */
    public String toString(){
        return this.currentPosition.toString(1) + "\n\t* Intensity : " + this.intensity;
    }

    /**
     * Will say if at the position there is a fire
     * @param row
     * @param column
     * @return boolean
     */
    public static boolean isFire(int row, int column){
        for (Fire fire : listFires){
            if (fire.getCurrentPosition().getRow() == row && fire.getCurrentPosition().getColumn() == column){
                return true;
            }
        }
        return false;
    }

    public static List<Box> getListFireBox()
    {
        List<Box> listFireBox = new ArrayList<>();
        for (Fire fire : listFires)
        {
            listFireBox.add(fire.getCurrentPosition());
        }
        return listFireBox;
    }

    public static void resetAllFires()
    {
        listFires.clear();
        listFiresMemory.clear();
        numberFire = 0;
    }
}
