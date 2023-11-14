import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

class TestJsonVisitor {

    @Test
    void testCircle() {

        JSonVisitor jsonVisiteur = new JSonVisitor();

        Circle circle = new Circle(100,200);

        jsonVisiteur.visit(circle);

        String jsonResultat = "{\"type\":\"circle\",\"x\":75,\"y\":175}";

        Assertions.assertEquals(jsonVisiteur.getRepresentation(),jsonResultat);

    }

    @Test
    void testSquare() {

        JSonVisitor jsonVisiteur = new JSonVisitor();

        Square square = new Square(100,200);

        jsonVisiteur.visit(square);

        String jsonResultat = "{\"type\":\"square\",\"x\":75,\"y\":175}";

        Assertions.assertEquals(jsonVisiteur.getRepresentation(),jsonResultat);

    }

    @Test
    void testTriangle() {

        JSonVisitor jsonVisiteur = new JSonVisitor();

        Triangle triangle = new Triangle(100,200);

        jsonVisiteur.visit(triangle);

        String jsonResultat = "{\"type\":\"triangle\",\"x\":75,\"y\":175}";

        Assertions.assertEquals(jsonVisiteur.getRepresentation(),jsonResultat);

    }
    
}
