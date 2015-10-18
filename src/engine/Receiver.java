package engine;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


class Receiver extends Thread{
    private ServerSocket serverSocket;
    private RequestStorage storage;


    Receiver(int port, int backlog, RequestStorage storage){
        try {
            serverSocket = new ServerSocket(port, backlog);
            this.storage = storage;
        }catch (UnknownHostException ex){
            ex.printStackTrace();
            System.exit(-1);

        } catch (BindException ex){
            System.err.println("The PORT: "+ port + " is busy");
            System.exit(-2);

        } catch (IOException ex){
            ex.printStackTrace();
            System.exit(-3);
        }

    }
    public void run(){
        try {
            while (!isInterrupted()){
                Socket client = serverSocket.accept();
                storage.addRequest(client);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
