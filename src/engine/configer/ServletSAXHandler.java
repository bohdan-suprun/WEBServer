package engine.configer;

import engine.ServletConfig;
import engine.loader.ServletLoader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by bod on 06.09.15.
 */
public class ServletSAXHandler extends AbstractSAXHandler {

    @Override
    public void startDocument() throws SAXException {
        parsedXML = new LinkedList<ServletConfig>();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
            throws SAXException {
        if(qName.toLowerCase().equals("servlet")){
            String pattern = atts.getValue("urlpattern");
            String classname = atts.getValue("classname");
            parsedXML.add(new ServletConfig(classname, pattern));
        }
    }

}
