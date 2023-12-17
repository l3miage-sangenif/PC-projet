package edu.uga.miage.m1.polygons.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class ImportJsonComposant {

    private static Logger logger;

    private JDrawingFrame frame;

    public ImportJsonComposant(JDrawingFrame frame) {
        this.frame = frame;
    }

    public boolean importJson(String fileString) {
        StringBuilder content = new StringBuilder();
        File myObj = new File(fileString);
        try (Scanner myReader = new Scanner(myObj)) {
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            content.append(data);
            }
        } catch (FileNotFoundException e) {
            logger.log(null,"not a valide file");
            return false;
        }
        try {
            String listShapeJson=content.substring(13, content.length() -3);
            String[] listString = listShapeJson.split("\\},\\{");
            for (String stringShape : listString) {
                String type = stringShape.split(",")[0].split(":")[1].replace("\"", "");
                String x = stringShape.split(",")[1].split(":")[1].replace("\"", "");
                String y = stringShape.split(",")[2].split(":")[1].replace("\"", "");
                
                MouseEvent evt = new MouseEvent(frame, 0, 0, 0, 50, 30, 0, false);
                ActionEvent evtA = new ActionEvent(evt, 0, type.toUpperCase());
                frame.setSelectedShape(evtA);
                evt = new MouseEvent(frame, 0, 0, 0, Integer.parseInt(x)+25, Integer.parseInt(y)+25, 0, false);
                frame.mouseClicked(evt);
            }
        } catch (Exception e) {
            logger.log(null,"problem in the file content");
            return false;
        }
        return true;
    }

    public boolean importJson() {
        return importJson("jsonExport.json");
    }
    
}
