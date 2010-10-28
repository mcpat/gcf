package com.github.gcf.io.test.datagram;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;

/**
 * @author Marcel Patzlaff
 */
public class DatagramClient {
    private final String _host;
    private final int _port;
    
    private DatagramConnection _con;
    
    public DatagramClient(String host, int port) {
        _host= host;
        _port= port;
    }
    
    public void open() throws IOException {
        _con= (DatagramConnection) Connector.open("datagram://" + _host + ":" + _port);
    }
    
    public void send(String message) throws IOException {
        Datagram dgram= _con.newDatagram(_con.getMaximumLength());
        dgram.writeUTF(message);
        _con.send(dgram);
    }
    
    public void close() throws IOException {
        _con.close();
        _con= null;
    }
}
