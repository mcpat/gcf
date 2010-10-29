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

package com.github.gcf.io.jse;

import java.io.File;
import java.util.Enumeration;
import java.util.NoSuchElementException;

class FileEnumerator implements Enumeration {
    private final File[] _files;
    private int _index;
    
    FileEnumerator(File[] files) {
        _files= files;
        _index= 0;
    }

    public boolean hasMoreElements() {
        return _files != null && _index < _files.length;
    }

    public Object nextElement() {
        if(_files == null || _index >= _files.length) {
            throw new NoSuchElementException("file enumerator");
        }
        
        File f= _files[_index++];
        // TODO: check if directory names have a trailing slash
        return f.getName();
    }
}
