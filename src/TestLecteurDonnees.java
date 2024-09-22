
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

                AStar aStar = new AStar();
                List<Robot> listRobots = Robot.getListRobots();
                List<Box> listWater = Map.getListWater();
                for (Box endBox : listWater)
                {
                    for (Robot robot : listRobots)
                    {
                        if (robot.getType().equals("LeggedRobot"))
                        {
                            System.out.println("No need to putt water");
                        }
                        else
                        {
                            System.out.println("Robot: " + robot.getType() + "\n" + robot.getPositionRobot());
                            System.out.println("End box: \n" + endBox);
                            List<Box> path = aStar.aStarSearch(Map.getCurrentMap(), robot, endBox);
                            System.out.println(AStar.showInfo(path) + "\n");
                        }
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

