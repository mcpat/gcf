package com.github.gcf.io;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connection;

/**
 * @author Marcel Patzlaff
 */
public interface IConnectionFactory {
    boolean conflictsWith(String protocol, IConnectionFactory factory);
    
    Vector getSupportedProtocols();
    Connection openPrim(String protocol, String name, int mode, boolean timeouts) throws IOException;
}
