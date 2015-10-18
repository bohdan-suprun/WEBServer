package engine.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by bod on 11.10.15.
 */
public class HttpPackage {

    private String startingLine;
    protected HashMap<String, Object> headers;
    private ByteArrayOutputStream body;

    public HttpPackage(){
        headers = new HashMap<String, Object>();
        body = new ByteArrayOutputStream();

    }

    public void setStartingLine(String sl){
        startingLine = sl;
    }

    public OutputStream getBody(){
        return body;
    }

    public void addHeader(String name, Object value){
        headers.put(name, value);
    }

    public void writeTo(OutputStream out)throws IOException{
        ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
        int contentLength = body.size();
        for(String key: headers.keySet()){
            tempOut.write((key+": "+headers.get(key)+"\r\n").getBytes());
        }
        tempOut.write(("Content-Length: "+contentLength+"\r\n").getBytes());
        tempOut.write("\r\n".getBytes());
        out.write(startingLine.getBytes());
        out.write(tempOut.toByteArray());
        out.write(body.toByteArray());
    }

    public ByteArrayInputStream readBody(){
        return new ByteArrayInputStream(body.toByteArray());
    }
}
