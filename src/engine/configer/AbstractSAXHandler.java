package engine.configer;

import engine.ServletConfig;
import engine.loader.ServletLoader;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by bod on 06.09.15.
 */
abstract public class AbstractSAXHandler extends DefaultHandler {
    protected List<ServletConfig> parsedXML;

    public List<ServletConfig> getParsed(){
        return parsedXML;
    }
}
