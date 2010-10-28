package com.github.gcf.io.jse;

import java.io.IOException;

import javax.microedition.io.SecureConnection;
import javax.microedition.io.SecurityInfo;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


/**
 * @author Marcel Patzlaff
 */
final class SSLConnectionImpl extends SocketConnectionImpl implements SecureConnection {
    private SecurityInfo _securityInfo;
    
    public SSLConnectionImpl(String host, int port) throws IOException {
        super(SSLSocketFactory.getDefault().createSocket(host, port));
    }

    public SecurityInfo getSecurityInfo() throws IOException {
        if(_securityInfo == null) {
            _securityInfo= SSLSecurityInfo.create((SSLSocket)socket);
        }
        
        return _securityInfo;
    }
}
