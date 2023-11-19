package edu.uga.miage.m1.polygons.gui.command;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.shapes.ListShapes;

public interface Command {
    
    void execute(ListShapes list, Graphics2D g2);

    void undo(ListShapes list, Graphics2D g2);

}
