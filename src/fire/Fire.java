package fire;

import enumerator.Direction;
import enumerator.TypeLand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import map.*;
import robot.Robot;

public class Fire 
{
    private final Box currentPosition;
    private int intensity; // Represent the quantityy of water needed to extinguish the fire
    private static List<Fire> listFires = new ArrayList<>();
    private final static List<Fire> listFiresMemory = new ArrayList<>(); // to keep the initial values of the fires
    private final int initValues; // to keep the initial values of the fires
    private static int numberFire = 0;
    private final static String[] files = {"images/fire1.png", "images/fire2.png", "images/fire3.png",  "images/fire4.png"};

    private Fire(Box currentPosition, int intensity){
        this.initValues = intensity;
        this.currentPosition = currentPosition;
        this.intensity = intensity;
    }

    // Factory method
    public static Fire createFire(Box currentPosition, int intensity) {
        Fire fire = new Fire(currentPosition, intensity);
        listFires.add(fire);
        listFiresMemory.add(fire); // to keep the initial values of the fires
        numberFire++;
        return fire;
    }

    public int getIntensity(){ return this.intensity;}
    public Box getCurrentPosition(){ return this.currentPosition; }
    public void setIntensity(int v){ this.intensity = v;}
    public int getInitValues(){ return this.initValues;}
    public static void setNumberFire(int v){ numberFire = v;}
    public static int getNumberFire(){ return numberFire;}
    
    public static String[] getFiles(){
        return files;
    }


    /**
     * Get the closest fire from a box
     * @param box
     * @return Fire
     */
    public static Fire getClosestFire(Box box){
        int minDistance = Integer.MAX_VALUE;
        Fire closestFire = null;
        for (Fire fire : listFires){
            int distance = 
            Math.abs(fire.getCurrentPosition().getRow() - box.getRow()) 
            + 
            Math.abs(fire.getCurrentPosition().getColumn() - box.getColumn());
            if (distance < minDistance){
                minDistance = distance;
                closestFire = fire;
            }
        }
        return closestFire;
    }

    public static List<Fire> getListFires(){return listFires;}


    public static void setListFires(List<Fire> L){ 
        listFires = L;
    }
    public static List<Fire> getListFiresMemory(){return listFiresMemory;}

    /**
     * Reset the list of fires for an other simulation
     */
    public static void resetListFires(){listFires.clear();}

    /**
     * Remove a fire from the list
     * @param fire
     */
    public static void removeFire(Fire fire){
        listFires.remove(fire);
        numberFire--;
    }

    /**
     * Show the list of fires
     * @return String
     */
    public static String showListFires(){
        String listFiresString = "Number of fires : " + numberFire + "\n";
        for (Fire fire : listFires)
        {
            listFiresString += "Fire at position :\n" + fire.toString() + "\n"; 
        }
        return listFiresString;
    }

    @Override
    /**
     * toString methode
     * @return String
     */
    public String toString(){
        return this.currentPosition.toString(1) + "\n\t* Intensity : " + this.intensity;
    }

    /**
     * Will say if at the position there is a fire
     * @param row
     * @param column
     * @return boolean
     */
    public static boolean isFire(int row, int column){
        for (Fire fire : listFires){
            if (fire.getCurrentPosition().getRow() == row && fire.getCurrentPosition().getColumn() == column){
                return true;
            }
        }
        return false;
    }


    public static boolean isFire(Box box){
        return isFire(box.getRow(), box.getColumn());
    }

    public static List<Box> getListFireBox()
    {
        List<Box> listFireBox = new ArrayList<>();
        for (Fire fire : listFires)
        {
            listFireBox.add(fire.getCurrentPosition());
        }
        return listFireBox;
    }

    public static void resetAllFires()
    {
        listFires.clear();
        listFiresMemory.clear();
        numberFire = 0;
    }

    public void propagate(long currentTime) {
        // Récupère la carte actuelle
        Box[][] map = Map.getCurrentMap();
        int row = this.currentPosition.getRow();
        int column = this.currentPosition.getColumn();

        // Mélange les directions pour choisir une direction au hasard
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);

        // Essaye de propager dans la première direction possible
        for (Direction direction : directions) {
            int newRow = row + direction.getX();
            int newColumn = column + direction.getY();

            // Vérifie si la nouvelle case est valide pour recevoir un feu
            if (newRow >= 0 && newRow < map.length && newColumn >= 0 && newColumn < map[0].length) {
                Box adjacentBox = map[newRow][newColumn];
                if (adjacentBox.getNature() != TypeLand.WATER && adjacentBox.getNature() != TypeLand.STONE && !Fire.isFire(newRow, newColumn)
                        && !Robot.isRobot(newRow, newColumn) ) {
                    // Crée un nouvel incendie avec une intensité réduite
                    int newIntensity = this.intensity / 2; // Réduit l'intensité
                    if (newIntensity > 0) {
                        // Map.setFire(newRow, newColumn, newIntensity);
                        Fire fire = new Fire(Map.getBox(newRow, newColumn), newIntensity);
                        listFires.add(fire);
                        numberFire++;
                        break; // Propagation réussie, on arrête la boucle
                    }
                }
            }
        }
    }
}
