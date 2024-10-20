package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
public class Oval extends CenteredGraphicalElement
{
    private Color drawColor;
    private Color fillColor;
    private int width;
    private int height;

    public Oval(int paramInt1, int paramInt2, Color paramColor1, Color paramColor2, int paramInt3, int paramInt4) {
        super(paramInt1, paramInt2);
        this.drawColor = paramColor1;
        this.fillColor = paramColor2;
        this.width = paramInt3;
        this.height = paramInt4;
    }

    public Oval(int paramInt1, int paramInt2, Color paramColor1, Color paramColor2, int paramInt3) {
        this(paramInt1, paramInt2, paramColor1, paramColor2, paramInt3, paramInt3);
    }


    public void paint(Graphics2D paramGraphics2D) {
    Stroke stroke = paramGraphics2D.getStroke();
    paramGraphics2D.setStroke(new BasicStroke(2.0F));
    Color color = paramGraphics2D.getColor();
    if (this.fillColor != null) {
    paramGraphics2D.setColor(this.fillColor);
    paramGraphics2D.fillOval(getX() - this.width / 2, getY() - this.height / 2, this.width, this.height);
        } 
        paramGraphics2D.setColor(this.drawColor);
        paramGraphics2D.drawOval(getX() - this.width / 2, getY() - this.height / 2, this.width, this.height);
        paramGraphics2D.setColor(color);
        paramGraphics2D.setStroke(stroke);
    }


    public String toString() {
        return this.drawColor.toString() + " circle";
    }
}

