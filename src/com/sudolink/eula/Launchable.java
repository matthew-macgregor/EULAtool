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

/**
 * @author Matthew MacGregor
 */
public interface Launchable {

    /**
     * EULATool provides a dead-simple implementation to show license(s) and
     * ensuring that the user has accepted them before launching an application.
     * Upon acceptance of the license(s), EULATool calls this method. Wrap your
     * application initialization code in the launchApplication() method and
     * pass parameters from main() and it should require very few changes to 
     * your application.
     * 
     * @param isEulaAccepted True if the EULA has been accepted by the user.
     * @param args Allows an arbitrary array of arguments as a pass-through. May
     * be used to pass command-line arguments to the launching application.
     */
    public void launchApplication(boolean isEulaAccepted, String[]  args);

}
