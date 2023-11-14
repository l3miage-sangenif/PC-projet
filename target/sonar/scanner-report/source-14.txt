import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

class TestShapes {
    @Test
    void testCircle() {
        Circle circle = new Circle(100,200);

        JPanel mPanel = new JPanel();
        mPanel.setBackground(Color.WHITE);
        mPanel.setLayout(null);
        mPanel.setMinimumSize(new Dimension(400, 400));

        Graphics2D g2 = (Graphics2D) mPanel.getGraphics();

        Assertions.assertEquals(75,circle.getX());
        Assertions.assertEquals(175,circle.getY());
    }

    @Test
    void testSquare() {
        Square square = new Square(100,200);

        JPanel mPanel = new JPanel();
        mPanel.setBackground(Color.WHITE);
        mPanel.setLayout(null);
        mPanel.setMinimumSize(new Dimension(400, 400));

        Graphics2D g2 = (Graphics2D) mPanel.getGraphics();

        Assertions.assertEquals(75,square.getX());
        Assertions.assertEquals(175,square.getY());
    }
}
