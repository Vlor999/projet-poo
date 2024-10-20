package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
public class ImageElement
extends CenteredGraphicalElement
{
    private Image image;
    private double width;
    private double height;
    private ImageObserver observer;

    public ImageElement(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, ImageObserver paramImageObserver) {
        super(paramInt1, paramInt2);
        this.image = Toolkit.getDefaultToolkit().getImage(paramString);
        this.width = paramInt3;
        this.height = paramInt4;
        this.observer = paramImageObserver;
    }


    public void paint(Graphics2D paramGraphics2D) {
        int i = this.image.getWidth(this.observer);
        int j = this.image.getHeight(this.observer);
        AffineTransform affineTransform = AffineTransform.getTranslateInstance(getX(), getY());
        affineTransform.concatenate(AffineTransform.getScaleInstance(this.width / i, this.height / j));
        
        paramGraphics2D.drawImage(this.image, affineTransform, this.observer);
    }


    public String toString() {
        return this.image.toString() + " image";
    }
}

