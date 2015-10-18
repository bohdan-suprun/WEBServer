package engine;

import engine.exceptions.IncorrectStartLineException;
import engine.exceptions.UnknownMethodException;
import engine.exceptions.WrongPackageException;
import engine.loader.PreLoader;
import engine.loader.ServletLoader;
import my.servlet.MyServlet;
import my.servlet.Request;
import my.servlet.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;

;

/**
 * Created by bod on 02.08.15.
 */
class Sender extends Thread{
    private final String PACKAGE_OK_HEADER = "HTTP/1.1 200 OK\r\nDate: %s\r\nServer: Kirpichik\r\nContent-Type: %s\r\n" +
            "Connection: close\n\n";
    private final String PACKAGE_EMPTY_HEADER = "HTTP/1.1 400 Empty package";
    private RequestStorage storage;
    Sender(RequestStorage storage){
        setDaemon(true);
        this.storage = storage;
    }
    @Override
    public void run(){
        while (true){
            try{
                Socket client = storage.getRequest();
                InputStream in = client.getInputStream();
                StringBuilder httpPackage = new StringBuilder();
                while(in.available() > 0)
                    httpPackage.append((char)in.read());
                Response response = new Response();

                try {
                    Request request = new Request(httpPackage.toString());
                    PreLoader preLoader = new PreLoader(request.getUrl());
                    String className = preLoader.getClassName();
                    if(className == null) throw new ClassNotFoundException();
                    ServletLoader loader = new ServletLoader("webapp/"+preLoader.getFolderName()+"/WEB-INF/classes/",
                            ClassLoader.getSystemClassLoader());
                    MyServlet newClass = (MyServlet) loader.loadClass(className).newInstance();
                    newClass.getClass().getMethod("do"+request.getMethod(),
                            Request.class, Response.class).invoke(newClass, request, response);
                    response.addHeader("Date", new Date().toString());
                } catch (WrongPackageException e) {
                    response.getPrintStream().print(PACKAGE_EMPTY_HEADER);
                } catch (IncorrectStartLineException e) {
                    response.getPrintStream().print(PACKAGE_EMPTY_HEADER);
                } catch (UnknownMethodException e) {
                    response.setCode("500");
                    response.setTextResponse("METHOD NOT IMPLEMENTED");
                    response.getPrintStream().print("Method not implemented");
                }catch (ClassNotFoundException e) {
                    response.setCode("404");
                    response.setTextResponse("PAGE NOT FOUND");
                    response.getPrintStream().print("Page not found!");
                } catch (FileNotFoundException ex){
                    response.setCode("404");
                    response.setTextResponse("PAGE NOT FOUND");
                    response.getPrintStream().print("Page not found!");
                } catch (Exception  e) {
                    response.setCode("500");
                    response.setTextResponse("INTERNAL ERROR");
                    response.getPrintStream().print("Internal server error!"+e.getMessage());
                } finally {
                    response.writeTo(client.getOutputStream());
                    client.close();
                }
            }catch (NoSuchElementException ex){
                // empty queue
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
