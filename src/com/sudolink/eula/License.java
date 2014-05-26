/*
 * The MIT License
 *
 * Copyright 2014 Matthew.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sudolink.eula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matthew MacGregor
 * May 2014
 */
class License { //deliberately package-private

    public License(String key, String path) {
        this.key = key;
        this.path = path;
    }
    
    /**
     * Reads the data from a text file that is stored in the jar file as a
     * resource.
     *
     * @return
     */
    public final String read() {
        String thisLine;
        String fileText = "";
        
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if( is == null ) { 
                /* 
                *  Path isn't valid, return null. 
                */
                return null;
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is)
            );
            while ((thisLine = br.readLine()) != null) {
                fileText += thisLine + "\n";
            }

        } catch (IOException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fileText;
    }
    
    /**
     * The key represents a unique value for the license. 
     * @return the key
     */
    public String getKey() {
        return key;
    }
    
    private final String path;
    private final String key;
}
