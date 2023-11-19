package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import edu.uga.miage.m1.polygons.gui.command.Command;
import edu.uga.miage.m1.polygons.gui.command.CommandAddShape;
import edu.uga.miage.m1.polygons.gui.command.CommandMoveShape;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.ListShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    private enum Shapes {

        SQUARE, TRIANGLE, CIRCLE, CUBE
    }

    private static final long serialVersionUID = 1L;

    private JToolBar mToolbar;

    private Shapes mSelected;

    private JPanel mPanel;

    private JLabel mLabel;

    private transient ActionListener mReusableActionListener = new ShapeActionListener(this);

    private transient SimpleShape shapeToMove;

    private MouseEvent lastDraggedEvt;

    private static Logger logger;

    private transient ListShapes listShapes;

    private transient Deque<Command> listCommands =  new ArrayDeque<>();

    private transient Deque<Command> listCommandsUndo =  new ArrayDeque<>();

    /**
     * Tracks buttons to manage the background.
     */
    private EnumMap<Shapes, JButton> mButtons = new EnumMap<>(Shapes.class);

    /**
     * Default constructor that populates the main window.
     * 
     * @param frameName
     */
    public JDrawingFrame(String frameName) {
        super(frameName);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        // Instantiates components
        mToolbar = new JToolBar("Toolbar");
        mPanel = new JPanel();
        mPanel.setBackground(Color.WHITE);
        mPanel.setLayout(null);
        mPanel.setMinimumSize(new Dimension(400, 400));
        mPanel.addMouseListener(this);
        mPanel.addMouseMotionListener(this);
        mLabel = new JLabel(" ", 2);
        listShapes = new ListShapes(mPanel);
        // Fills the panel
        setLayout(new BorderLayout());
        add(mToolbar, BorderLayout.NORTH);
        add(mPanel, BorderLayout.CENTER);
        add(mLabel, BorderLayout.SOUTH);
        // Add shapes in the menu
        addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
        addShape(Shapes.CUBE, new ImageIcon(getClass().getResource("images/underc.png")));
        addExport("export json", (ActionEvent e) -> exportJson());
        addExport("export xml", (ActionEvent e) -> exportXml());
        setPreferredSize(new Dimension(400, 400));
    }

    public void exportJson() {
        Deque<SimpleShape> listCopy  = listShapes.getList();
        String jsonExport = "";
        StringBuilder bld = new StringBuilder();
        JSonVisitor jsonVisiteur = new JSonVisitor();
        for (SimpleShape s : listCopy) {
            s.accept(jsonVisiteur);
            if (s == listCopy.peekFirst()) {
                bld.append(jsonVisiteur.getRepresentation());
            } else {
                bld.append(",");
                bld.append(jsonVisiteur.getRepresentation());
            }
        }
        jsonExport = bld.toString();
        exportGlobal("{\"shapes\": [" + jsonExport + "]}", "jsonExport.json");
        this.requestFocusInWindow();
    }

    public void exportXml() {
        Deque<SimpleShape> listCopy  = listShapes.getList();
        String xmlExport = "";
        StringBuilder bld = new StringBuilder();
        XMLVisitor xmlVisitor = new XMLVisitor();
        for (SimpleShape s : listCopy) {
            s.accept(xmlVisitor);
            bld.append(xmlVisitor.getRepresentation());
        }
        xmlExport = bld.toString();
        exportGlobal("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><shapes>" + xmlExport + "</shapes></root>",
                "xmlExport.xml");
        this.requestFocusInWindow();
    }

    private void exportGlobal(String s, String file) {
        try (FileWriter myWriter = new FileWriter(file);) {
            myWriter.write(s);

        } catch (IOException e) {
            logger.log(null, "an error append");
        }
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     * 
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        mButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(mReusableActionListener);
        if (mSelected == null) {
            button.doClick();
        }
        mToolbar.add(button);
        mToolbar.validate();
    }

    private void addExport(String text, ActionListener export) {
        JButton button = new JButton(text);
        button.setBorderPainted(false);
        button.addActionListener(export);
        mToolbar.add(button);
        mToolbar.validate();
        repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (mPanel.contains(evt.getX(), evt.getY())) {
            Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
            Command addShape;
            switch (mSelected) {
                case CIRCLE:
                    Circle c = new Circle(evt.getX(), evt.getY());
                    addShape = new CommandAddShape(c);
                    addShape.execute(listShapes, g2);
                    listCommands.addFirst(addShape);
                    break;
                case TRIANGLE:
                    Triangle t = new Triangle(evt.getX(), evt.getY());
                    addShape = new CommandAddShape(t);
                    addShape.execute(listShapes, g2);
                    listCommands.addFirst(addShape);
                    break;
                case SQUARE:
                    Square s = new Square(evt.getX(), evt.getY());
                    addShape = new CommandAddShape(s);
                    addShape.execute(listShapes, g2);
                    listCommands.addFirst(addShape);
                    break;
                case CUBE:
                    Cube c2 = new Cube(evt.getX(), evt.getY());
                    addShape = new CommandAddShape(c2);
                    addShape.execute(listShapes, g2);
                    listCommands.addFirst(addShape);
                    break;
                default:
                    logger.log(null, "No shape named " + mSelected);
            }
            listCommandsUndo =  new ArrayDeque<>();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_Z) 
            && (e.isControlDown())
            && !listCommands.isEmpty()) {
                Command command = listCommands.pollFirst();
                Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, mPanel.getWidth(), mPanel.getHeight());
                command.undo(listShapes, g2);
                listCommandsUndo.addFirst(command);
        }
        if ((e.getKeyCode() == KeyEvent.VK_Y) 
            && (e.isControlDown())
            && !listCommandsUndo.isEmpty()) {
                Command command = listCommandsUndo.pollFirst();
                Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
                command.execute(listShapes, g2);
                listCommands.addFirst(command);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but must be implemented
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used, but must be implemented
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        // Do nothing
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        mLabel.setText(" ");
        mLabel.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     */
    public void mousePressed(MouseEvent evt) {
        // Do nothing
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        shapeToMove = null;
        lastDraggedEvt = null;
        // Do nothing
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        SimpleShape shapeInit = shapeToMove;
        if (shapeToMove==null)
            for (SimpleShape simpleShape : listShapes.getList()) {
                if(simpleShape.getX()>=evt.getX()-50 && 
                    simpleShape.getX()<=evt.getX() && 
                    simpleShape.getY()>=evt.getY()-50 && 
                    simpleShape.getY()<=evt.getY()) {
                        shapeToMove=simpleShape;
                }
            }
        if (shapeToMove != null) {
            if (lastDraggedEvt == null) {
                lastDraggedEvt = evt;
            }
            CommandMoveShape moveShape = new CommandMoveShape(shapeToMove, lastDraggedEvt.getX(), lastDraggedEvt.getY(), evt);
            Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
            moveShape.execute(listShapes, g2);
            // add a move command only once and then stack it to make ctrl+z and ctrl+y better
            if(moveShape.stackMultipleCommand(listCommands.peekFirst()) && shapeInit!=null){
                moveShape.stackMultipleCommandMove((CommandMoveShape) listCommands.peekFirst());
            }
            else {
                listCommands.addFirst(moveShape);
            }
            lastDraggedEvt=evt;
        }
        listCommandsUndo =  new ArrayDeque<>();
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        mLabel.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    public void setSelectedShape(ActionEvent evt) {
        Iterator<Shapes> keys = mButtons.keySet().iterator();
        while (keys.hasNext()) {
            Shapes shape = keys.next();
            JButton btn = mButtons.get(shape);
            if (evt.getActionCommand().equals(shape.toString())) {
                btn.setBorderPainted(true);
                mSelected = shape;
            } else {
                btn.setBorderPainted(false);
            }
            btn.repaint();
        }
        this.requestFocusInWindow();
    }

    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener implements ActionListener {

        private JDrawingFrame jDrawingFrame;

        public ShapeActionListener(JDrawingFrame jDrawingFrame) {
            this.jDrawingFrame = jDrawingFrame;
        }

        public void actionPerformed(ActionEvent evt) {
            // Itère sur tous les boutons
            jDrawingFrame.setSelectedShape(evt);
        }
    }
}
