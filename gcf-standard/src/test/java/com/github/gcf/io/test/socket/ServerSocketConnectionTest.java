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

package com.github.gcf.io.test.socket;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;

import junit.framework.TestCase;

public class ServerSocketConnectionTest extends TestCase {
    public void testServerSocketSchemes() throws Exception {
        ServerSocketConnection ssc= (ServerSocketConnection) Connector.open("serversocket://:0");
        int usablePort= ssc.getLocalPort();
        ssc.close();
        
        ssc= (ServerSocketConnection) Connector.open("socket://:" + usablePort);
        
        assertNotNull("local address cannot be null", ssc.getLocalAddress());
        int localPort= ssc.getLocalPort();
        
        if(localPort < 1 || localPort > 65535) {
            fail("invalid port " + localPort);
        }
        
        ssc.close();
        
        try {
            ssc.getLocalAddress();
            fail("connection not closed properly");
        } catch (Exception e) {
            assertEquals("connection not closed properly", e.getClass(), IOException.class);
        }
        
        try {
            ssc.getLocalPort();
            fail("connection not closed properly");
        } catch (Exception e) {
            assertEquals("connection not closed properly", e.getClass(), IOException.class);
        }
        
        try {
            Connector.open("serversocket://fakehost:80");
            fail("uri check insufficient");
        } catch (Exception e) {
            assertEquals("uri check insufficient", e.getClass(), IllegalArgumentException.class);
        }
        
        try {
            Connector.openInputStream("serversocket://:" + usablePort);
            fail("invalid inheritance from InputConnection");
        } catch (Exception e) {
            assertEquals("openInputStream: invalid exception", e.getClass(), IllegalArgumentException.class);
        }
        
        try {
            Connector.openOutputStream("socket://:" + usablePort);
            fail("invalid inheritance from OutputConnection");
        } catch (Exception e) {
            assertEquals("openOutputStream: invalid exception", e.getClass(), IllegalArgumentException.class);
        }
    }
    
    public void testServerSocket() throws Exception {
        ServerSocketConnectionHandler ssch= new ServerSocketConnectionHandler();
        SocketHandler toServer= new SocketHandler("localhost", ssch.getPort());
        
        SocketConnectionHandler client= ssch.getNextHandler();
        assertNotNull("server cannot accept", client);
        
        String m1= "Hello World";
        toServer.send(m1);
        assertEquals("cannot exchange messages", m1, client.getNextMessage());
        
        ssch.stopAcceptingAndClose();
        String m2= "Server Closed!";
        toServer.send(m2);
        assertEquals("cannot exchange messages", m2, client.getNextMessage());
        
        toServer.stopAndClose();
        client.stopAndClose();
    }
}