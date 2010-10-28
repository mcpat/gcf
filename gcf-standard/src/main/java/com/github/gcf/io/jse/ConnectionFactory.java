package com.github.gcf.io.jse;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Vector;

import javax.microedition.io.Connection;
import javax.net.ssl.HttpsURLConnection;

import com.github.gcf.io.IConnectionFactory;
import com.github.gcf.io.PrimitiveURI;


/**
 * @author Marcel Patzlaff
 */
public class ConnectionFactory implements IConnectionFactory {
    public static final String DATAGRAM_SCHEME= "datagram://";
    
    public boolean conflictsWith(String protocol, IConnectionFactory factory) {
        return false;
    }

    public Vector getSupportedProtocols() {
        Vector protocols= new Vector();
        protocols.addElement("file");
        protocols.addElement("datagram");
        protocols.addElement("socket");
        protocols.addElement("serversocket");
        protocols.addElement("ssl");
        protocols.addElement("http");
        protocols.addElement("https");
        return protocols;
    }

    public Connection openPrim(String protocol, String name, int mode, boolean timeouts) throws IOException {
        if (name.charAt(0) != '/' || name.charAt(1) != '/') {
            throw new IllegalArgumentException("Protocol must end with \"//\"");
        }
        
        PrimitiveURI uri= new PrimitiveURI(protocol + ":" + name);
        
        // create HTTP connection
        if(protocol.equals("http")) {
            return new HttpConnectionImpl((HttpURLConnection) uri.toURL().openConnection());
        }
        
        // create HTTPS connection
        if(protocol.equals("https")) {
            return new HttpsConnectionImpl((HttpsURLConnection) uri.toURL().openConnection());
        }
        
        // create FILE connection
        if(protocol.equals("file")) {
            File file= new File(uri.path);
            return new FileInputConnectionImpl(file);
        }
        
        if (uri.path != null || uri.query != null || uri.fragment != null) {
            throw new IllegalArgumentException("Malformed address");
        }

        if(uri.port < 0) {
            throw new IOException("Invalid port " + uri);
        }
        
        // create DATAGRAM connection
        if(protocol.equals("datagram")) {
            return new UDPDatagramConnectionImpl(uri, uri.host == null);
        }

        if(uri.host != null) {
            if(protocol.equals("ssl")) {
                return new SSLConnectionImpl(uri.host, uri.port);
            } else if(protocol.equals("socket")) {
                return new SocketConnectionImpl(uri.host, uri.port);
            } else {
                throw new IllegalArgumentException("Malformed address for server socket");
            }
        } else {
            if(protocol.equals("ssl")) {
                throw new IllegalArgumentException("SSL server unsupported");
            }
            
            return new ServerSocketConnectionImpl(uri.port);
        }
    }
}
