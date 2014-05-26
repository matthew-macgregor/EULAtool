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

import java.awt.Color;

/**
 *
 * @author Matthew MacGregor
 */
public interface EULA {
    /**
     * Launches the EULA viewer frame. Call this method after you've added any
     * licenses.
     */
    public void start();
    
        /**
     * Adds a license to be displayed to the user.
     *
     * @param key A user-specified string to identify the license. If key
     * exists, it will overwrite previous values.
     *
     * @param path The path to the license file that is embedded in the jar
     * file. Note that licenses that aren't embedded are not supported at this
     * time.
     */
    public void addLicense(String key, String path);
    
    /**
     * Sets implementation-specific configuration details defined by type.
     * 
     * @param type The implementation-specific configuration item.
     * @param value The boolean value for the configuration point.
     */
    public void setConfiguration( int type, boolean value);
    
    /**
     * Allows for implementation-specific configuration of colors.
     * @param type
     * @param c 
     */
    public void setColor( int type, Color c);
    
}
