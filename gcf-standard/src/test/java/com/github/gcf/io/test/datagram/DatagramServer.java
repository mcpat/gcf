/*
 * GCF - Generic Connection Framework for Java SE
 *       GCF-Standard
 *
 * Copyright (c) 2007-2011 Marcel Patzlaff (marcel.patzlaff@gmail.com)
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

package com.github.gcf.io.test.datagram;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.UDPDatagramConnection;

/**
 * @author Marcel Patzlaff
 */
public class DatagramServer {
    private final int _port;
    
    protected UDPDatagramConnection con;
    
    protected final Vector messages;
    private Thread _workThread= null;
    
    public DatagramServer(int port) {
        _port= port;
        messages= new Vector();
    }
    
    public void openAndStartReading() throws IOException {
        con= (UDPDatagramConnection) Connector.open("datagram://:" + _port);
        
        _workThread= new Thread() {
            public void run() {
                try {
                    Datagram dgram= con.newDatagram(con.getMaximumLength());
                    while(true) {
                        dgram.reset();
                        con.receive(dgram);
                        System.out.println("read message of size " + dgram.getLength());
                        synchronized(messages) {
                            messages.add(dgram.readUTF());
                            messages.notify();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("connection was closed");
                }
            }
        };
        
        _workThread.start();
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
    
    public void stopReadingAndClose() throws IOException {
        con.close();
        try {
            _workThread.join();
        } catch (InterruptedException e) {
            throw new IOException("could not join with work thread", e);
        }
    }
}
