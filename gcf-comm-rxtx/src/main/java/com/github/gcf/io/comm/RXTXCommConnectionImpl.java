package com.github.gcf.io.comm;

import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.CommConnection;

import com.github.gcf.io.AbstractConnection;


/**
 * @author Marcel Patzlaff
 */
public class RXTXCommConnectionImpl extends AbstractConnection implements CommConnection {
    private final SerialPort _port;
    
    protected RXTXCommConnectionImpl(SerialPort port) {
        _port= port;
    }
    
    public int getBaudrate() {
        return _port.getBaudRate();
    }

    public int setBaudrate(int baudrate) {
        try {
            _port.setSerialPortParams(
                baudrate,
                _port.getDataBits(),
                _port.getStopBits(),
                _port.getParity()
            );
        } catch (UnsupportedCommOperationException e) {
            System.err.println("Cannot change baud rate");
        }
        
        return _port.getBaudRate();
    }

    public DataInputStream openDataInputStream() throws IOException {
        ensureOpen();
        return new DataInputStream(openInputStream());
    }

    public InputStream openInputStream() throws IOException {
        ensureOpen();
        return _port.getInputStream();
    }

    public DataOutputStream openDataOutputStream() throws IOException {
        ensureOpen();
        return new DataOutputStream(openOutputStream());
    }

    public OutputStream openOutputStream() throws IOException {
        ensureOpen();
        return _port.getOutputStream();
    }

    public void close() throws IOException {
        super.close();
        _port.close();
    }
}
