package edu.uga.miage.m1.polygons.gui.command;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import edu.uga.miage.m1.polygons.gui.shapes.ListShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CommandMoveShape implements Command {

    private SimpleShape shape;
    private int coordXInit;
    private int coordYInit;
    private MouseEvent evt;

    public CommandMoveShape(SimpleShape shape, int xInit, int yInit, MouseEvent evt) {
        this.shape=shape;
        this.coordXInit=xInit;
        this.coordYInit=yInit;
        this.evt=evt;
    }

    @Override
    public void execute(ListShapes list, Graphics2D g2) {
        list.moveShape(shape, shape.getX()+evt.getX()-coordXInit, shape.getY()+evt.getY()-coordYInit);
        list.executeAll(g2);
    }

    @Override
    public void undo(ListShapes list, Graphics2D g2) {
        list.moveShape(shape, coordXInit, coordYInit);
        list.executeAll(g2);
    }

    public boolean stackMultipleCommand(Command command) { // return true if the stack was possible and succesfull 
        return true;
    }
    
}
