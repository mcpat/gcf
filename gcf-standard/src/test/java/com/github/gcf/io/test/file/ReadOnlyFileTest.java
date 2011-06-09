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

package com.github.gcf.io.test.file;

import java.net.URL;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.IllegalModeException;

import junit.framework.TestCase;

public class ReadOnlyFileTest extends TestCase {
    private String _classesFolderName;
    
    protected void setUp() throws Exception {
        URL location= getClass().getProtectionDomain().getCodeSource().getLocation();
        _classesFolderName= location.getPath();
    }

    public void testReadOnlyFile() throws Exception {
        String path= _classesFolderName + "testfile.txt";
        String url= "file://" + path;
        
        FileConnection file= (FileConnection) Connector.open(url, Connector.READ);

        assertTrue("file url is not parsed correctly", file.exists());
        assertEquals("invalid file size", 62L, file.fileSize());
        
        file.canRead();
        file.canWrite();
        
        assertEquals("invalid file name", "testfile.txt", file.getName());
        assertEquals("invalid file path", path, file.getPath());
        assertEquals("invalid file url", url, file.getURL());
        
        assertFalse("file is not recognised correctly", file.isDirectory());
        assertFalse("file attributes are not recognised", file.isHidden());
        
        try {
            file.create();
            fail("create: mode check failed");
        } catch (Exception e) {
            assertEquals("create: mode check failed", e.getClass(), IllegalModeException.class);
        }
        
        try {
            file.delete();
            fail("delete: mode check failed");
        } catch (Exception e) {
            assertEquals("delete: mode check failed", e.getClass(), IllegalModeException.class);
        }
        
        try {
            file.setReadable(false);
            fail("setReadable: mode check failed");
        } catch (Exception e) {
            assertEquals("setReadable: mode check failed", e.getClass(), IllegalModeException.class);
        }
        
        try {
            file.setWritable(false);
            fail("setWritable: mode check failed");
        } catch (Exception e) {
            assertEquals("setWritable: mode check failed", e.getClass(), IllegalModeException.class);
        }
        
        try {
            file.truncate(0);
            fail("truncate: mode check failed");
        } catch (Exception e) {
            assertEquals("truncate: mode check failed", e.getClass(), IllegalModeException.class);
        }
        
        if(file.availableSize() < 0) {
            fail("invalid available size");
        }
        
        if(file.totalSize() < 0) {
            fail("invalid total size");
        }
        
        file.close();
    }
    
    public void testReadOnlyFolder() throws Exception {
        
    }
}
