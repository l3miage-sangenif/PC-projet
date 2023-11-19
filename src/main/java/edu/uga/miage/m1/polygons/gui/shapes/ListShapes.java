package edu.uga.miage.m1.polygons.gui.shapes;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class ListShapes {

    private Deque<SimpleShape> list =  new ArrayDeque<>();

    private JPanel mPanel;


    public ListShapes(JPanel mPanel) {
        this.mPanel=mPanel;
    }

    public void addShape(SimpleShape shape) {
        list.addLast(shape);
    }

    public void remove(SimpleShape shape) {
        list.remove(shape);
    }

    public void executeAll(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, mPanel.getWidth(), mPanel.getHeight());
        for (SimpleShape simpleShape : list) {
            simpleShape.draw(g2);
        }
    }

    public Deque<SimpleShape> getList(){
        return list;
    }

    public void moveShape(SimpleShape shape, int x, int y) {
        list.remove(shape);
        shape.move(x,y);
        addShape(shape);
    }
}
