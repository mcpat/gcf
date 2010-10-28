package com.github.gcf.io;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Vector;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;

/**
 * @author Marcel Patzlaff
 */
public class FactoryRegistry {
    private final static boolean DEBUG= true;
    private final static Hashtable FACTORIES;
    
    static {
        FACTORIES= new Hashtable();
        ServiceLoader loader= ServiceLoader.load(IConnectionFactory.class);
        for(Iterator iter= loader.iterator(); iter.hasNext();) {
            IConnectionFactory current= (IConnectionFactory) iter.next();
            Vector protocols= current.getSupportedProtocols();
            for(int i= 0; i < protocols.size(); ++i) {
                String protocol= (String) protocols.elementAt(i);
                IConnectionFactory oldFac= (IConnectionFactory) FACTORIES.put(protocol, current);
                if(oldFac != null) {
                    if(oldFac.conflictsWith(protocol, current)) {
                        // oldFac overrides current
                        FACTORIES.put(protocol, oldFac);
                    } else if(!current.conflictsWith(protocol, oldFac)) {
                        System.err.println("[FactoryRegistry] WARN: multiple factories for " + protocol);
                    }
                }
                if(DEBUG) {
                    System.out.println("[FactoryRegistry] INFO: " + current.getClass().getName() + " provides " + protocol);
                }
            }
        }
    }
    
    public static Connection openConnection(String protocol, String name, int mode, boolean timeouts) throws IOException {
        IConnectionFactory factory= (IConnectionFactory) FACTORIES.get(protocol);
        
        if(factory == null) {
            throw new ConnectionNotFoundException(protocol + ":" + name);
        }
        
        return factory.openPrim(protocol, name, mode, timeouts);
    }
}
