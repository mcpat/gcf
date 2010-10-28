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

import java.io.IOException;

public interface DatagramConnection extends Connection {
    int getMaximumLength() throws IOException;

    int getNominalLength() throws IOException;

    void receive(Datagram dgram) throws IOException;

    void send(Datagram dgram) throws IOException;

    Datagram newDatagram(int size) throws IOException;

    Datagram newDatagram(int size, String addr) throws IOException;

    Datagram newDatagram(byte[] buf, int size) throws IOException;

    Datagram newDatagram(byte[] buf, int size, String addr) throws IOException;

}
