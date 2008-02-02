package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Run the android aapt tool to generate the R resource file.
 * 
 * @goal genr
 * @requiresDependencyResolution compile
 * @description Generates the Android R.java resource file
 * 
 * @author charlie collins
 */
public class GenerateRMojo extends AbstractAndroidMojo {

    private AbstractScriptHandler handler;
    private boolean isUnix;
  
    public GenerateRMojo() {
        super();
        
        if(System.getProperty("os.name").toLowerCase(Locale.US).startsWith("windows")) {            
            this.getLog().info("os = WINDOWS");
            //handler = new ScriptHandlerWindows();
        } else {
            this.getLog().info("os = UNIX (variant)");
            isUnix = true;
            handler = new ScriptHandlerUnix();
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        // aapt compile -m -j [SRC] -M AndroidManifest.xml -S [RES_DIR] -I
        // android.jar

        // TODO debug logging in validator
        ///System.out.println("srcDir - " + this.getSrcDir());
        ///System.out.println("resDir - " + this.getResDir());
        ///System.out.println("android.home - " + this.getAndroidHome());
        ///System.out.println("android jar - " + this.getAndroidJar());
        ///System.out.println("aaptTool - " + this.getAaptTool());

        /*
        if (this.getAaptTool() == null || this.getSrcDir() == null || this.getResDir() == null
                || this.getAndroidJar() == null) {
            // TODO have validator handle all the validation?
            throw new MojoFailureException("VALIDATION/ENV PROBLEM - abort");
        }
        */

        if( !this.getOutput().exists() ){
            this.getLog().info("creating output - " + this.getOutput().getAbsolutePath());
            this.getOutput().mkdirs();
        }
        
        if(!isUnix) {
            // WINDOWS
            // TODO windows
            throw new UnsupportedOperationException("Windows not yet supported");
        } else {
            // UNIX            
            try{
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                File commandFile = unixHandler.writeRScript(this);
                unixHandler.runScriptUnix(commandFile); 
                this.getLog().info("generate R complete");
            } catch(Exception e){
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }         
    }   

}
