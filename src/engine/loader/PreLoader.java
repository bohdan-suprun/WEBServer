package engine.loader;

import engine.ServletConfig;
import engine.configer.ConfigReader;
import engine.configer.ServletSAXHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bod on 11.10.15.
 */
public class PreLoader {
    private String url;
    private String folderName;
    private String path;

    public PreLoader(String url) {
        if(url.contains("http")){
            url = url.substring(url.indexOf("//") + 2);
            if(url.contains("/")) {
                url = url.substring(url.indexOf("/"));
            } else url="/"+url;
        }
        if(url.contains("?")){
            url = url.split("\\?")[0];
        }
        this.url = url;
        setPath();

    }

    public String getClassName() throws IOException {
        ServletSAXHandler handler = new ServletSAXHandler();
        ConfigReader.getConfigReader().read("webapp/"+folderName+"/WEB-INF/web.xml", handler);
        List<ServletConfig> list = handler.getParsed();
        for(ServletConfig config: list){
            if(config.matches(path))
                return config.getClassName();
        }
        return null;
    }

    private void setPath(){
        if(url.equals("/")){
            folderName = "ROOT";
            path = "/";
        } else{
            folderName = url.substring(1);
            folderName = folderName.substring(0, folderName.indexOf("/"));
            path = url.substring(1);
            path = path.substring(path.indexOf("/"));
        }

    }

    public String getFolderName() {
        return folderName;
    }

}
