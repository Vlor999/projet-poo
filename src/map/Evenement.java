package map;

import java.util.*;

import enumerator.*;
import robot.*;

import fire.Fire;
import io.Data;
import io.LecteurDonnees;

public abstract class Evenement{
    private long date;


    public Evenement(long date){
        this.date = 0;
        
    }

    public long getDate(){ return date; }

    public void execute(){
        
        SuperRobot r = new SuperRobot(Data.)
        r.next();
        date += 1;
    }

}