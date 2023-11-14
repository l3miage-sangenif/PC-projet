package edu.uga.miage.m1.polygons.gui.persistence;


import java.io.StringWriter;
import java.lang.System.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation;

    private static Logger logger;

    public XMLVisitor() {
      representation = null;
    }

    @Override
    public void visit(Circle circle) {
      representation = this.createXmlFormat(circle, "circle");
    }

    @Override
    public void visit(Square square) {
      representation = this.createXmlFormat(square, "square");
    }

    @Override
    public void visit(Triangle triangle) {
      representation = this.createXmlFormat(triangle, "triangle");
    }

    /**
     * @return the representation in JSon example for a Triangle:
     *
     *         <pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */
    public String getRepresentation() {
        return representation;
    }

    public String createXmlFormat(SimpleShape shape , String name) {
      String xmlString= "";
      try {
            Element racine = null;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            // Ã©lÃ©ment de racine
            Document doc = docBuilder.newDocument();
            racine = doc.createElement("shape");
            doc.appendChild(racine);

            // le type
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(name));
            racine.appendChild(type);

              // x
            Element x = doc.createElement("x");
            x.appendChild(doc.createTextNode(String.valueOf(shape.getX())));
            racine.appendChild(x);

              // y
            Element y = doc.createElement("y");
            y.appendChild(doc.createTextNode(String.valueOf(shape.getY())));
            racine.appendChild(y);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(racine), new StreamResult(writer));

            xmlString = writer.toString();

        } catch (ParserConfigurationException | TransformerException e) {
          logger.log(null,"error append");
        }
      return xmlString;
    }

}
