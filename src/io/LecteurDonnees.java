package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import enumerator.TypeLand;
import map.Box;
import map.Map;
import robot.Robot;
import gui.GUISimulator;

/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {

    private static boolean isVerbose = false;
    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static void lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        if (fichierDonnees.equals("-v"))
        {
            return;
        }
        System.out.println("\n == Lecture du fichier : " + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte();
        lecteur.lireIncendies();
        lecteur.lireRobots();
        scanner.close();
        System.out.println("\n == Lecture terminee");
        if (isVerbose)
        {
            System.out.println(Map.showMap());
            System.out.println(TypeLand.infos());
        }
    }

    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            double tailleCases = scanner.nextInt();	// en m
            if (isVerbose)
            {
                System.out.println("Carte " + nbLignes + "x" + nbColonnes
                        + "; taille des cases = " + tailleCases + "m");
            }
            Data myData = new Data(nbLignes, nbColonnes, tailleCases);
            Data.setIsVerbose(isVerbose);
            Map.setDataMap(myData);
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    TypeLand currentTypeLand = lireCase(lig, col);
                    Map.setMapValue(new Box(lig, col, currentTypeLand));
                    if (isVerbose)
                    {
                        System.out.println("Case (" + lig + "," + col + ") = "
                                + currentTypeLand);
                    }
                }
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private TypeLand lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        //		NatureTerrain nature;
        try {
            chaineNature = scanner.next();
            verifieLigneTerminee();
            TypeLand currentTypeLand = TypeLand.convertStringToTypeLand(chaineNature);
            return currentTypeLand;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]\n");
        }
    }

    /**
     * Lit et affiche les donnees des incendies.
     */
    private void lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            if (isVerbose)
            {
                System.out.println("Nb d'incendies = " + nbIncendies);
            }
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        if (isVerbose)
        {
            System.out.print("Incendie " + i + ": ");
        }
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            if (isVerbose)
            {
                System.out.println("position = (" + lig + "," + col + ");\t intensite = " + intensite);
            }
            Map.setFire(lig, col, intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    private void lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            if (isVerbose)
            {
                System.out.println("Nb de robots = " + nbRobots);
            }
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private void lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();
        if (isVerbose)
        {
            System.out.println("Robot " + i + ": ");
        }

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();
            
            Box currentBox = new Box(lig, col, Map.getTypeLand(lig, col));
            
            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+(\\.\\d+)?)");	// float : km/h -> m/s ?
            // pour lire un flottant:    ("(\\d+)");
            
            double vitesse = -1;
            if (s != null) {
                vitesse = Double.parseDouble(s);
            }
            
            if (isVerbose)
            {
                System.out.println("\tposition = (" + lig + "," + col + ");");
                System.out.println("\t type = " + type);
                System.out.println("\t vitesse = " + vitesse);
            }

            // Creation of the robot based on the type, the map and the box
            Robot.stringToRobot(type, Map.getDataMap(), currentBox, vitesse);
            
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }

    public static void analyse(String[] args)
    {
        for (String arg : args)
        {
            if (arg.equals("-v"))
            {
                isVerbose = true;
            }
        }
    }

    public static void lireFichierEtSimuler(String args, GUISimulator gui) {
        try{
            Map.resetAllDatas();
            LecteurDonnees.lire(args);
            Robot.setGuiRobots(gui);
            Draw.drawMap(gui);
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("fichier " + args + " inconnu ou illisible");
        }
        catch (DataFormatException e) 
        {
            System.out.println("\n\t**format du fichier " + args + " invalide: " + e.getMessage());
        }
    }
}
