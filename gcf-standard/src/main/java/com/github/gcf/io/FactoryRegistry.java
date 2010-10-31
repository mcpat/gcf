/*
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
    
    public static Connection openConnection(PrimitiveURI uri, int mode, boolean timeouts) throws IOException {
        String protocol= uri.scheme;
        
        IConnectionFactory factory= (IConnectionFactory) FACTORIES.get(protocol);
        
        if(factory == null) {
            throw new ConnectionNotFoundException(uri.rawString);
        }
        
        return factory.openPrim(protocol, uri, mode, timeouts);
    }
}
