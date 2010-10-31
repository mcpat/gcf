/*
 * GCF -- Generic Connection Framework for Java SE
 *        GCF-Standard
 *
 * Copyright (c) 2007-2010 Marcel Patzlaff (marcel.patzlaff@gmail.com)
 *
 * This library is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.gcf.io.test.socket;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

/**
 * @author Marcel Patzlaff
 */
class ServerSocketConnectionHandler {
    protected ServerSocketConnection con;
    private Thread _workThread;
    
    protected final BlockingQueue<SocketConnectionHandler> socketHandlers= new ArrayBlockingQueue<SocketConnectionHandler>(5);
    
    protected ServerSocketConnectionHandler() throws IOException {
        startAccepting();
    }
    
    private void startAccepting() throws IOException {
        con= (ServerSocketConnection) Connector.open("socket://:0");
        
        _workThread= new Thread() {
            public void run() {
                try {
                    while(true) {
                        SocketConnection sc= (SocketConnection) con.acceptAndOpen();
                        SocketConnectionHandler sh= new SocketConnectionHandler(sc);
                        socketHandlers.offer(sh);
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
    
    public SocketConnectionHandler getNextHandler() throws InterruptedException {
        return socketHandlers.poll(1000L, TimeUnit.MILLISECONDS);
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
