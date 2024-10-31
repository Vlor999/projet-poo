package simulation;

import gui.*;
import io.Draw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import robot.*;

public class Simulateur implements Simulable{
    private static long dateSimulation;
    private CaptainRobot capitaine;
    private boolean end = false;
    private static GUISimulator gui;
    private static List<Evenement> listEvenements = new ArrayList<>();

    public Simulateur(long date, GUISimulator myGUI){
        this.dateSimulation = date;
        this.capitaine = new CaptainRobot();
        Simulateur.setGUI(myGUI);
        myGUI.setSimulable(this);
    }

    public void ajouteEvenement(Evenement e){
        this.listEvenements.add(e);
    }

    public static long getDateSimulation(){return dateSimulation;}

    /**
     * Permet de savoir combien de temps s'est ecoule depuis le debut de la simulation
     */
    private void incrementeDate()
    {
        this.dateSimulation += 1;
    }

    private boolean Terminee()
    {
        return end;
    }

    private static void setGUI(GUISimulator gui)
    {
        Simulateur.gui = gui;
    }

    public static GUISimulator getGUI()
    {
        return Simulateur.gui;
    }

    @Override
    public void next()
    {
        if(Terminee())
        {
            System.out.printf("\r----- Simulation terminee ------");
            Draw.end(Simulateur.getGUI());
            return;
        }
        // TENTATIVE AFFICHAGE EVENEMENTS
        for (Evenement eve : this.listEvenements){
            eve.execute();
            if (eve.getDate() <= this.dateSimulation){
                this.listEvenements.remove(eve);
            }
        }
        System.out.println(listEvenements +"feznef\n");
        end = this.capitaine.next();
        this.incrementeDate();
    }

    @Override
    public void restart()
    {
        this.capitaine.restart();
        this.dateSimulation = 0;
        end = false;
    }
}
