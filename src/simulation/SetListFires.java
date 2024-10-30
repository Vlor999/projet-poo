package simulation;
import fire.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SetListFires extends Evenement{
    public SetListFires(long date){
        super(date);
    }

    /**
     * Set the list of fires to know where to fires are
     */
    public static void setListFires()
    {
        List<Fire> listFiresToAdd = new ArrayList<>();
        int compteur = 0;
        for (Fire f : Fire.getListFiresMemory())
        {
            listFiresToAdd.add(f);
            f.setIntensity(f.getInitValues()); 
            compteur += 1;
        }
        Fire.setListFires(listFiresToAdd);
        Fire.setNumberFire(compteur);
    }

    @Override
    public String toString(){
        return "Feux mis en place";
    }
}