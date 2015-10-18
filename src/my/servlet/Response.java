package my.servlet;

import engine.Engine;
import engine.loader.HttpResponsePackage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * Created by bod on 10.08.15.
 */
final public class Response {
    private PrintStream out;
    private HttpResponsePackage pack;

    public Response(){
        try {
            pack = new HttpResponsePackage(200, "OK");
            this.out = new PrintStream(pack.getBody(), true, Engine.getEncoding());
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
            this.out = new PrintStream(out);
        }
    }

    public PrintStream getPrintStream(){
        return out;
    }

    public void setContentType(String contentType){
        pack.addHeader("Content-Type: ", contentType);
    }

    public void writeTo(OutputStream out)throws IOException{
        this.out.flush();
        pack.writeTo(out);
    }

    public void setCode(String code) {
        pack.setCode(code);
    }

    public void setTextResponse(String text) {
        pack.setText(text);
    }

    public void addHeader(String name, Object value){
        pack.addHeader(name, value);
    }
}
