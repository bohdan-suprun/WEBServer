package engine.loader;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by bod on 11.10.15.
 */
public class HttpResponsePackage extends HttpPackage {

    private String code, text;

    public HttpResponsePackage(int code, String text) {
        this.code = ""+code;
        this.text = text;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        setStartingLine("HTTP/1.1 "+code+" "+text+"\r\n");
        super.writeTo(out);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
