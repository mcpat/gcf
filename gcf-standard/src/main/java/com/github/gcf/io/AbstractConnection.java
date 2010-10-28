package com.github.gcf.io;

import java.io.IOException;

import javax.microedition.io.Connection;

/**
 * @author Marcel Patzlaff
 */
public abstract class AbstractConnection implements Connection {
    private boolean _closed= false;

    public void close() throws IOException {
        _closed= true;
    }
    
    protected void ensureOpen() throws IOException {
        if(_closed) {
            throw new IOException("Connection is closed");
        }
    }
}
