
import io.LecteurDonnees;
import Robot.*;
import fire.Fire;

import java.io.FileNotFoundException;
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

