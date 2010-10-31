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
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 * @author Marcel Patzlaff
 */
class SocketConnectionHandler extends AbstractSocketHandler<SocketConnection> {
    SocketConnectionHandler(String host, int port) throws IOException {
        super(host, port);
    }
    
    SocketConnectionHandler(SocketConnection c) throws IOException {
        super(c);
    }

    protected void closeConnection() throws IOException {
        connection.close();
    }

    protected SocketConnection createConnection(String host, int port) throws IOException {
        return (SocketConnection) Connector.open("socket://" + host + ":" + port);
    }

    protected InputStream getInputStream() throws IOException {
        return connection.openInputStream();
    }

    protected OutputStream getOutputStream() throws IOException {
        return connection.openOutputStream();
    }
}
