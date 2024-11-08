package simulation;
import java.util.Collections;
import java.util.List;
import map.*;
import robot.*;

public class SetIterator extends Evenement{
    private final Robot robot;
    private final List<Box> list;
    public SetIterator(long date, Robot r, List<Box> list){
        super(date);
        this.robot = r;
        this.list = list;
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
        return  "[" + this.getDate() + "] Nouveau chemin trouvé\n";
    }

    @Override
    public void execute() {
        setIterator(this.list, this.robot);
    }
}