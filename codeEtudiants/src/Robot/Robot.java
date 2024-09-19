package Robot;
import enumerator.*;
import io.Case;


public abstract class Robot 
{
    private int x;
    private int y;
    private int tank; // en litres
    private int deverse; // en litres
    private int travelSpeed; // en km/h et si pas dans le dico pas de déplacement sur ce type de case
    private TypeLand[] availableLand; // Will have in here all the NatureTerrain type with some number to say if available 
    private int fillingType; // vaut 0 si sur la case, 1 si à coté, -1 si pas besoin
    private int fillingTime; // en min
    private int timeDeverse; // en sec 
    private TypeLand currentLand;

    public Robot(int n, int m, int x, int y, int tank, int deverse, int type_remplissage, int temps_remplissage, int temps_deverse, int vitesse_deplacement, TypeLand terrain_actuel){
        if (x >= m || x < 0 || y >= n || y < 0){
            throw new IllegalArgumentException("Probleme d'argument ");
        }
        this.x = x;
        this.y = y;
        this.tank = tank;
        this.deverse = deverse;
        this.currentLand = terrain_actuel;
        this.travelSpeed = vitesse_deplacement;
        this.fillingType = type_remplissage;
        this.timeDeverse = temps_deverse;
        this.fillingTime = temps_remplissage;
    }

    public Case getPosition(){
        Case res = new Case(this.x, this.y, this.currentLand);
        return res;
    }

    public void setPosition(Case c){
        this.x = c.getLine();
        this.y = c.getColumne();
        this.currentLand = c.getNature();
    }

    public void spillOut(int vol)
    {
        if (this.tank >= vol)
        {
            this.tank -= vol;
        }
        else
        {
            throw new IllegalArgumentException("The volume must be lower than the tank strorage");
        }
    }

}