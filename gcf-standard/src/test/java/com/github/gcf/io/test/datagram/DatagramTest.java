package com.github.gcf.io.test.datagram;

import junit.framework.TestCase;

/**
 * @author Marcel Patzlaff
 */
public class DatagramTest extends TestCase {
    public void testWithFixAddress() throws Exception {
        DatagramServer s= new DatagramServer(5481);
        DatagramClient c= new DatagramClient("127.0.0.1", 5481);
        
        s.openAndStartReading();
        
        c.open();
        c.send("Hallo Welt");
        
        assertEquals("Hallo Welt", s.getNextMessage());
        
        c.close();
        s.stopReadingAndClose();
    }
}
