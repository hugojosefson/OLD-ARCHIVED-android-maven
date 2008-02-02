package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ccollins
 */
public class ScriptHandlerUnix extends AbstractScriptHandler{
    
    /** Creates a new instance of ScriptWriterUnix */
    public ScriptHandlerUnix() {        
    }
    
    @SuppressWarnings("static-access")
    public File writeDexScript(AbstractAndroidMojo mojo) throws IOException
    {                
        String filename = "dex.sh";
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
        
        writer.print(mojo.getDxTool().getCanonicalPath());        
        writer.print(" -Jmx383m");
        writer.print(" --dex");
        writer.print(" --output=" + mojo.getDexFileDir().getAbsolutePath());
        writer.print(" --locals=full");
        writer.print(" --positions=lines");
        writer.print(" " + mojo.getBuildDir().getAbsolutePath());       
        writer.println();
        
        writer.flush();
        writer.close();        
        return file;
    }
    
    
    @SuppressWarnings("static-access")
    public File writeRScript(AbstractAndroidMojo mojo) throws IOException
    {                
        String filename = "genr.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));
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
