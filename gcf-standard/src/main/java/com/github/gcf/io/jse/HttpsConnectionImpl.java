package com.github.gcf.io.jse;

import java.io.IOException;

import javax.microedition.io.HttpsConnection;
import javax.microedition.io.SecurityInfo;
import javax.net.ssl.HttpsURLConnection;


/**
 * @author Marcel Patzlaff
 */
final class HttpsConnectionImpl extends HttpConnectionImpl implements HttpsConnection {
    private SecurityInfo _securityInfo;
    
    public HttpsConnectionImpl(HttpsURLConnection connection) {
        super(connection);
    }
    
    public SecurityInfo getSecurityInfo() throws IOException {
        if(_securityInfo == null) {
            _securityInfo= SSLSecurityInfo.create((HttpsURLConnection) connection);
        }
        
        return _securityInfo;
    }
}
