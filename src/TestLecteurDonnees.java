
import io.*;
import robot.*;
import gui.GUISimulator;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;

public class TestLecteurDonnees {
    
    private static GUISimulator gui;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        else
        {
            LecteurDonnees.analyse(args);
        }
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        LecteurDonnees.lireFichierEtSimuler(args[0], gui);
    }

    public static GUISimulator getGui() {
        return gui;
    }
}

