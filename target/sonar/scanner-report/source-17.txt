import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

class TestJDrawing {
    

    @Test
    void testExportJson() throws FileNotFoundException {

        JDrawingFrame frame = new JDrawingFrame("test");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);

        MouseEvent evt = new MouseEvent(frame, 0, 0, 0, 50, 30, 0, false);
        frame.mouseClicked(evt);

        frame.exportJson();

        String s="";
        File myObj = new File("jsonExport.json");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":25,\"y\":5}]}",s);

    }

    @Test
    void testExportXML() throws FileNotFoundException {

        JDrawingFrame frame = new JDrawingFrame("test");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);

        MouseEvent evt = new MouseEvent(frame, 0, 0, 0, 50, 30, 0, false);
        frame.mouseClicked(evt);

        frame.exportXml();

        String s="";
        File myObj = new File("xmlExport.xml");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><shapes><shape><type>square</type><x>25</x><y>5</y></shape></shapes></root>",s);

    }

    @Test
    void testMoreShapes() throws FileNotFoundException {

        JDrawingFrame frame = new JDrawingFrame("test");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);

        MouseEvent evt = new MouseEvent(frame, 0, 0, 0, 50, 30, 0, false);
        frame.mouseClicked(evt);

        ActionEvent evtA = new ActionEvent(evt, 0, "TRIANGLE");
        frame.setSelectedShape(evtA);

        evt = new MouseEvent(frame, 0, 0, 0, 100, 50, 0, false);
        frame.mouseClicked(evt);
        
        evtA = new ActionEvent(evt, 0, "CIRCLE");
        frame.setSelectedShape(evtA);

        evt = new MouseEvent(frame, 0, 0, 0, 120, 60, 0, false);
        frame.mouseClicked(evt);

        frame.exportJson();

        String s="";
        File myObj = new File("jsonExport.json");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":25,\"y\":5},{\"type\":\"triangle\",\"x\":75,\"y\":25},{\"type\":\"circle\",\"x\":95,\"y\":35}]}",s);

    }

    @Test
    void testCTRLZ() throws FileNotFoundException {

        JDrawingFrame frame = new JDrawingFrame("test");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);
        MouseEvent evt = new MouseEvent(frame, 0, 0, 0, 50, 30, 0, false);
        frame.mouseClicked(evt);
        ActionEvent evtA = new ActionEvent(evt, 0, "TRIANGLE");
        frame.setSelectedShape(evtA);
        evt = new MouseEvent(frame, 0, 0, 0, 100, 50, 0, false);
        frame.mouseClicked(evt);
        evtA = new ActionEvent(evt, 0, "CIRCLE");
        frame.setSelectedShape(evtA);
        evt = new MouseEvent(frame, 0, 0, 0, 120, 60, 0, false);
        frame.mouseClicked(evt);
        evt = new MouseEvent(frame, 0, 0, 0, 130, 60, 0, false);
        frame.mouseClicked(evt);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        Robot robot;
        try {
            robot = new Robot();        
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_Z);

            robot.keyRelease(KeyEvent.VK_Z);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        frame.exportJson();

        String s="";
        File myObj = new File("jsonExport.json");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":25,\"y\":5},{\"type\":\"triangle\",\"x\":75,\"y\":25},{\"type\":\"circle\",\"x\":95,\"y\":35}]}",s);

    }

    @Test
    void testCTRLZStart() throws FileNotFoundException {

        JDrawingFrame frame = new JDrawingFrame("test");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);

        Robot robot;
        try {
            robot = new Robot();        
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_Z);

            robot.keyRelease(KeyEvent.VK_Z);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        

        
        MouseEvent evt = new MouseEvent(frame, 0, 0, 0, 50, 30, 0, false);
        frame.mouseClicked(evt);
        ActionEvent evtA = new ActionEvent(evt, 0, "TRIANGLE");
        frame.setSelectedShape(evtA);
        evt = new MouseEvent(frame, 0, 0, 0, 100, 50, 0, false);
        frame.mouseClicked(evt);
        
        frame.exportJson();

        String s="";
        File myObj = new File("jsonExport.json");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":25,\"y\":5},{\"type\":\"triangle\",\"x\":75,\"y\":25}]}",s);

    }

    @Test
    void testClickMiss() throws FileNotFoundException {

        JDrawingFrame frame = new JDrawingFrame("test");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);

        MouseEvent evt = new MouseEvent(frame, 0, 0, 0, -30, -50, 0, false);
        frame.mouseClicked(evt);

        frame.exportJson();

        String s="";
        File myObj = new File("jsonExport.json");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("{\"shapes\": []}",s);

    }
    
}
