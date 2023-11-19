package edu.uga.miage.m1.polygons.gui.command;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.shapes.ListShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CommandAddShape implements Command {

    private SimpleShape shape;

    public CommandAddShape(SimpleShape shape) {
        this.shape=shape;
    }

    @Override
    public void execute(ListShapes list, Graphics2D g2) {
        shape.draw(g2);
        list.addShape(shape);
    }

    @Override
    public void undo(ListShapes list, Graphics2D g2) {
        list.remove(shape);
        list.executeAll(g2);
    }
    
}
