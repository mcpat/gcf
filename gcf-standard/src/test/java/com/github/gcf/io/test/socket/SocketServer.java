package com.github.gcf.io.test.socket;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

/**
 * @author Marcel Patzlaff
 */
public class SocketServer {
    private final int _port;
    
    protected final Vector socketHandlers;
    
    protected ServerSocketConnection con;
    private Thread _workThread;
    
    public SocketServer(int port) {
        _port= port;
        socketHandlers= new Vector();
    }
    
    public void openAndStartAccepting() throws IOException {
        con= (ServerSocketConnection) Connector.open("socket://:" + _port);
        
        _workThread= new Thread() {
            public void run() {
                try {
                    while(true) {
                        SocketConnection sc= (SocketConnection) con.acceptAndOpen();
                        SocketHandler sh= new SocketHandler(sc);
                        
                        synchronized (socketHandlers) {
                            socketHandlers.add(sh);
                            socketHandlers.notify();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("server socket was closed");
                }
            }
        };
        
        _workThread.start();
    }
    
    public int getPort() throws IOException {
        return con.getLocalPort();
    }
    
    public SocketHandler getNextHandler() {
        synchronized(socketHandlers) {
            if(socketHandlers.isEmpty()) {
                try {
                    socketHandlers.wait(1000);
                } catch (InterruptedException e) {
                }
            }
            
            if(socketHandlers.isEmpty()) {
                return null;
            }
            
            SocketHandler sh= (SocketHandler) socketHandlers.elementAt(0);
            socketHandlers.removeElementAt(0);
            return sh;
        }
    }
    
    public void stopAcceptingAndClose() throws IOException {
        con.close();
        try {
            _workThread.join();
        } catch (InterruptedException e) {
            throw new IOException("could not join with work thread", e);
        }
    }
}
