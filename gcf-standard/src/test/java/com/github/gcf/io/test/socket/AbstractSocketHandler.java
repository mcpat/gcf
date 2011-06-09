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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Marcel Patzlaff
 */
public abstract class AbstractSocketHandler<C> {
    protected final C connection;
    
    protected BufferedReader reader;
    protected BlockingQueue<String> messages;
    
    private Thread _workThread;
    private BufferedWriter _writer;
    
    protected AbstractSocketHandler(String host, int port) throws IOException {
        this.connection= createConnection(host, port);
        start();
    }
    
    protected AbstractSocketHandler(C connection) throws IOException {
        this.connection= connection;
        start();
    }
    
    public final void send(String message) throws IOException {
        _writer.append(message);
        _writer.newLine();
        _writer.flush();
    }
    
    public final String getNextMessage() throws InterruptedException {
        return messages.poll(1000L, TimeUnit.MILLISECONDS);
    }
    
    public final void stopAndClose() throws IOException {
        _writer.close();
        reader.close();
        closeConnection();
        try {
            _workThread.join();
        } catch (InterruptedException e) {
            throw new IOException("could not join with work thread", e);
        }
    }
    
    protected abstract C createConnection(String host, int port) throws IOException;
    protected abstract InputStream getInputStream() throws IOException;
    protected abstract OutputStream getOutputStream() throws IOException;
    protected abstract void closeConnection() throws IOException;
    
    private void start() throws IOException {
        messages= new ArrayBlockingQueue<String>(5);
        _writer= new BufferedWriter(new OutputStreamWriter(getOutputStream()));
        
        reader= new BufferedReader(new InputStreamReader(getInputStream()));
        _workThread= new Thread() {
            public void run() {
                try {
                    
                    while(true) {
                        String line= reader.readLine();
                        if(line != null) {
                            messages.offer(line);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("socket closed");
                }
            }
        };
        
        _workThread.start();
    }
}
