EULAUtility
===========

Java (Swing) library that requires users to accept a jar-embedded EULA. This is 
useful when you're distributing jar files without an installer but still want a 
user to acknowledge a license before using the software. 

**Features:**

* License file is embedded in the jar making standalone jar distributions easy.
* Supports multiple license files for complex projects.
* Adding additional licenses (or new versions) during an update is easy.
* Small amount of source code makes it easy to integrate .java files directly (or as a jar).

**Example Usage:**

        /*
        * This is the path to the marker file EULAUtility uses to track whether 
        * the user has accepted the license. You will generally wan to put this 
        * with the rest of the user-specific configuration data.
        */
        String fullPath = "/path/to/user/app/config/.eula";

        /*
        * Launch an instance of the EULA display class. If the eula is
        * accepted, launch the main application.
        */
        EULA eula = new Viewer(fullPath, new Launchable() {

            @Override
            public void launchApplication(String[] args) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Launch your main application here.
                        new com.myapp.MainForm().setVisible(true);
                    }
                });
            }
        }
        );

        /*
        * 
        * Provide an identifier for this license as well as a path to the
        * license file itself. Please note that the license file needs to
        * be embedded in the jar file and should use an absolute path to 
        * the resource in the jar. 
        *
        * In the example below, license.txt needs to be in /jar/path/to/.
        * 
        * If any of the license files can't be found, the library will abort
        * with a user message.
        */
        eula.addLicense("eula.main", "/jar/path/to/license.txt");
        eula.start();
        
The marker file keeps track of which EULA's the user has accepted. It should be 
in a location that the user has write permissions to and generally it's best to 
keep it with the application's other configuration data. Licenses are tracked 
using the key that you provide to addLicense(key, path). Providing the same key 
twice will simply override the previous license path. 

If you need to specify multiple licenses you may do so by providing new keys. 
For example:

        addLicense( "gpl", "/gpl.txt" );
        addLicense( "mit", "/mit.txt" );
        
This will prompt the user with the content of gpl.txt followed by the contents 
of mit.txt. If the user abandons the process, she will be asked again on the 
next launch of the application. After accepting all of the licenses, EULAUtility 
commits the information to the marker file. The user will no longer be prompted 
to accept the licenses on launch of app.

If you update the jarfile and add a new dependency, it's easy to add a license. 
Just add another line:

        addLicense( "newlicense", "/new.txt" );
        
The user will be prompted to accept the new license but not the original ones.

The keys used to identify a license are arbitrary and can be used to version the 
license. For example, version 1 of a jar might contain this license:

        addLicense( "license.1", "/license.txt" );

When version 2 rolls around, there's a new license. To reprompt the user, replace 
the line above with:

        addLicense( "license.2", "/license.txt" );
        
Because the key is now different, the user will be prompted to accept the new 
license. Please note that it's assumed in this example that the contents of 
license.txt are now different. You could also provide a new filename, but it's 
not necessary. The library doesn't check the contents of the file so you'll need 
to tell it to reprompt by changing the key.


