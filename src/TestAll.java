import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

import map.Box;

import Robot.Robot;
import fire.Fire;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import io.Data;
import io.LecteurDonnees;
import map.Map;


public class TestAll {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        int width = 800;
        int height = 600;
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        
        for (int i = 0; i < args.length; i++)
        {
            try {
                GUISimulator gui = new GUISimulator(width, height, Color.BLACK);
                LecteurDonnees.lire(args[i]);
                System.out.println(Fire.showListFires());

                new Invader(gui, Color.BLACK);

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


/**
 * Un mini-invader...
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et 
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 */
class Invader implements Simulable {
    /** L'interface graphique associée */
    private GUISimulator gui;	

    /** La couleur de dessin de l'invader */
    private Color invaderColor;	

    /** Abcisse courante de l'invader (bord gauche) */
    private int x;

    /** Ordonnée courante de l'invader (bord supérieur) */
    private int y;

    /** Itérateur sur les abcisses de l'invader au cours du temps */
    private Iterator<Integer> xIterator;

    /** Itérateur sur les ordonnées de l'invader au cours du temps */
    private Iterator<Integer> yIterator;

    /**
     * Crée un Invader et le dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur de l'invader
     */
    public Invader(GUISimulator gui, Color invaderColor) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.invaderColor = invaderColor;

        planCoordinates();
        drawMap();
    }

    /**
     * Programme les déplacements de l'invader. 
     */
    private void planCoordinates() {
        // panel must be large enough... unchecked here!
        // total invader size: height == 120, width == 80
        int xMin = 60;
        int yMin = 40;
        int xMax = gui.getWidth() - xMin - 80;
        xMax -= xMax % 10;
        int yMax = gui.getHeight() - yMin - 120;
        yMax -= yMax % 10;

        // let's plan the invader displacement!
        List<Integer> xCoords = new ArrayList<Integer>();
        List<Integer> yCoords = new ArrayList<Integer>();
        // going right
        for (int x = xMin + 10; x <= xMax; x += 10) {
            xCoords.add(x);
            yCoords.add(yMin);
        }
        // going down
        for (int y = yMin + 10; y <= xMin + 150; y += 10) {
            xCoords.add(xMax);
            yCoords.add(y);
        }
        // going left
        for (int x = xMax - 10; x >= xMin; x -= 10) {
            xCoords.add(x);
            yCoords.add(yMin + 170);
        }

        this.xIterator = xCoords.iterator();
        this.yIterator = yCoords.iterator();
        // current position
        this.x = xMin;
        this.y = yMin;		
    }

    @Override
    public void next() {
        if (this.xIterator.hasNext())
            this.x = this.xIterator.next();		
        if (this.yIterator.hasNext())
            this.y = this.yIterator.next();		
        drawMap();
    }

    @Override
    public void restart() {
        planCoordinates();
        drawMap();
    }

    private void drawMap()
    {
        Data dataMap = Map.getDataMap();
        int rows = dataMap.getRows();
        int columns = dataMap.getColumns();
        Box[][] currentMap = Map.getCurrentMap();
        int width = gui.getWidth();
        int height = (int) (gui.getHeight() * 0.8);

        int widthLength = width / columns;
        int heightLength = height / rows;
        for (int l = 0; l < rows; l +=1)
        {
            for (int c = 0; c < columns; c +=1)
            {
                Color color = currentMap[l][c].getNature().getColor();
                gui.addGraphicalElement(new Rectangle(c * widthLength, l * heightLength, color, color, widthLength, heightLength));
            }
        }
    }

}
