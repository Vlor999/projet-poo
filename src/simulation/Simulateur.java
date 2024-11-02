package simulation;

import gui.*;
import io.Draw;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import robot.*;

public class Simulateur implements Simulable{
    private static long dateSimulation;
    private CaptainRobot capitaine;
    private boolean end = false;
    private static GUISimulator gui;
    protected static List<Evenement> listEvenements = new ArrayList<>();

    public Simulateur(long date, GUISimulator myGUI){
        this.dateSimulation = date;
        this.capitaine = new CaptainRobot();
        Simulateur.setGUI(myGUI);
        myGUI.setSimulable(this);
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

        List<String> test = new ArrayList<>();
        test.add("a");
        test.add("a");
        test.add("b");
        test.add("b");
        List<String> test2 = test;
        test.remove("a");
        System.out.println(test);
         System.out.println(test2);
        if(Terminee())
        {
            System.out.printf("\r----- Simulation terminee ------");
            Draw.end(Simulateur.getGUI());
            return;
        }
        // TENTATIVE AFFICHAGE EVENEMENTS
        int indice = 0;
        if (!listEvenements.isEmpty()){
            for (Evenement eve : listEvenements){
                if (eve.getDate() < dateSimulation - 1){
                    // listEvenements.remove(indice);
                }
                else{
                    eve.execute();
                    indice += 1;
                }
            }
        }
        // System.out.println(listEvenements +"\n");
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
