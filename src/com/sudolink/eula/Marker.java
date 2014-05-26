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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

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

    public Marker(String path) {
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
     *
     * @param key
     * @return
     */
    public boolean isEulaAccepted(String key) {
        
        if( read() ) {
            return marker.containsKey(key);
        }    
 
        return false;
    }

    /**
     * Writes the marker to disk.
     *
     * @return True if the write succeeds.
     */
    public boolean commit() {
        try {
            write();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    /**
     * Adds a key to the map and commits it in one step.
     *
     * @param key
     * @return True if the write succeeds.
     */
    public boolean commit(String key) {
        put(key);
        return commit();
    }

    /**
     * Adds a key to the map with the default EULA_ACCEPTED code.
     *
     * @param key The key that is being added. Duplicate keys will overwrite one
     * another.
     */
    public void put(String key) {
        marker.put(key, EULA_ACCEPTED);
    }

    /**
     * Adds the key, value to the map. Duplicate keys will overwrite one
     * another.
     *
     * @param key The key to be stored.
     * @param value The value for that key.
     */
    public void put(String key, int value) {
        marker.put(key, value);
    }

    /**
     * Fetches the value for the given key in the Map.
     *
     * @param key The key to fetch.
     * @return The int value for that key.
     */
    public int get(String key) {
        Integer val;
        try {
            val = marker.get(key);
        } catch (ClassCastException e) {
            val = EULA_UNKNOWN;
        }

        return (val != null) ? val : EULA_UNKNOWN;
    }

    /**
     * Reads the marker file into memory.
     *
     * @throws IOException if the read fails.
     */
    private boolean read() {

        if (path == null) {
            throw new NullPointerException("Path to eula acceptance cannot be null");
        }

        boolean didSucceed = false;
        File inputFile = new File(path);
        
        if (inputFile.exists()) {

            // Catch IOExceptions specifically, because these indicate a kind of
            // failure that's different from attempts to deserialize the file. No
            // need to keep trying in this case.
            try {
                // First try reading the file from the Base64 implementation
                didSucceed = readV2(inputFile);
                
                if( didSucceed == false ) {
                    // Fall back to the older binary implementation
                    didSucceed = readV1(inputFile); 
                }
                
            } catch (IOException ex) {
                
                didSucceed = false; //redundant, but for readability
            
            }
        }

        return didSucceed;

    }

    /**
     * Writes the in-memory map back to disk.
     *
     * @throws IOException If the write fails for any reason.
     */
    private void write() throws IOException {
        
        if (path == null) {
            throw new NullPointerException("Path to eula acceptance cannot be null");
        }
        
        File f = new File(path);
        
        // Create the needed directories if they don't exist.
        if ( ! f.exists() ) {
            f.getParentFile().mkdirs();
        }

        String b64string;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream os = new ObjectOutputStream(baos)) {
            os.writeObject(marker);
            b64string = DatatypeConverter.printBase64Binary(baos.toByteArray());
        }
        Files.write(Paths.get(path), b64string.getBytes());

    }

    /**
    * Writes the in-memory map back to disk. This method is no longer used but
    * is preserved here for backwards compatibility.
    * 
    * @throws IOException If the write fails for any reason.
    * @deprecated 
    */
    @Deprecated
    private void writeV1() throws IOException {
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
    
    /**
     * This is the original method for reading the marker file. Markers were
     * serialized to binary using ObjectOutputStream and written directly. If
     * the file is appropriately formatted, this method will read this format.
     * Provided for backwards compatibility.
     * 
     * @param f The marker file.
     * @return True if the read operation has completed successfully.
     * @throws IOException If there are problems reading the file that aren't 
     * related to the deserialization. 
     * @deprecated Always use readV2.
     */
    @Deprecated
    private boolean readV1(File f) throws IOException {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            marker = (HashMap<String, Integer>) ois.readObject();
            // Now that we've got the data, overwrite the file to bring
            // it up to the latest version.
            write();
            return true;

        } catch (  ClassNotFoundException ex) {
            
            //If we've gotten to this point there's a real problem with
            //the file. We'd be better off killing it and starting over.
           
            Files.delete(Paths.get(f.getAbsolutePath()));
           
        }

        return false;
    }
    
    /**
     * This is the new method for reading the marker file. Markers were
     * serialized to binary using ObjectOutputStream then encoded to b64.
     * If the file is appropriately formatted (v2), this method will read this 
     * format.
     * 
     * @param f The marker file.
     * @return True if the read operation has completed successfully.
     * @throws IOException If there are problems reading the file that aren't 
     * related to the deserialization. 
     */
    private boolean readV2(File f) throws IOException {
        // We're going to attempt to read the marker file using the newer Base64
        // logic: basically, decode b64 -> binary, then deserialize the bytes.
        try {
            
            String s = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
            // Convert the b64 string into a byte array (stream) 
            ByteArrayInputStream bais = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(s));
            // Deserialize into an object again
            try (ObjectInputStream ois2 = new ObjectInputStream(bais)) {
                marker = (HashMap<String, Integer>) ois2.readObject();
            } 
            // Success!
            return true;
            
        } catch ( ClassNotFoundException | ArrayIndexOutOfBoundsException ex) {
            // Any number of things might have gone wrong, but we actually don't
            // care which bad thing happened. In the case of failure we should
            // try again with the older version.
            return false;
        }
        
        
    }

    /* Path to the marker file. */
    private String path;
    /* Individual licenses are identified in a dictionary. */
    private Map<String, Integer> marker;
    /* Constants */
    private static final int EULA_ACCEPTED = 1010;
    private static final int EULA_UNKNOWN = 0;
}
