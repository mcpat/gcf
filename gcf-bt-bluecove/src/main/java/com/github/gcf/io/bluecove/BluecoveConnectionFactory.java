/*
 * GCF -- Generic Connection Framework for Java SE
 *        GCF-Bluetooth (Bluecove)
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

package com.github.gcf.io.bluecove;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connection;

import com.github.gcf.io.IConnectionFactory;
import com.github.gcf.io.PrimitiveURI;
import com.intel.bluetooth.MicroeditionConnector;


/**
 * @author Marcel Patzlaff
 */
public class BluecoveConnectionFactory implements IConnectionFactory {
    public boolean conflictsWith(String protocol, IConnectionFactory factory) {
        return false;
    }

    public Vector getSupportedProtocols() {
        Vector protocols= new Vector();
        protocols.add("btgoep");
        protocols.add("btl2cap");
        protocols.add("btspp");
        protocols.add("tcpobex");
        return protocols;
    }

    public Connection openPrim(String protocol, PrimitiveURI uri, int mode, boolean timeouts) throws IOException {
        return MicroeditionConnector.open(uri.rawString, mode, timeouts);
    }
}
