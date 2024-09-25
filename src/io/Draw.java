package io;

import gui.GUISimulator;
import gui.GraphicalElement;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import Robot.Robot;
import enumerator.TypeLand;
import fire.Fire;
import gui.ImageElement;
import map.Box;
import map.Map;

public class Draw {

    public static void drawMap(GUISimulator gui) 
    {
        gui.reset(); // clear the window
        Data dataMap = Map.getDataMap();

        int rows = dataMap.getRows();
        int columns = dataMap.getColumns();

        Box[][] currentMap = Map.getCurrentMap();
        int width = gui.getWidth();
        int height =(int) (gui.getHeight() - 150);

        // Calculate the width and height of each cell
        int widthLength = width / columns;
        int heightLength = height / rows;

        // Iterate over each row and column to draw the boxes

        for (int c = 0; c < columns; c +=1) {
            for (int l = 0; l < rows; l += 1) 
            {
                Box currentBox = currentMap[c][l];
                int flippedY = (rows - 1 - l) * heightLength; // To have a well oriented image
                TypeLand currentNatureTypeLand = currentBox.getNature();

                if (currentBox != null && currentNatureTypeLand != null) {
                    String[] filesName = currentNatureTypeLand.getFiles();

                    gui.addGraphicalElement(new ImageElement(c * widthLength, flippedY, filesName[0], widthLength, heightLength, gui));

                    if (currentNatureTypeLand.equals(TypeLand.HABITATION))
                    {
                        gui.addGraphicalElement(new ImageElement(c * widthLength, flippedY, "images/d8.png", widthLength, heightLength, gui));
                    }
                    boolean isFire  = Fire.isFire(c, l);
                    if (isFire)
                    {
                        gui.addGraphicalElement(new ImageElement(c * widthLength, flippedY, "images/f3.png", widthLength, heightLength, gui));   
                    }
                    if (Robot.isRobot(l, c))
                    {
                        String f = Robot.getRobotPostion(l, c).getFile();
                        gui.addGraphicalElement(new ImageElement(c * widthLength, flippedY, f, widthLength, heightLength, gui));
                    }
                }
            }
        }   
    }
    
}
