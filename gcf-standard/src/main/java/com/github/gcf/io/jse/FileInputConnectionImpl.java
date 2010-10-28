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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.InputConnection;

import com.github.gcf.io.AbstractConnection;


/**
 * @author Marcel Patzlaff
 */
final class FileInputConnectionImpl extends AbstractConnection implements InputConnection {
    protected final File file;

    public FileInputConnectionImpl(File file) throws IOException {
        this.file= file;
    }
    
    public DataInputStream openDataInputStream() throws IOException {
        ensureOpen();
        return new DataInputStream(openInputStream());
    }

    public InputStream openInputStream() throws IOException {
        ensureOpen();
        return new FileInputStream(file);
    }
}
