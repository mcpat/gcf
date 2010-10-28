/**
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
