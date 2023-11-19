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

class TestCommandMove {
    
    @Test
    void testMoveShape() throws FileNotFoundException {

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

        frame.mouseDragged(evt);
        MouseEvent evt2 = new MouseEvent(frame, 0, 0, 0, 60, 35, 0, false);
        frame.mouseDragged(evt2);

        MouseEvent newPlace = new MouseEvent(frame, 0, 0, 0, 150, 60, 0, false);
        frame.mouseDragged(newPlace);

        frame.mouseReleased(newPlace);

        frame.exportJson();

        String s="";
        File myObj = new File("jsonExport.json");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            s+= myReader.nextLine();
        }
        myReader.close();

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":125,\"y\":35}]}",s);

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

        frame.mouseDragged(evt);
        MouseEvent evt2 = new MouseEvent(frame, 0, 0, 0, 60, 35, 0, false);
        frame.mouseDragged(evt2);

        MouseEvent newPlace = new MouseEvent(frame, 0, 0, 0, 150, 60, 0, false);
        frame.mouseDragged(newPlace);

        frame.mouseReleased(newPlace);

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

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":25,\"y\":5}]}",s);

    }

    @Test
    void testCTRLY() throws FileNotFoundException {

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

        frame.mouseDragged(evt);
        MouseEvent evt2 = new MouseEvent(frame, 0, 0, 0, 60, 35, 0, false);
        frame.mouseDragged(evt2);

        MouseEvent newPlace = new MouseEvent(frame, 0, 0, 0, 150, 60, 0, false);
        frame.mouseDragged(newPlace);

        frame.mouseReleased(newPlace);

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
        try {
            robot = new Robot();        
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_Y);

            robot.keyRelease(KeyEvent.VK_Y);
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

        Assertions.assertEquals("{\"shapes\": [{\"type\":\"square\",\"x\":125,\"y\":35}]}",s);

    }

}
