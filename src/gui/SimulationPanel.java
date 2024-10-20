package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.JPanel;

class SimulationPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Collection<GraphicalElement> shapes;

    protected SimulationPanel(int paramInt1, int paramInt2, Color paramColor) {
        this.bgColor = paramColor;
        this.width = paramInt1;
        this.height = paramInt2;
        setPreferredSize(new Dimension(paramInt1, paramInt2));
        reset();
    }
    private int width; private int height; private Color bgColor;
    protected void reset() {
        this.shapes = new LinkedList<>();
        repaint();
    }

    protected void addGraphicalElement(GraphicalElement paramGraphicalElement) {
        synchronized (this.shapes) {
        this.shapes.add(paramGraphicalElement);
        } 
        repaint();
    }

    public void paintComponent(Graphics paramGraphics) {
        super.paintComponent(paramGraphics);
        Graphics2D graphics2D = (Graphics2D)paramGraphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics2D.setColor(this.bgColor);
        graphics2D.fillRect(0, 0, this.width, this.height);
        synchronized (this.shapes) {
        for (GraphicalElement graphicalElement : this.shapes) {
            graphicalElement.paint(graphics2D);
        }
        } 
        graphics2D.dispose();
    }
}

