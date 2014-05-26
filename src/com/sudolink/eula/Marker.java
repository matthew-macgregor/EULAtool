/*
 * The MIT License
 *
 * Copyright 2010 - 2014 Matthew MacGregor.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/** 
 * The code checks for a file located at the provided path. If the file exists
 * it is deserialized into a HashMap<String, Integer>. Each entry in the map 
 * represents one license. The existence of the entry indicates that the license
 * has been accepted (values are ignored), although it has been designed to 
 * allow for more complexity in the future.
 * 
 * @author Matthew MacGregor
 */
class Marker { //deliberately package-private

    public Marker( String path ) {
        marker = new HashMap<>();
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    /**
     * Checks if the EULA for the given key has been accepted by the user.
     * @param key
     * @return 
     */
    public boolean isEulaAccepted( String key ) {
        try {
            read();
        } catch (IOException ex) {
            return false;
        }
        
        return marker.containsKey(key);
    }
    
    /**
     * Writes the marker to disk.
     * 
     * @return True if the write succeeds.
     */
    public boolean commit( ) {
        try {
            write();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Adds a key to the map and commits it in one step.
     * @param key
     * @return True if the write succeeds.
     */
    public boolean commit( String key ) {
        put(key);
        return commit();
    }
    
    /**
     * Adds a key to the map with the default EULA_ACCEPTED code.
     * @param key The key that is being added. Duplicate keys will overwrite 
     * one another.
     */
    public void put(String key) {
        marker.put(key, EULA_ACCEPTED);
    }
    
    /**
     * Adds the key, value to the map. Duplicate keys will overwrite one another.
     * 
     * @param key The key to be stored.
     * @param value The value for that key.
     */
    public void put( String key, int value ) {
        marker.put(key, value);
    }
    
    /**
     * Fetches the value for the given key in the Map.
     * 
     * @param key The key to fetch.
     * @return The int value for that key.
     */
    public int get(String key ) {
        Integer val;
        try {
            val = marker.get(key);
        } catch( ClassCastException e ) {
            val = EULA_UNKNOWN;
        }
        
        return ( val != null ) ?  val : EULA_UNKNOWN;
    }
   
    /**
     * Reads the marker file into memory.
     * @throws IOException if the read fails.
     */
    private void read() throws IOException {
        if( path == null ) {
            throw new NullPointerException("Path to eula acceptance cannot be null");
        }
        
        ObjectInputStream ois;
        File inputFile = new File(path);
        if (inputFile.exists()) {
            try {
                ois = new ObjectInputStream(
                        new FileInputStream(inputFile)
                );
                marker = (HashMap<String, Integer>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new IOException(e.getMessage());
            }

        } 
       
    }

    /**
     * Writes the in-memory map back to disk. 
     * @throws IOException If the write fails for any reason.
     */
    private void write() throws IOException {
        if( path == null ) {
            throw new NullPointerException("Path to eula acceptance cannot be null");
        }
        File f = new File(path);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
        }

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) {
            os.writeObject(marker);
        }

    }
    /* Path to the marker file. */
    private String path;
    /* Individual licenses are identified in a dictionary. */
    private Map<String, Integer> marker;
    /* Constant used to represent acceptance of the EULA. */
    private static final int EULA_ACCEPTED = 1010;
    private static final int EULA_UNKNOWN = 0;
}
