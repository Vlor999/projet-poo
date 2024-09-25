
import io.LecteurDonnees;
import Robot.*;
import fire.Fire;
import map.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.ArrayList;



public class TestLecteurDonnees {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        for (int i = 0; i < args.length; i++)
        {
            try{
                LecteurDonnees.lire(args[i]);
                AStar aStar = new AStar();
                List<Robot> listRobots = Robot.getListRobots();
                List<Box> listWater = Map.getListWater();
                for (Robot robot : listRobots){
                    List<Box> bestPath = AStar.findBestWayToWater(aStar,robot,listWater);
                    System.out.println(aStar.showInfo(bestPath));
                }
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

