package com.github.gcf.io.jse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.microedition.io.SocketConnection;
import javax.net.SocketFactory;

import com.github.gcf.io.AbstractConnection;

/**
 * @author Marcel Patzlaff
 */
class SocketConnectionImpl extends AbstractConnection implements SocketConnection {
    protected final Socket socket;
    
    protected SocketConnectionImpl(Socket socket) {
        this.socket= socket;
    }
    
    public SocketConnectionImpl(String host, int port) throws IOException {
        this(SocketFactory.getDefault().createSocket(host, port));
    }
    
    public final String getAddress() throws IOException {
        ensureOpen();
        return socket.getInetAddress().toString();
    }

    public final String getLocalAddress() throws IOException {
        ensureOpen();
        return socket.getLocalAddress().toString();
    }

    public final int getLocalPort() throws IOException {
        ensureOpen();
        return socket.getLocalPort();
    }

    public final int getPort() throws IOException {
        ensureOpen();
        return socket.getPort();
    }

    public final int getSocketOption(byte option) throws IllegalArgumentException, IOException {
        ensureOpen();
        switch(option) {
            case DELAY: {
                return socket.getTcpNoDelay() ? 1 : 0;
            }
            
            case KEEPALIVE: {
                return socket.getKeepAlive() ? 1 : 0;
            }
            
            case LINGER: {
                return socket.getSoLinger();
            }
            
            case RCVBUF: {
                return socket.getReceiveBufferSize();
            }
            
            case SNDBUF: {
                return socket.getSendBufferSize();
            }
            
            default: {
                throw new IllegalArgumentException("unvalid socket option: " + option);
            }
        }
    }

    public final void setSocketOption(byte option, int value) throws IllegalArgumentException, IOException {
        ensureOpen();
        switch(option) {
            case DELAY: {
                socket.setTcpNoDelay(value != 0);
            }
            
            case KEEPALIVE: {
                socket.setKeepAlive(value != 0);
            }
            
            case LINGER: {
                socket.setSoLinger(value != 0, value);
            }
            
            case RCVBUF: {
                socket.setReceiveBufferSize(value);
            }
            
            case SNDBUF: {
                socket.setSendBufferSize(value);
            }
            
            default: {
                throw new IllegalArgumentException("unvalid socket option: " + option);
            }
        }
    }

    public final DataInputStream openDataInputStream() throws IOException {
        ensureOpen();
        return new DataInputStream(openInputStream());
    }

    public final InputStream openInputStream() throws IOException {
        ensureOpen();
        return socket.getInputStream();
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }

    public final DataOutputStream openDataOutputStream() throws IOException {
        ensureOpen();
        return new DataOutputStream(openOutputStream());
    }

    public final OutputStream openOutputStream() throws IOException {
        ensureOpen();
        return socket.getOutputStream();
    }
}
