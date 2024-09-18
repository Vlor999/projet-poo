package io;
import enumerator.*;
import java.util.Dictionary;

public class Robot{
    private int x;
    private int y;
    private int reservoir; // en litres
    private int deverse; // en litres
    private Dictionary vitesse_deplacement; // en km/h et si pas dans le dico pas de déplacement sur ce type de case
    private int type_remplissage; // vaut 0 si sur la case, 1 si à coté, -1 si pas besoin
    private int temps_remplissage; // en min
    private int temps_deverse; // en sec 
    private NatureTerrain terrain_actuel;

    public Robot(int n, int m, int x, int y, int reservoir, int deverse, int type_remplissage, int temps_remplissage, int temps_deverse, Dictionary vitesse_deplacement, NatureTerrain terrain_actuel){
        if (x >= m || x < 0 || y >= n || y < 0){
            throw new IllegalArgumentException("Probleme d'argument ");
        }
        this.x = x;
        this.y = y;
        this.reservoir = reservoir;
        this.deverse = deverse;
        this.terrain_actuel = terrain_actuel;
        this.vitesse_deplacement = vitesse_deplacement;
        this.type_remplissage = type_remplissage;
        this.temps_deverse = temps_deverse;
        this.temps_remplissage = temps_remplissage;
    }

    public Case getPosition(){
        Case res = new Case(this.x, this.y, this.terrain_actuel);
        return res;
    }

    public void setPosition(Case c){
        this.x = c.getLine();
        this.y = c.getColumne();
        this.terrain_actuel = c.getNature();
    }

}