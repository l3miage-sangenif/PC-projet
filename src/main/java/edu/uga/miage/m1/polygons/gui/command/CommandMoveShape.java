package edu.uga.miage.m1.polygons.gui.command;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import edu.uga.miage.m1.polygons.gui.shapes.ListShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CommandMoveShape implements Command {

    private ListShapes shapes;
    private int coordXMove;
    private int coordYMove;
    MouseEvent evt;
    int[] coordXinit;
    int[] coordYInit;

    public CommandMoveShape(ListShapes shape, int xInit, int yInit, MouseEvent evt) {
        coordXinit= new int[shape.getList().size()];
        coordYInit= new int[shape.getList().size()];
        int i=0;
        for (SimpleShape s : shape.getList()) {
            coordXinit[i]=s.getX();
            coordYInit[i]=s.getY();
            i++;
        }
        this.shapes=shape;
        this.coordXMove=xInit;
        this.coordYMove=yInit;
        this.evt=evt;
    }

    @Override
    public void execute(ListShapes list, Graphics2D g2) {
        for (SimpleShape shape : shapes.getList()) {
            list.moveShape(shape, shape.getX()+evt.getX()-coordXMove, shape.getY()+evt.getY()-coordYMove);
        }
        list.executeAll(g2);
    }

    @Override
    public void undo(ListShapes list, Graphics2D g2) {
        int i=0;
        for (SimpleShape shape : shapes.getList()) {
            list.moveShape(shape, coordXinit[i], coordYInit[i]);
            i++;
        }
        list.executeAll(g2);
    }

    public boolean stackMultipleCommandMove(CommandMoveShape command) { // return true if the stack was possible and succesfull 
        command.evt=this.evt;
        return true;
    }

    public boolean stackMultipleCommand(Command command) { // return true if the stack was possible and succesfull 
        return command instanceof CommandMoveShape;
    }
    
}
