package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Text extends CenteredGraphicalElement
{
    private Color color;
    private String text;

    public Text(int paramInt1, int paramInt2, Color paramColor, String paramString) {
        super(paramInt1, paramInt2);
        this.color = paramColor;
        this.text = paramString;
    }

    public void paint(Graphics2D paramGraphics2D) {
        Color color = paramGraphics2D.getColor();
        paramGraphics2D.setColor(this.color);
        FontMetrics fontMetrics = paramGraphics2D.getFontMetrics();
        Rectangle2D rectangle2D = fontMetrics.getStringBounds(this.text, paramGraphics2D);
        int i = getX() - (int)rectangle2D.getWidth() / 2;
        int j = getY() - (int)rectangle2D.getHeight() / 2 + fontMetrics.getAscent();
        paramGraphics2D.drawString(this.text, i, j);
        paramGraphics2D.setColor(color);
    }

    public String toString() {
        return "text - " + this.text;
    }
}

