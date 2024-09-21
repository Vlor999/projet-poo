package fire;

import java.util.ArrayList;
import java.util.List;

import map.*;

public class Fire 
{
    private Box currentPosition;
    private int intensity;
    private static List<Fire> listFires = new ArrayList<>();
    static int numberFire = 0;

    public Fire(Box currentPosition, int intensity){
        this.currentPosition = currentPosition;
        this.intensity = intensity;
        listFires.add(this);
        numberFire++;
    }

    public Box getCurrentPosition(){ return this.currentPosition; }

    public void decreaseIntensity(int intensity){
        if (this.intensity - intensity < 0){
            this.intensity = 0;
            listFires.remove(this);
            numberFire--;
        }
        else{
            this.intensity -= intensity;
        }
    }

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
     * @return List of Fire
     */
    public List<Fire> getListFires(){
        return listFires;
    }

    /**
     * Reset the list of fires
     * @return void
     */
    public static void resetListFires(){
        listFires.clear();
    }

    /**
     * Remove a fire from the list of fires
     * @param fire
     */
    public void removeFire(Fire fire){
        listFires.remove(fire);
    }

    /**
     * Show the list of fires
     * @return String
     */
    public static String showListFires(){
        String listFiresString = "Number of fires : " + numberFire + "\n";
        for (Fire fire : listFires)
        {
            listFiresString += "" + fire + "\n";
        }
        return listFiresString;
    }

    /**
     * toString methode
     * @return String
     */
    public String toString(){
        return "Fire at position (" + this.currentPosition.getRow() + "," + this.currentPosition.getColumn() + ") with intensity " + this.intensity;
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
}
