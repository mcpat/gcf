package com.github.gcf.io.jse;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.InputConnection;

import com.github.gcf.io.AbstractConnection;


/**
 * @author Marcel Patzlaff
 */
final class FileInputConnectionImpl extends AbstractConnection implements InputConnection {
    protected final File file;

    public FileInputConnectionImpl(File file) throws IOException {
        this.file= file;
    }
    
    public DataInputStream openDataInputStream() throws IOException {
        ensureOpen();
        return new DataInputStream(openInputStream());
    }

    public InputStream openInputStream() throws IOException {
        ensureOpen();
        return new FileInputStream(file);
    }
}
