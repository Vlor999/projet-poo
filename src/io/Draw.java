package io;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Text;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import enumerator.TypeLand;
import fire.Fire;
import map.Box;
import map.Map;
import robot.Robot;

public class Draw {

    private static int numberToShow = 0;
    /**
     * Will show and end display to explain that there is not any fires
     * @param gui
     */
    public static void end(GUISimulator gui)
    {
        gui.reset();
        gui.addGraphicalElement(new Text(gui.getWidth() / 2, gui.getHeight() / 2, Color.WHITE, "End No More Fires"));
    }
    
    public static void restartDisplay(GUISimulator gui)
    {
        gui.reset();
        gui.addGraphicalElement(new Text(gui.getWidth() / 2, gui.getHeight() / 2, Color.WHITE, "Restart"));
        gui.addGraphicalElement(new Text(gui.getWidth() / 2, gui.getHeight() / 2 + 50, Color.WHITE, "Press Lecture to restart"));
    }

    /**
     * Draw the map with the boxes and the robots but also the fires water and other stuff
     * @param gui
     */
    public static void drawMap(GUISimulator gui) 
    {
        gui.reset(); // clear the window
        Data dataMap = Map.getDataMap();

        int rows = dataMap.getRows();
        int columns = dataMap.getColumns();

        Box[][] currentMap = Map.getCurrentMap();
        int widthOffset = 75;
        int heightOffset = 150;
        int width = gui.getWidth() - widthOffset;
        int height = gui.getHeight() - heightOffset;

        // Calculate the width and height of each cell
        int widthLength = width / columns;
        int heightLength = height / rows;
        int compteur = 0;
        List<Robot> listRobots = Robot.getListRobots();
        List<String> passed = new ArrayList<>();
        for (Robot r: listRobots)
        {
            if (passed.contains(r.getType()))
            {
                continue;
            }
            gui.addGraphicalElement(new ImageElement(width, heightLength * compteur * 2, TypeLand.FIELD.getFiles()[numberToShow], widthLength, heightLength, gui));
            gui.addGraphicalElement(new ImageElement(width, heightLength * compteur * 2, r.getFiles(numberToShow),widthLength, heightLength, gui));
            gui.addGraphicalElement(new Text(width + widthOffset / 3, heightLength * (compteur *2 + 1), Color.WHITE, r.getType()));
            compteur++;
            passed.add(r.getType());
        }
        

        // Iterate over each row and column to draw the boxes
        for (int c = 0; c < columns; c +=1) {
            for (int l = 0; l < rows; l += 1) 
            {
                Box currentBox = currentMap[c][l];
                int flippedY = (rows - 1 - l); // To have a well oriented image
                TypeLand currentNatureTypeLand = currentBox.getNature();

                if (currentBox != null && currentNatureTypeLand != null) {
                    String[] filesName = currentNatureTypeLand.getFiles();

                    // Here we draw the box with the nature.
                    // The value 0 represent the first set of images for the nature
                    // If we want to change and add some other images we can change the value but we also have to add the files
                    gui.addGraphicalElement(new ImageElement(c * widthLength, flippedY * heightLength, filesName[numberToShow], widthLength, heightLength, gui));

                    boolean isFire  = Fire.isFire(c, l);

                    if (isFire)
                    {
                        int number = (int)(Math.random() * 3 + 0.5); 
                        String randomfile = Fire.getFiles()[number];
                        gui.addGraphicalElement(new ImageElement(c * widthLength, flippedY * heightLength, randomfile, widthLength, heightLength, gui));   
                    }

                    List<Robot> robots = Robot.getListRobotsBox(currentBox);
                    int number = robots.size();
                    if (number > 0)
                    {
                        
                        int cols = (int)Math.ceil(Math.sqrt(number));
                        int r = (int)Math.ceil((double)number / cols);

                        int imageWidth = widthLength / cols;
                        int imageHeight = heightLength / r;

                        // Draw the robots and if they are on the same bow the they will be draw with a smaller size
                        for (int i = 0; i < number; i++)
                        {
                            int row = i / cols;
                            int col = i % cols;

                            int xOffset = col * imageWidth;
                            int yOffset = row * imageHeight;

                            gui.addGraphicalElement(new ImageElement(
                                c * widthLength + xOffset, 
                                flippedY * heightLength + yOffset, 
                                robots.get(i).getFiles(numberToShow), 
                                imageWidth, 
                                imageHeight, 
                                gui
                            ));
                        }
                    }

                }
            }
        }   
    }

    public static void setNumberToShow(int number)
    {
        numberToShow = number;
    }
}
