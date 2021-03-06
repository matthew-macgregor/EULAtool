/*
 * Viewer.java
 * Created on Apr 25, 2010, 8:22:11 AM
 * 
 * The MIT License
 *
 * Copyright 2010 - 2014 Matthew MacGregor
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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Creates a EULA acceptance frame that will present the user with all of the
 * licenses for the application. If any of the licenses are not accepted, the
 * application will not be launched. This is useful for embedding licenses into
 * runnable jar files that have no installer.
 *
 * @author Matthew MacGregor
 */
public class Viewer extends javax.swing.JFrame implements EULA {

    public class Config {
        public static final int PROGRESS_VISIBLE = 1;
    }
    /**
     *
     *
     * @param eulaPath This is the path to the EULA acceptance marker.
     * Generally, the path to this file should be in an applications settings
     * directory (such as AppData on windows).
     * @param application If the user accepts all of the licenses presented,
     * this application will be launched.
     */
    public Viewer(String eulaPath, Launchable application) {
        this.setUndecorated(true);
        initComponents();
        app = application;

        //Set a temporary license agreement file location
        marker = new Marker(new File(eulaPath).getAbsolutePath());
        licenses = new ArrayList<>();
    }

    /**
     * Adds a license to be displayed to the user.
     *
     * @param key A user-specified string to identify the license. If key
     * exists, it will overwrite previous values.
     *
     * @param jarpath The path to the license file that is embedded in the jar
     * file. Note that licenses that aren't embedded are not supported at this
     * time.
     */
    @Override
    public void addLicense(String key, String jarpath) {
        licenses.add(new License(key, jarpath));
    }

    /**
     * Launches the EULA viewer frame. Call this method after you've added any
     * licenses.
     */
    @Override
    public void start() {
        if(isEulaAccepted()) {
            launchApplication(true);
        } else {
            if(hasLicense()) {
                progressBar.setMaximum(licenses.size());
                setLocationRelativeTo(null);
                setVisible(true);
                displayLicense();
            } else {
                //If there are no licenses registered, assume there's nothing
                //to sign and let the user in.
                launchApplication(true);
            }
        }
    }
    

//<editor-fold defaultstate="uncollapsed" desc="Configuration options">

    /**
     * Sets implementation-specific configuration details defined by type.
     * 
     * @param type The implementation-specific configuration item.
     * @param value The boolean value for the configuration point.
     */
    @Override
    public void setConfiguration( int type, boolean value ) {
        switch(type) {
            case Config.PROGRESS_VISIBLE:
                progressBar.setVisible(value);
                break;
        }
    }
    
    @Override
    public void setColor(int type, Color c) {
        backgroundPanel.setBackground(c);
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        foregroundPanel = new javax.swing.JPanel();
        labelTopHeader = new javax.swing.JLabel();
        scrollPaneLicense = new javax.swing.JScrollPane();
        textAreaLicense = new javax.swing.JTextArea();
        checkboxEulaAgree = new javax.swing.JCheckBox();
        buttonDecline = new javax.swing.JButton();
        buttonAccept = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(102, 102, 102));

        backgroundPanel.setBackground(new java.awt.Color(129, 156, 161));

        labelTopHeader.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTopHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTopHeader.setText("License Agreement");

        scrollPaneLicense.setHorizontalScrollBar(null);

        textAreaLicense.setEditable(false);
        textAreaLicense.setColumns(20);
        textAreaLicense.setLineWrap(true);
        textAreaLicense.setRows(5);
        textAreaLicense.setWrapStyleWord(true);
        scrollPaneLicense.setViewportView(textAreaLicense);

        checkboxEulaAgree.setText("I agree to be bound by the terms of this license.");

