
import io.Draw;
import io.LecteurDonnees;
import Robot.*;
import fire.Fire;
import gui.GUISimulator;
import map.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.ArrayList;
import java.awt.Color;


public class TestLecteurDonnees {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        for (int i = 0; i < args.length; i++)
        {
            try{
                LecteurDonnees.lire(args[i]);
                Robot.setGuiRobots(gui);
                AStar aStar = new AStar();
                List<Robot> listRobots = Robot.getListRobots();
                List<Box> listWater = Map.getListWater();
                List<Box> listFire = Map.getListBoxFire();
                for (Robot robot : listRobots){
                    List<Box> bestPath = aStar.findBestWayTo(robot,listFire);
                    System.out.println(aStar.showInfo(bestPath));
                }
                Draw.drawMap(gui);
                Robot.clearRobots();
                Fire.resetListFires();
            }
            catch (FileNotFoundException e) 
            {
                System.out.println("fichier " + args[i] + " inconnu ou illisible");
            }
            catch (DataFormatException e) 
            {
                System.out.println("\n\t**format du fichier " + args[i] + " invalide: " + e.getMessage());
            }
        }

    }
}

