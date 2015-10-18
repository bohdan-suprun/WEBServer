package my.servlet;

import engine.Engine;
import engine.exceptions.IncorrectStartLineException;
import engine.exceptions.UnknownMethodException;
import engine.exceptions.WrongPackageException;
import engine.loader.HttpRequestPackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bod on 10.08.15.
 */

final public class Request {
    private HttpRequestPackage requestPackage;

    public Request(String httpPackage) throws WrongPackageException,
            IncorrectStartLineException, UnknownMethodException{

        String params[] = httpPackage.split("\n");
        if(params.length < 1) throw new WrongPackageException("Empty package");
        String[] startingLine = getStartLine(params[0]);
        try {
            requestPackage = new HttpRequestPackage(URLDecoder.decode(startingLine[1], "utf-8"),startingLine[0]);
            readHeaders(params);
            readBody(httpPackage);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Some problems with decoding URI: utf-8 isn't supported");
        }
    }

    private String[] getStartLine(String httpPackage)
            throws IncorrectStartLineException, UnknownMethodException{

        String[] result = httpPackage.split(" ");
        if(result.length != 3)
            throw new IncorrectStartLineException("Incorrect format of starting line");
        if(!Engine.isImplemented(result[0]))
            throw new UnknownMethodException("Method "+ result[0] + " is not implemented");

        return result;
    }

    private void readHeaders(String[] params){
        for(String parameter: params) {
            String[] parsed = parameter.split(": ");
            if(parsed.length == 2)
                requestPackage.addHeader(parsed[0].toLowerCase(), parsed[1]);
        }

    }

    private void readBody(String pack){
        try {
            String[] body = pack.split("\r\n\r\n");
            if (body.length == 2)
                requestPackage.getBody().write(body[1].getBytes());
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public String getParameter(String name){
        return requestPackage.getParameter(name.toLowerCase());
    }

    public String getPackageParameter(String name){
        return (name.toLowerCase());
    }

    public String getContentType(){
        return ("content-type");
    }

    public String getUrl(){
        return requestPackage.getUrl();
    }

    public String getMethod(){
        return requestPackage.getMethod();
    }
}
