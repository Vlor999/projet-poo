package simulation;
import robot.*;
import simulation.*;
import io.*;
import map.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SetIterator extends Evenement{

    public SetIterator(long date){
        super(date);
    }

    /**
     * Set the iterator for the robot if the robot is not useless
     * @param list
     */
    public static void setIterator(List<Box> list, Robot r) {
        if (r.getIsUseless())
        {
            // If the robot is useless we don't need to look for the closest fire or water
            r.setBoxIterator(Collections.emptyIterator());
        }
        else
        {
            AStar aStar = new AStar();
            List<Box> l = aStar.findBestWayTo(r, list); //Find the best way from the robot to the points of the list (list of water or fire).
            r.setBoxIterator(l.iterator());
        }
    }

    @Override
    public String toString(){
        return "Nouveau chemin trouvé";
    }
}