package com.github.gcf.io.jse;

import java.io.IOException;
import java.net.ServerSocket;

import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.StreamConnection;
import javax.net.ServerSocketFactory;

import com.github.gcf.io.AbstractConnection;

/**
 * @author Marcel Patzlaff
 */
class ServerSocketConnectionImpl extends AbstractConnection implements ServerSocketConnection {
    protected final ServerSocket serverSocket;

    protected ServerSocketConnectionImpl(ServerSocket serverSocket) {
        this.serverSocket= serverSocket;
    }
    
    public ServerSocketConnectionImpl(int port) throws IOException {
        this(ServerSocketFactory.getDefault().createServerSocket(port));
    }
    
    public final String getLocalAddress() throws IOException {
        ensureOpen();
        return serverSocket.getInetAddress().toString();
    }

    public final int getLocalPort() throws IOException {
        ensureOpen();
        return serverSocket.getLocalPort();
    }

    public StreamConnection acceptAndOpen() throws IOException {
        ensureOpen();
        return new SocketConnectionImpl(serverSocket.accept());
    }

    public void close() throws IOException {
        super.close();
        serverSocket.close();
    }
}
