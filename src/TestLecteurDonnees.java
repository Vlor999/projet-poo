
import io.LecteurDonnees;
import Robot.*;
import fire.Fire;
import map.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.DataFormatException;



public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        for (int i = 0; i < args.length; i++)
        {
            try {
                LecteurDonnees.lire(args[i]);
                System.out.println(Fire.showListFires());
                System.out.println(Map.showListWater());

                AStarAlgorithm aStar = new AStarAlgorithm();

                Box endBox = Map.getListWater().get(0);
                
                List<Robot> l1 = Robot.getListRobots();
                Robot r1 = l1.get(0);

                List<Node> path = aStar.aStarSearch(Map.getCurrentMap(), r1, endBox);

                if (path.isEmpty()) {
                    System.out.println("No path found.");
                } else {
                    System.out.println("Path found:" + AStarAlgorithm.getListDirection());
                    for (Node node : path) {
                        System.out.println("(" + node.x + ", " + node.y + ")");
                    }
                }

                Robot.clearRobots();
                Fire.resetListFires();
            } catch (FileNotFoundException e) {
                System.out.println("fichier " + args[i] + " inconnu ou illisible");
            } catch (DataFormatException e) {
                System.out.println("\n\t**format du fichier " + args[i] + " invalide: " + e.getMessage());
            }
        }
    }

}

