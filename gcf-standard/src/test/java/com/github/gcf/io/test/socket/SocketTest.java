package com.github.gcf.io.test.socket;

import junit.framework.TestCase;

/**
 * @author Marcel Patzlaff
 */
public class SocketTest extends TestCase {
    public void testSocket() throws Exception {
        SocketServer server= new SocketServer(0);
        server.openAndStartAccepting();
        
        int serverPort= server.getPort();
        
        SocketHandler sender= new SocketHandler("localhost", serverPort);
        SocketHandler echo= server.getNextHandler();
        assertNotNull("Accepting failed", echo);
        
        server.stopAcceptingAndClose();
        
        sender.send("Hallo Welt");
        assertEquals("Request mismatch", "Hallo Welt", echo.getNextMessage());

        
        echo.send("Hallo Echo!");
        assertEquals("Response mismatch", "Hallo Echo!", sender.getNextMessage());
        sender.stopAndClose();
        echo.stopAndClose();
    }
}
