package com.github.gcf.io.jse;

import java.io.IOException;
import java.net.DatagramSocket;

import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;

import com.github.gcf.io.AbstractConnection;
import com.github.gcf.io.PrimitiveURI;


/**
 * @author Marcel Patzlaff
 */
class DatagramConnectionImpl extends AbstractConnection implements DatagramConnection {
    protected DatagramSocket socket;
    private final PrimitiveURI _defaultAddress;
    
    private final int _maximumSize;
    
    protected DatagramConnectionImpl(PrimitiveURI uri, boolean serverMode) throws IOException {
        if(serverMode) {
            socket= new DatagramSocket(uri.port);
            _defaultAddress= null;
        } else {
            socket= new DatagramSocket();
            _defaultAddress= uri;
        }
        
        int sendBuf= socket.getSendBufferSize();
        int recvBuf= socket.getReceiveBufferSize();
        _maximumSize= sendBuf > recvBuf ? recvBuf : sendBuf;
    }
    
    public final int getMaximumLength() throws IOException {
        ensureOpen();
        return _maximumSize;
    }

    public final int getNominalLength() throws IOException {
        ensureOpen();
        return _maximumSize;
    }

    public final Datagram newDatagram(byte[] buf, int size, String addr) throws IOException {
        ensureOpen();
        
        if(size > _maximumSize) {
            throw new IOException("requested size exceeds maximum");
        }
        
        Datagram datagram= new DatagramObject(buf, size);
        datagram.setAddress(addr);
        return datagram;
    }

    public final Datagram newDatagram(byte[] buf, int size) throws IOException {
        ensureOpen();
        DatagramObject datagram= new DatagramObject(buf, size);
        
        if(_defaultAddress != null) {
            datagram.setAddress(_defaultAddress);
        }
        
        return datagram;
    }

    public final Datagram newDatagram(int size, String addr) throws IOException {
        return newDatagram(new byte[size], size, addr);
    }

    public final Datagram newDatagram(int size) throws IOException {
        return newDatagram(new byte[size], size);
    }

    public final void receive(Datagram dgram) throws IOException {
        if(_defaultAddress != null) {
            throw new IOException("Connection is in client mode");
        }
        
        if(!(dgram instanceof DatagramObject)) {
            throw new IllegalArgumentException("Invalid Datagram");
        }
        
        ensureOpen();
        DatagramObject d= (DatagramObject) dgram;
        d.doReceive(socket);
    }

    public final void send(Datagram dgram) throws IOException {
        if(_defaultAddress == null) {
            throw new IOException("Connection is in server mode");
        }
        
        if(!(dgram instanceof DatagramObject)) {
            throw new IllegalArgumentException("Invalid Datagram");
        }
        
        ensureOpen();
        DatagramObject d= (DatagramObject) dgram;
        d.doSend(socket);
    }

    public final void close() throws IOException {
        super.close();
        if(socket != null) {
            try {
                socket.close();
            } finally {
                socket= null;
            }
        }
    }
    
    protected final void ensureOpen() throws IOException {
        super.ensureOpen();
        
        if(socket == null || socket.isClosed())
            throw new IOException("Connection is closed");
    }
}
