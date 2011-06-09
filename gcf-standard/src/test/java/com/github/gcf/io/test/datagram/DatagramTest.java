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
