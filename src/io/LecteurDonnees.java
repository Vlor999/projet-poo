package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import enumerator.TypeLand;
import map.Box;
import map.Map;

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


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static void lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        Map myMap = lecteur.lireCarte();
        lecteur.lireIncendies(myMap);
        lecteur.lireRobots(myMap);
        scanner.close();
        System.out.println("\n == Lecture terminee");
        System.out.println(myMap);
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
    private Map lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);
            Data myData = new Data(nbLignes, nbColonnes, tailleCases);
            Map myMap = new Map(myData);
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    TypeLand currentTypeLand = lireCase(lig, col);
                    myMap.setMapValue(new Box(lig, col, currentTypeLand));
                }
            }
            return myMap;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }

    private TypeLand convertStringToTypeLand(String chaineNature)
    {
        switch (chaineNature) {
            case "TERRAIN_LIBRE":
                return TypeLand.FIELD;
            case "EAU":
                return TypeLand.WATER;
            case "HABITAT":
                return TypeLand.HABITATION;
            case "FORET":
                return TypeLand.FOREST;
            case "ROCHE":
                return TypeLand.STONE;
            default:
                throw new IllegalArgumentException("Unknown terrain type: " + chaineNature);
        }
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private TypeLand lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // We are converting the string to the corresponding enum

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature + "\n");
            TypeLand currentTypeLand = convertStringToTypeLand(chaineNature);
            return currentTypeLand;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]\n");
        }
    }


    /**
     * Lit et affiche les donnees des incendies.
     */
    private void lireIncendies(Map myMap) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i, myMap);
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
    private void lireIncendie(int i, Map myMap) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            myMap.setFire(lig, col, true, intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    private void lireRobots(Map myMap) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i, myMap);
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
    private void lireRobot(int i, Map myMap) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            System.out.print("\t type = " + type);


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();
            myMap.setRobot(lig, col, true);
            System.out.println();

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
}
