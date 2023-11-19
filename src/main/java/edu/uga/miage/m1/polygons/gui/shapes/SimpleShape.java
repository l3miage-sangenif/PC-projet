package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes. 
 * 
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public abstract class SimpleShape
{
    int mX;

    int mY;

    protected SimpleShape(int x, int y) {
        mX = x - 25;
        mY = y - 25;
    }

    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     **/
    public abstract void draw(Graphics2D g2);
    
    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public abstract void accept(Visitor visitor);

    public void setX(int x) {
        mX=x;
    }

    public void setY(int y) {
        mY=y;
    }
}