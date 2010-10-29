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

package javax.microedition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.gcf.io.FactoryRegistry;


public class Connector {
    public static final int READ= 0x01;
    public static final int WRITE= 0x02;
    public static final int READ_WRITE= READ | WRITE;
    
    public static Connection open(String name) throws IOException {
        return open(name, READ_WRITE);
    }

    public static Connection open(String name, int mode) throws IOException {
        return open(name, mode, false);
    }
    
    public static Connection open(String name, int mode, boolean timeouts) throws IOException {
        if (name == null) {
            throw new IllegalArgumentException("URL must not be null");
        }

        int colon = name.indexOf(':');

        if (colon < 1) {
            throw new IllegalArgumentException("invalid URL format: " + name);
        }

        String protocol = name.substring(0, colon);
        name = name.substring(colon + 1);
        protocol = protocol.replace('-', '_');
        return FactoryRegistry.openConnection(protocol, name, mode, timeouts);
    }
    
    public static DataInputStream openDataInputStream(String name) throws IOException {
        InputConnection con = (InputConnection) Connector.open(name, Connector.READ);

        try {
            return con.openDataInputStream();
        } catch(IOException ioe) {
            con.close();
            throw ioe;
        }
    }

    public static DataOutputStream openDataOutputStream(String name) throws IOException {
        OutputConnection con = (OutputConnection) Connector.open(name, Connector.WRITE);

        try {
            return con.openDataOutputStream();
        } catch(IOException ioe) {
            con.close();
            throw ioe;
        }
    }

    public static InputStream openInputStream(String name) throws IOException {
        return openDataInputStream(name);
    }

    public static OutputStream openOutputStream(String name) throws IOException {
        return openDataOutputStream(name);
    }
    
    private Connector() {}
}
