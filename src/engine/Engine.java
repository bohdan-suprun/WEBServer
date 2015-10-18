package engine;

import engine.configer.ConfigReader;
import engine.configer.ServletSAXHandler;

import java.io.IOException;
import java.util.Formatter;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by bod on 05.08.15.
 */
public class Engine {
    public static final String[] IMPLEMENTED_METHODS = {
            "GET",
            "POST",
    };
    private static final String DEF_ENCODING = "UTF-8";
    private static final int DEF_PORT = 8090;
    private static final int DEF_BACKLOG = 50;
    private static final int DEF_THREAD_COUNT = 4;

    private static Integer port, backlog, threadCount;
    private static String encoding;

    public static void main(String[] args)throws InterruptedException{
        setDefaultConfig();
        RequestStorage rs = new RequestStorage();
        Receiver receiver = new Receiver(port, backlog, rs);
        Sender[] senders = new Sender[threadCount];
        for (int i = 0; i < senders.length; i++) {
            senders[i] = new Sender(rs);
            senders[i].start();
        }
        System.out.println("Starting listening port "+port+"...");
        receiver.start();
        receiver.join();
    }
    public static boolean isImplemented(String method){
        method = method.toUpperCase();
        for(String m: IMPLEMENTED_METHODS)
            if(m.equals(method)) return true;

        return false;
    }

    private static void setDefaultConfig(){
        port = DEF_PORT;
        backlog = DEF_BACKLOG;
        threadCount = DEF_THREAD_COUNT;
        encoding = DEF_ENCODING;
    }

    public static String getEncoding(){
        return encoding;
    }
}
