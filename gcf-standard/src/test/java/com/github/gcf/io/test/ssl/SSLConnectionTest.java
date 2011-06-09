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

package com.github.gcf.io.test.ssl;

import javax.microedition.io.Connector;
import javax.microedition.io.SecureConnection;
import javax.microedition.io.SecurityInfo;

import junit.framework.TestCase;

/**
 * @author Marcel Patzlaff
 */
public class SSLConnectionTest extends TestCase {
    public void testSocket() throws Exception {
        String url= "ssl://www.fortify.net:443";
        
        SecureConnection sc= (SecureConnection) Connector.open(url);
        SecurityInfo si= sc.getSecurityInfo();
        assertNotNull("security info not intialised", si);
        assertNotNull("cypher suite not present", si.getCipherSuite());
        assertNotNull("protocol name not parsed", si.getProtocolName());
        assertNotNull("protocol version not parsed", si.getProtocolVersion());
        
        assertNotNull("no remote address", sc.getAddress());
        assertNotNull("no local address", sc.getLocalAddress());
        
        if(sc.getLocalPort() <= 0) {
            fail("invalid local port");
        }
        
        if(sc.getPort() <= 0) {
            fail("invalid remote port");
        }
        
        if(si != sc.getSecurityInfo()) {
            fail("security info must not change");
        }
        
        sc.close();
    }
}
