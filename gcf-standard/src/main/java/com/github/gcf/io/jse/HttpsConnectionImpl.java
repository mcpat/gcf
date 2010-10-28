/**
 * GCF -- Generic Connection Framework for Java SE
 *        GCF-Standard
 *
 * Copyright (c) 2007-2010 Marcel Patzlaff (marcel.patzlaff@gmail.com)
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
