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

package javax.microedition.io;

import java.io.IOException;

public interface HttpConnection extends ContentConnection {
    String HEAD= "HEAD";

    String GET= "GET";

    String POST= "POST";

    int HTTP_OK= 200;

    int HTTP_CREATED= 201;

    int HTTP_ACCEPTED= 202;

    int HTTP_NOT_AUTHORITATIVE= 203;

    int HTTP_NO_CONTENT= 204;

    int HTTP_RESET= 205;

    int HTTP_PARTIAL= 206;

    int HTTP_MULT_CHOICE= 300;

    int HTTP_MOVED_PERM= 301;

    int HTTP_MOVED_TEMP= 302;

    int HTTP_SEE_OTHER= 303;

    int HTTP_NOT_MODIFIED= 304;

    int HTTP_USE_PROXY= 305;

    int HTTP_TEMP_REDIRECT= 307;

    int HTTP_BAD_REQUEST= 400;

    int HTTP_UNAUTHORIZED= 401;

    int HTTP_PAYMENT_REQUIRED= 402;

    int HTTP_FORBIDDEN= 403;

    int HTTP_NOT_FOUND= 404;

    int HTTP_BAD_METHOD= 405;

    int HTTP_NOT_ACCEPTABLE= 406;

    int HTTP_PROXY_AUTH= 407;

    int HTTP_CLIENT_TIMEOUT= 408;

    int HTTP_CONFLICT= 409;

    int HTTP_GONE= 410;

    int HTTP_LENGTH_REQUIRED= 411;

    int HTTP_PRECON_FAILED= 412;

    int HTTP_ENTITY_TOO_LARGE= 413;

    int HTTP_REQ_TOO_LONG= 414;

    int HTTP_UNSUPPORTED_TYPE= 415;

    int HTTP_UNSUPPORTED_RANGE= 416;

    int HTTP_EXPECT_FAILED= 417;

    int HTTP_INTERNAL_ERROR= 500;

    int HTTP_NOT_IMPLEMENTED= 501;

    int HTTP_BAD_GATEWAY= 502;

    int HTTP_UNAVAILABLE= 503;

    int HTTP_GATEWAY_TIMEOUT= 504;

    int HTTP_VERSION= 505;

    /**
     * @throws IOException
     */
    long getDate() throws IOException;
    
    /**
     * @throws IOException
     */
    long getExpiration() throws IOException;
    
    String getFile();
    
    /**
     * @throws IOException
     */
    String getHeaderField(int n) throws IOException;
    
    /**
     * @throws IOException
     */
    String getHeaderField(String name) throws IOException;
    
    /**
     * @throws IOException
     */
    long getHeaderFieldDate(String name, long def) throws IOException;
    
    /**
     * @throws IOException
     */
    int getHeaderFieldInt(String name, int def) throws IOException;
    
    /**
     * @throws IOException
     */
    String getHeaderFieldKey(int n) throws IOException;
    
    String getHost();
    
    /**
     * @throws IOException
     */
    long getLastModified() throws IOException;

    int getPort();
    
    String getProtocol();

    String getQuery();
    
    String getRef();

    String getURL();

    String getRequestMethod();

    /**
     * @throws IOException
     */
    int getResponseCode() throws IOException;
    
    /**
     * @throws IOException
     */
    String getResponseMessage() throws IOException;

    String getRequestProperty(String key);

    /**
     * @throws IOException
     */
    void setRequestMethod(String method) throws IOException;
    
    /**
     * @throws IOException
     */
    void setRequestProperty(String key, String value) throws IOException;

}
