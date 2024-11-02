package simulation;

import fire.*;
import map.*;

public class SplitFire extends Evenement{
    public SplitFire(long date){
        super(date);
    }

    /**
     * Split the fire to the adjacent boxes if possible but not introduced into the subject
     */
    public void splitFire(Fire f){
        int row = f.getCurrentPosition().getRow();
        int column = f.getCurrentPosition().getColumn();
        if (row - 1 >= 0){
            Map.setFire(row - 1, column, f.getIntensity());
        }
        if (row + 1 < Map.getDataMap().getRows()){
            Map.setFire(row + 1, column, f.getIntensity());
        }
        if (column - 1 >= 0){
            Map.setFire(row, column - 1, f.getIntensity());
        }
        if (column + 1 < Map.getDataMap().getColumns()){
            Map.setFire(row, column + 1, f.getIntensity());
        }
    }

    @Override
    public String toString(){
        return "Le feu se propage";
    }

}