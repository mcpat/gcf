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

package com.github.gcf.io.test.file;

import java.util.Enumeration;

import javax.microedition.io.file.FileSystemListener;
import javax.microedition.io.file.FileSystemRegistry;

import junit.framework.TestCase;

public class FileSystemRegistryTest extends TestCase {
    private final static String THREAD_CLASS_NAME= "com.github.gcf.io.jse.FileSystemRegistryImpl$CheckerTask";
    
    public void testFileSystemRegistry() throws Exception {
        Enumeration roots= FileSystemRegistry.listRoots();
        
        assertTrue("invalid root listing", roots.hasMoreElements());
        
        while(roots.hasMoreElements()) {
            System.out.println(roots.nextElement());
        }
        
        final FileSystemListener fsl= new FileSystemListener() {
            public void rootChanged(int state, String rootName) {
                String stateStr= state == ROOT_ADDED ? "ADDED" : "REMOVED";
                System.out.println(stateStr + ": " + rootName);
            }
        };
        
        assertTrue("cannot attach listener", FileSystemRegistry.addFileSystemListener(fsl));
        assertFalse("attached listener multiple times", FileSystemRegistry.addFileSystemListener(fsl));
        
        Thread.sleep(1000);
        
        int count= 0;
        for(Thread thr : Thread.getAllStackTraces().keySet()) {
            if(thr.getClass().getName().equals(THREAD_CLASS_NAME)) {
                count++;
            }
        }
        
        assertEquals("registry did not start polling", 1, count);
        
        assertTrue("cannot detach listener", FileSystemRegistry.removeFileSystemListener(fsl));
        assertFalse("detached listener multiple times", FileSystemRegistry.removeFileSystemListener(fsl));
        
        Thread.sleep(1000);
        
        count= 0;
        for(Thread thr : Thread.getAllStackTraces().keySet()) {
            if(thr.getClass().getName().equals(THREAD_CLASS_NAME)) {
                count++;
            }
        }
        
        assertEquals("registry did not stop polling", 0, count);
    }
}
