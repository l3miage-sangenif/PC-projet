package edu.uga.miage.m1.polygons.gui.persistence;

import javax.json.Json;

import javax.json.JsonObjectBuilder;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private String representation;

    public JSonVisitor() {
        representation = null;
    }

    @Override
    public void visit(Circle circle) {
        representation = createJsonObjectBuilder(circle, "circle");
    }

    @Override
    public void visit(Square square) {
        representation = createJsonObjectBuilder(square, "square");
    }

    @Override
    public void visit(Triangle triangle) {
        representation = createJsonObjectBuilder(triangle, "triangle");
    }

    /**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */
    public String getRepresentation() {
        return representation;
    }

    public String createJsonObjectBuilder(SimpleShape shape , String type) {
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("type", type)
            .add("x", shape.getX()).add("y", shape.getY());
        
        return json.build().toString();
    }

}