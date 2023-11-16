package edu.uga.miage.m1.polygons.gui.shapes;

import java.util.ArrayDeque;
import java.util.Deque;
import java.awt.Graphics2D;

public class ListShapes {

    private transient Deque<SimpleShape> list =  new ArrayDeque<>();


    public void addShape(SimpleShape shape) {
        list.addLast(shape);
    }

    public void remove(SimpleShape shape) {
        list.remove(shape);
    }
    
    public void moveShape(Circle shape, int x, int y) {
        remove(shape);
        addShape(new Circle(x, y));
    }

    public void moveShape(Square shape, int x, int y) {
        remove(shape);
        addShape(new Square(x, y));
    }

    public void moveShape(Triangle shape, int x, int y) {
        remove(shape);
        addShape(new Triangle(x, y));
    }

    public void executeAll(Graphics2D g2) {
        for (SimpleShape simpleShape : list) {
            simpleShape.draw(g2);
        }
    }

    public Deque<SimpleShape> getList(){
        return list;
    }

    public void moveShape(SimpleShape shape, int x, int y) {
        shape.move(x,y);
        //moveShape(shape, coordXInit, coordYInit);
    }
}
