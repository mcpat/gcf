package com.github.gcf.io.test;

import com.github.gcf.io.PrimitiveURI;

import junit.framework.TestCase;

/**
 * @author Marcel Patzlaff
 */
public class URITest extends TestCase {
    private Object[][] _testURIs;

    protected void setUp() throws Exception {
        _testURIs= new Object[][]
            {
                {"file:///dev/dsp", "file", null, null, Integer.valueOf(-1), "/dev/dsp", null, null},
                {"socket://localhost:666", "socket", null, "localhost", Integer.valueOf(666), null, null, null},
                {"http://marcel:pw@stilgar/index.html#foo", "http", "marcel:pw", "stilgar", Integer.valueOf(-1), "/index.html", null, "foo"},
                {"http://user:password@www.example.org:8080/serverside/index.html?arg=value&answer=42#anchor",
                 "http", "user:password", "www.example.org", Integer.valueOf(8080), "/serverside/index.html", "arg=value&answer=42", "anchor"},
                {"file:/C:\\\\Programme", "file", null, null, Integer.valueOf(-1), "/C:\\\\Programme", null, null},
            };
    }
    
    public void testURIs() {
        for(int i= 0; i < _testURIs.length; ++i) {
            PrimitiveURI uri= new PrimitiveURI((String) _testURIs[i][0]);
            
            assertEquals("Scheme", _testURIs[i][1], uri.scheme);
            assertEquals("UserInfo", _testURIs[i][2], uri.userInfo);
            assertEquals("Host", _testURIs[i][3], uri.host);
            assertEquals("Port", _testURIs[i][4], Integer.valueOf(uri.port));
            assertEquals("Path", _testURIs[i][5], uri.path);
            assertEquals("Query", _testURIs[i][6], uri.query);
            assertEquals("Fragment", _testURIs[i][7], uri.fragment);
        }
    }
}
