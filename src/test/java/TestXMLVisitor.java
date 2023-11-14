import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

class TestXMLVisitor {

    @Test
    void testCircle() {

        XMLVisitor xmlVisitor = new XMLVisitor();

        Circle circle = new Circle(100,200);

        xmlVisitor.visit(circle);

        String jsonResultat = "<shape><type>circle</type><x>75</x><y>175</y></shape>";

        Assertions.assertEquals(xmlVisitor.getRepresentation(),jsonResultat);

    }

    @Test
    void testSquare() {

        XMLVisitor xmlVisitor = new XMLVisitor();

        Square square = new Square(100,200);

        xmlVisitor.visit(square);

        String jsonResultat = "<shape><type>square</type><x>75</x><y>175</y></shape>";

        Assertions.assertEquals(xmlVisitor.getRepresentation(),jsonResultat);

    }

    @Test
    void testTriangle() {

        XMLVisitor xmlVisitor = new XMLVisitor();

        Triangle triangle = new Triangle(100,200);

        xmlVisitor.visit(triangle);

        String jsonResultat = "<shape><type>triangle</type><x>75</x><y>175</y></shape>";

        Assertions.assertEquals(xmlVisitor.getRepresentation(),jsonResultat);

    }
    
}
