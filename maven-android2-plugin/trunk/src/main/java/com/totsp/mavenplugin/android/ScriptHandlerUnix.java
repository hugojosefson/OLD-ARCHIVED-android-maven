/*
 * ScriptWriterUnix.java
 *
 * Created on November 23, 2007, 12:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// aapt package/compile/delete/add
/*
Modifiers:
    -d  one or more device assets to include, separated by commas
    -c  compile resources from assets
    -f  force overwrite of existing files
    -g  only write generated assets; do not package source assets
    -j  specify a jar or zip file containing classes to include
    -l  one or more locale assets to include, separated by commas
    -m  make package directories under location specified by -J
    -s  sync existing packages (update + remove deleted files)
    -u  update existing packages (add new, replace older)
    -v  verbose output
    -x  create extending (non-application) resource IDs
    -z  require localization of resource attributes marked with localization="suggested"
    -A  additional directory in which to find raw asset files
    -I  add an existing package to base include set
    -J  specify where to output R.java resource constant definitions
    -M  specify full path to AndroidManifest.xml to include in zip
    -P  specify where to output public resource definitions
    -R  specify directory containing generated resource files (in or output)
    -S  additional directory in which to find resource source assets
    -0  don't compress files we're adding
*/


/**
 *
 * @author ccollins
 */
public class ScriptHandlerUnix extends AbstractScriptHandler{
    
    /** Creates a new instance of ScriptWriterUnix */
    public ScriptHandlerUnix() {        
    }
    
    @SuppressWarnings("static-access")
    public File writeRScript(AbstractAndroidMojo mojo) throws IOException
    {                
        String filename = "genr.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        //Collection<File> classpath = mojo.buildRuntimeClasspathList();
        File sh = new File("/bin/bash");
        if( !sh.exists() ){
            sh = new File("/usr/bin/bash");
        }
        if( !sh.exists() ){
            sh = new File("/bin/sh");
        }
        writer.println("#!"+sh.getAbsolutePath());        
        
        writer.print(mojo.getAaptTool().getCanonicalPath());        
        writer.print(" compile");
        writer.print(" -m");
        writer.print(" -J " + mojo.getTargetRDir());
        writer.print(" -M " + mojo.getResourcesDir().getAbsolutePath() + "/AndroidManifest.xml");
        writer.print(" -S " + mojo.getResDir().getAbsolutePath());
        writer.print(" -I " + mojo.getAndroidJar().getAbsolutePath());       
        writer.println();
        
        writer.flush();
        writer.close();        
        return file;
    }
    
    
    
    
 
    
}
