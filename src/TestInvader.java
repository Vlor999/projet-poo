import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;
import java.awt.image.ImageObserver;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import io.Data;
import io.LecteurDonnees;
import map.Box;
import map.Map;


public class TestInvader {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        // Invader invader = new Invader(gui, Color.decode("#f2ff28"));
        String txt = "cartes/carteSujet.map";
        LecteurDonnees.lire(txt);
        new Invader(gui, Color.decode("#f2ff28"));
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
        draw();
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
        // going rightimport java.awt.image.ImageObserver;
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
        draw();
    }

    @Override
    public void restart() {
        planCoordinates();
        draw();
        drawrect(0, 0, 50, 50, Color.BLUE);
    }

    /**
     * Drw a rectangle and it could be useful for the fires, robot, water and others.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     */
    public void drawrect(int x, int y, int width, int height, Color color) {
        gui.addGraphicalElement(new Rectangle(x, y, color, color, width));
    }
    /**
     * Dessine l'invader.
     */
    private void draw() {
        gui.reset();	// clear the window
        drawMap();

        gui.addGraphicalElement(new Rectangle(x + 30, y     , invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y     , invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y     , invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x + 20, y + 10, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 30, y + 10, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 10, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y + 10, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 10, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x + 10, y + 20, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 20, y + 20, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 30, y + 20, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 20, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y + 20, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 20, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 70, y + 20, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x     , y + 30, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 10, y + 30, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 30, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 70, y + 30, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 80, y + 30, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x     , y + 40, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 10, y + 40, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 40, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 70, y + 40, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 80, y + 40, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x     , y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 10, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 20, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 30, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 70, y + 50, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 80, y + 50, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x + 20, y + 60, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 30, y + 60, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 60, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y + 60, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 60, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x + 10, y + 70, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 20, y + 70, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 30, y + 70, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 70, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y + 70, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 70, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 70, y + 70, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x + 10, y + 80, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 20, y + 80, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 30, y + 80, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 40, y + 80, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 50, y + 80, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 80, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 70, y + 80, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Rectangle(x     , y + 90, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 20, y + 90, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 60, y + 90, invaderColor, invaderColor, 10));
        gui.addGraphicalElement(new Rectangle(x + 80, y + 90, invaderColor, invaderColor, 10));

        gui.addGraphicalElement(new Text(x + 40, y + 120, invaderColor, "INVADER"));
    }

    private void drawMap() 
    {
        Data dataMap = Map.getDataMap();
        int rows = dataMap.getRows();
        int columns = dataMap.getColumns();
        Box[][] currentMap = Map.getCurrentMap();
        int width = gui.getWidth();
        int height = gui.getHeight();

        // Calculate the width and height of each cell
        int widthLength = width / columns;
        int heightLength = height / rows;

        // Iterate over each row and column to draw the boxes
        for (int c = 0; c < columns; c++) {
            for (int l = 0; l < rows; l++) 
            {
                Box currentBox = currentMap[c][l];
                if (currentBox != null && currentBox.getNature() != null) {
                    String fileName = "images/base_grass_flat_E.png";
                    ImageObserver obs;
                    gui.addGraphicalElement();
                    // Color color = currentBox.getNature().getColor();
                    
                    // // Flip the y-axis by subtracting l from rows - 1
                    // int flippedY = (rows - 1 - l) * heightLength;

                    // // Add the graphical element with the correct position and size
                    // gui.addGraphicalElement(new Rectangle(
                    //         c * widthLength,  // X position (same)
                    //         flippedY,         // Y position (flipped)
                    //         color,            // Fill color
                    //         color,            // Border color
                    //         widthLength,      // Width of the rectangle
                    //         heightLength      // Height of the rectangle
                    // ));
                }
            }
        }   
    }

}
