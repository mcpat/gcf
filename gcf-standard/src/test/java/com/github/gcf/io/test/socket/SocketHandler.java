package com.github.gcf.io.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 * @author Marcel Patzlaff
 */
public class SocketHandler {
    protected SocketConnection con;
    protected final BufferedReader reader;
    protected final Vector messages;
    
    private final Thread _workThread;
    private final PrintStream _writer;
    
    public SocketHandler(String host, int port) throws IOException {
        this((SocketConnection)Connector.open("socket://" + host + ":" + port));
    }
    
    public SocketHandler(SocketConnection c) throws IOException {
        con= c;
        messages= new Vector();
        _writer= new PrintStream(con.openOutputStream());
        
        reader= new BufferedReader(new InputStreamReader(con.openInputStream()));
        _workThread= new Thread() {
            public void run() {
                try {
                    
                    while(true) {
                        String line= reader.readLine();
                        synchronized (messages) {
                            messages.add(line);
                            messages.notify();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("socket closed");
                }
            }
        };
        
        _workThread.start();
    }
    
    public void send(String message) throws IOException {
        _writer.println(message);
        _writer.flush();
    }
    
    public String getNextMessage() {
        synchronized(messages) {
            if(messages.isEmpty()) {
                try {
                    messages.wait(1000);
                } catch (InterruptedException e) {
                }
            }
            
            if(messages.isEmpty()) {
                return null;
            }
            
            String m= (String) messages.elementAt(0);
            messages.removeElementAt(0);
            return m;
        }
    }
    
    public void stopAndClose() throws IOException {
        _writer.close();
        reader.close();
        con.close();
        try {
            _workThread.join();
        } catch (InterruptedException e) {
            throw new IOException("could not join with work thread", e);
        }
    }
}
