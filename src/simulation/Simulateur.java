package simulation;

import gui.*;
import io.Draw;
import robot.*;

public class Simulateur implements Simulable{
    private long dateSimulation;
    private CaptainRobot capitaine;
    private boolean end = false;
    private static GUISimulator gui;

    public Simulateur(long date, GUISimulator myGUI){
        this.dateSimulation = date;
        this.capitaine = new CaptainRobot();
        Simulateur.setGUI(myGUI);
        myGUI.setSimulable(this);
    }

    public void ajouteEvenement(Evenement e){
        System.out.println("Methode ajouteEvenement(Evenement e): pas encore implementee! (simulation)");
    }

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
        end = this.capitaine.next();
        this.incrementeDate();
    }

    @Override
    public void restart()
    {
        this.capitaine.restart();
    }
}
