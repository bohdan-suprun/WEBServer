package engine.configer;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by bod on 04.09.15.
 */
public class ConfigReader {
    private static ConfigReader self;
    private SAXParser parser;
    private ConfigReader(){
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ConfigReader getConfigReader(){
        return (self == null)? self=new ConfigReader(): self;
    }

    public Map<String, String> read(String filePath, DefaultHandler handler) throws IOException{

        try {
            parser.parse(new File(filePath), handler);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
