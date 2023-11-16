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

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
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

    private transient Deque<SimpleShape> list =  new ArrayDeque<>();

    private JToolBar mToolbar;

    private Shapes mSelected;

    private JPanel mPanel;

    private JLabel mLabel;

    private transient ActionListener mReusableActionListener = new ShapeActionListener(this);

    private transient SimpleShape shapeToMove;

    private MouseEvent lastDraggedEvt;

    private static Logger logger;

    

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
        Deque<SimpleShape> listCopy = new ArrayDeque<>(list);
        String jsonExport = "";
        StringBuilder bld = new StringBuilder();
        JSonVisitor jsonVisiteur = new JSonVisitor();
        for (SimpleShape s : listCopy) {
            s.accept(jsonVisiteur);
            if (s== listCopy.peekFirst()) {
                bld.append(jsonVisiteur.getRepresentation());
            }
            else {
                bld.append(",");
                bld.append(jsonVisiteur.getRepresentation());
            }          
        }
        jsonExport = bld.toString();
        exportGlobal("{\"shapes\": [" + jsonExport + "]}", "jsonExport.json");
        this.requestFocusInWindow();
    }

    public void exportXml() {
        String xmlExport = "";
        StringBuilder bld = new StringBuilder();
        XMLVisitor xmlVisitor = new XMLVisitor();
        for (SimpleShape s : list) {
            s.accept(xmlVisitor);
            bld.append(xmlVisitor.getRepresentation());
        }
        xmlExport = bld.toString();
        exportGlobal("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><shapes>" + xmlExport + "</shapes></root>", "xmlExport.xml");
        this.requestFocusInWindow();
    }

    private void exportGlobal(String s, String file) {
        try (FileWriter myWriter = new FileWriter(file);){
            myWriter.write(s);

        } catch (IOException e) {
            logger.log(null,"an error append");
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
            switch (mSelected) {
                case CIRCLE:
                    Circle c = new Circle(evt.getX(), evt.getY());
                    c.draw(g2);
                    list.addLast(c);
                    break;
                case TRIANGLE:
                    Triangle t = new Triangle(evt.getX(), evt.getY());
                    t.draw(g2);
                    list.addLast(t);
                    break;
                case SQUARE:
                    Square s = new Square(evt.getX(), evt.getY());
                    s.draw(g2);
                    list.addLast(s);
                    break;
                case CUBE:
                    Cube c2 = new Cube(evt.getX(), evt.getY());
                    c2.draw(g2);
                    list.addLast(c2);
                    break;
                default:
                    logger.log(null, "No shape named " + mSelected);
            }
        }
    }

    private void undoLastShape() {        
        if (!list.isEmpty()) {
            list.pollLast();
            playUndo(); 
        }
    }

    private void playUndo() {
        Graphics2D g2 = (Graphics2D) mPanel.getGraphics();

        // Clear the canvas by filling it with a white background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, mPanel.getWidth(), mPanel.getHeight());
        for (SimpleShape s : list) {
            s.draw(g2);            
        }

        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown())) {
            // Ctrl+Z is pressed
            undoLastShape();
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
        shapeToMove=null;
        lastDraggedEvt=null;
        // Do nothing
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        if (shapeToMove==null)
            for (SimpleShape simpleShape : list) {
                if(simpleShape.getX()>=evt.getX()-50 && 
                    simpleShape.getX()<=evt.getX() && 
                    simpleShape.getY()>=evt.getY()-50 && 
                    simpleShape.getY()<=evt.getY()) {
                        shapeToMove=simpleShape;
                }
            }
        if (shapeToMove!=null){
            if (lastDraggedEvt==null){
                lastDraggedEvt=evt;
            }
            shapeToMove.setX(shapeToMove.getX()+evt.getX()-lastDraggedEvt.getX());
            shapeToMove.setY(shapeToMove.getY()+evt.getY()-lastDraggedEvt.getY());
            lastDraggedEvt=evt;
            list.remove(shapeToMove);
            list.addLast(shapeToMove);
            playUndo();
        }
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
