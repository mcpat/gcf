package com.github.gcf.io.jse;

import java.io.IOException;

import javax.microedition.io.UDPDatagramConnection;

import com.github.gcf.io.PrimitiveURI;


/**
 * @author Marcel Patzlaff
 */
final class UDPDatagramConnectionImpl extends DatagramConnectionImpl implements UDPDatagramConnection {
    public UDPDatagramConnectionImpl(PrimitiveURI uri, boolean serverMode) throws IOException {
        super(uri, serverMode);
    }

    public String getLocalAddress() throws IOException {
        ensureOpen();
        return socket.getLocalAddress().toString();
    }

    public int getLocalPort() throws IOException {
        ensureOpen();
        return socket.getLocalPort();
    }
}
