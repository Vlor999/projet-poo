
import io.*;
import gui.GUISimulator;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        /*
         * This part is if you want to test the program without using the terminal
         */
        if(args.length == 0)
        {
            args = new String[1];
            args[0] = "cartes/carteSujet.map";
        }
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        else
        {
            LecteurDonnees.analyse(args);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            LecteurDonnees.lireFichierEtSimuler(args[0], gui);
        }
    }
}

