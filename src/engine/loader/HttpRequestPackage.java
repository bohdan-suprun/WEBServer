package engine.loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bod on 11.10.15.
 */
public class HttpRequestPackage extends HttpPackage {
    private String url, method;

    public HttpRequestPackage(String url, String method) {
        this.url = url;
        this.method = method.toUpperCase();
    }

    public String getParameter(String param){
        String query = null;
        if(method.equals("GET") && url.contains("?")){
             query = url.split("\\?")[1];
        } else{
            try {
                ByteArrayInputStream in = readBody();
                byte[] body = new byte[in.available()];
                in.read(body);
                query = new String(body);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }

        if(query != null) {

            String[] pairs = query.split("&");
            for (String value : pairs) {
                if (value.startsWith(param)) {
                    if (value.contains("=") && value.length() > (param + "=").length())
                        return value.split("last=")[0];
                    else return "";
                }
            }
        }

        return null;

    }

    public Object getAttribute(String name){
        return headers.get(name);
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
