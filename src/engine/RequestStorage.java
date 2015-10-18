package engine;

import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by bod on 02.08.15.
 */
class RequestStorage{
    private volatile ConcurrentLinkedDeque<Socket> requestQueue;

    RequestStorage(){
        requestQueue = new ConcurrentLinkedDeque<Socket>();
    }

    void addRequest(Socket e){
        requestQueue.addLast(e);
    }

    Socket getRequest(){
        return requestQueue.removeFirst();
    }
}
