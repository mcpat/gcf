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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.net.SocketFactory;

/**
 * @author Marcel Patzlaff
 */
class SocketHandler extends AbstractSocketHandler<Socket> {
    SocketHandler(String host, int port) throws IOException {
        super(host, port);
    }

    SocketHandler(Socket connection) throws IOException {
        super(connection);
    }

    protected void closeConnection() throws IOException {
        connection.close();
    }

    protected Socket createConnection(String host, int port) throws IOException {
        return SocketFactory.getDefault().createSocket(host, port);
    }

    protected InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    protected OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }
}
