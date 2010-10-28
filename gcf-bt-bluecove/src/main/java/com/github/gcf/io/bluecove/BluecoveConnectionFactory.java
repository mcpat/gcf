package com.github.gcf.io.bluecove;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connection;

import com.github.gcf.io.IConnectionFactory;
import com.intel.bluetooth.MicroeditionConnector;


/**
 * @author Marcel Patzlaff
 */
public class BluecoveConnectionFactory implements IConnectionFactory {
    public boolean conflictsWith(String protocol, IConnectionFactory factory) {
        return false;
    }

    public Vector getSupportedProtocols() {
        Vector protocols= new Vector();
        protocols.add("btgoep");
        protocols.add("btl2cap");
        protocols.add("btspp");
        protocols.add("tcpobex");
        return protocols;
    }

    public Connection openPrim(String protocol, String name, int mode, boolean timeouts) throws IOException {
        return MicroeditionConnector.open(protocol + ":" + name, mode, timeouts);
    }
}
