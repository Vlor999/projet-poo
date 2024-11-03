package simulation;

import fire.Fire;
import gui.*;
import io.Draw;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import map.*;
import robot.*;

public class Simulateur implements Simulable{
    private static long dateSimulation;
    private final CaptainRobot capitaine;
    private boolean end = false;
    private static GUISimulator gui;
    protected static List<Evenement> listEvenements = new ArrayList<>();

    public Simulateur(long date, GUISimulator myGUI){
        dateSimulation = date;
        this.capitaine = new CaptainRobot();
        Simulateur.setGUI(myGUI);
    }

    public void initialize()
    {
        gui.setSimulable(this);
    }

    /**
     * Permet d'agrandir la liste des évènements 
     */
    public static void ajouteEvenement(Evenement e){
        listEvenements.add(e);
    }

    public static long getDateSimulation(){return dateSimulation;}

    /**
     * Permet de savoir combien de temps s'est ecoule depuis le debut de la simulation
     */
    private void incrementeDate()
    {
        dateSimulation += 1;
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
        if (!listEvenements.isEmpty()){
            List<Evenement> copy = new ArrayList<>();
            for (Evenement eve : listEvenements){
                if (eve.getDate() >= dateSimulation - 1){
                    copy.add(eve);
                    eve.execute();
                }
            }
            listEvenements = copy;
            copy.clear();
        }
        
        Random rand = new Random();
        if (Simulateur.getGUI().isFirePropagationEnabled() && dateSimulation != 0) { // Exemple : propagation tous les en fonction de la taille de la case
            if (dateSimulation % (Math.round(Map.getDataMap().getCaseSize()/2)) == 0)
            {
                for (Fire fire : Fire.getListFires()) {
                    Double proba = rand.nextDouble();
                    if (proba < 0.1)
                    {
                        ajouteEvenement(new FirePropa(dateSimulation, fire));
                    }
                }
            }
        }

        end = this.capitaine.next();
        this.incrementeDate();
    }

    @Override
    public void restart()
    {
        this.capitaine.restart();
        dateSimulation = 0;
        end = false;
    }
}
