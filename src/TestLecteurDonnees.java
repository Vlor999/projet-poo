
import io.LecteurDonnees;
import Robot.*;
import enumerator.*;
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
            try {
                LecteurDonnees.lire(args[i]);

                AStar aStar = new AStar();
                List<Robot> listRobots = Robot.getListRobots();
                List<Box> listWater = Map.getListWater();
                for (Robot robot : listRobots)
                {
                    System.out.println("Robot: " + robot.getType() + "\n" + robot.getPositionRobot());
                    double minVal = -1;
                    List<Box> bestPath = new ArrayList<>();
                    for (Box endBox : listWater)
                    {
                        if (robot.getType().equals("LeggedRobot"))
                        {
                            continue;
                        }
                        else
                        {
                            List<Box> path = aStar.aStarSearch(Map.getCurrentMap(), robot, endBox);
                            double currentVal = path.get(path.size() - 1).getFCost();
                            if (currentVal < minVal || minVal == -1)
                            {
                                aStar.setFinalListDirection(aStar.getListDirection());
                                minVal = currentVal;
                                bestPath = path;
                            }
                        }
                        
                    }
                    System.out.println(aStar.showInfo(bestPath));
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