        buttonDecline.setText("Decline");
        buttonDecline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeclineActionPerformed(evt);
            }
        });

        buttonAccept.setText("Accept");
        buttonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAcceptActionPerformed(evt);
            }
        });

        progressBar.setVisible(false);

        org.jdesktop.layout.GroupLayout foregroundPanelLayout = new org.jdesktop.layout.GroupLayout(foregroundPanel);
        foregroundPanel.setLayout(foregroundPanelLayout);
        foregroundPanelLayout.setHorizontalGroup(
            foregroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(foregroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(foregroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, foregroundPanelLayout.createSequentialGroup()
                        .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 192, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(buttonDecline)
                        .add(18, 18, 18)
                        .add(buttonAccept)
                        .add(19, 19, 19))
                    .add(foregroundPanelLayout.createSequentialGroup()
                        .add(labelTopHeader, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, foregroundPanelLayout.createSequentialGroup()
                        .add(foregroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(scrollPaneLicense, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                            .add(foregroundPanelLayout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 223, Short.MAX_VALUE)
                                .add(checkboxEulaAgree)))
                        .addContainerGap())))
        );
        foregroundPanelLayout.setVerticalGroup(
            foregroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(foregroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(labelTopHeader)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollPaneLicense, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(checkboxEulaAgree)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(foregroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(foregroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(buttonDecline)
                        .add(buttonAccept))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout backgroundPanelLayout = new org.jdesktop.layout.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(foregroundPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(foregroundPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, backgroundPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonDeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeclineActionPerformed
        this.dispose();
        launchApplication(false);
    }//GEN-LAST:event_buttonDeclineActionPerformed

    private void buttonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAcceptActionPerformed
        if (this.checkboxEulaAgree.isSelected()) {
            // Process the current license that's being displayed
            marker.put(popLicense().getKey());
            progressBar.setValue(progressBar.getValue() + 1);
            displayLicense();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "You must select the checkbox if you accept the license agreement.");
        }
    }//GEN-LAST:event_buttonAcceptActionPerformed
    
    private boolean isEulaAccepted() {
        List<License> unacceptedLicenses = new ArrayList<>();
        //Filter out the licenses that have been accepted
        for(License l : licenses) {
            if (marker.isEulaAccepted(l.getKey()) == false) {
                unacceptedLicenses.add(l);
            }
        }
        
        licenses = unacceptedLicenses;
        //Let the application know that all licenses are accepted
        return licenses.isEmpty();
    }
    
    private boolean hasLicense() {
        return licenses.isEmpty() == false;
    }
    
    private void displayLicense() {
        // Check if we're done, launch the application, otherwise prepare
        // the next license.
        if(hasLicense()) {
            try {
                prepareLicense();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "An important file is missing.\nPlease contact the software vendor to correct this issue.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                launchApplication(false);
            }
        } else {
            // If there are no remaining licenses, all have been accepted
            launchApplication(true);
        }
    }
    
    private void launchApplication(boolean isEulaAccepted) {
        app.launchApplication(isEulaAccepted, args);
        if(isEulaAccepted) {
            marker.commit();
        }
        dispose();
    }
    
    private License prepareLicense() throws FileNotFoundException {
        License license;
        if (licenses.isEmpty()) {
            return null;
        }
        
        license = licenses.get(0);
        String licenseText = null;
        
        if (license != null) {
            licenseText = license.read();
        }
        
        if (license == null || licenseText == null) {
            throw new FileNotFoundException("The license file could not be opened.");
        }
        
        textAreaLicense.setText(licenseText);
        textAreaLicense.setCaretPosition(0);
        checkboxEulaAgree.setSelected(false);
        
        return license;
    }
    
    private License popLicense() {
        if (licenses.isEmpty()) {
            return null;
        }
        
        return licenses.remove(0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private members">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton buttonAccept;
    private javax.swing.JButton buttonDecline;
    private javax.swing.JCheckBox checkboxEulaAgree;
    private javax.swing.JPanel foregroundPanel;
    private javax.swing.JLabel labelTopHeader;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JScrollPane scrollPaneLicense;
    private javax.swing.JTextArea textAreaLicense;
    // End of variables declaration//GEN-END:variables

    //The application to be launched if the user accepts the EULA.
    private final Launchable app;
    //Arguments to be passed to the application from the command line.
    private String[] args;
    //Marker that indicates the user has accepted EULA.
    private final Marker marker;
    //List of the licenses to be displayed to the user.
    private List<License> licenses;
    // </editor-fold>
}
