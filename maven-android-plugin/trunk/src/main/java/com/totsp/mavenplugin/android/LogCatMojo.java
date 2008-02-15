package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @goal logcat
 * @requiresDependencyResolution compile
 * @description Run adb logcat if emulator is running.
 * 
 * @author charlie collins
 */
public class LogCatMojo extends AbstractAndroidMojo {

    public LogCatMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();        
        if (!isUnix) {
            try {
                ScriptHandlerWindows winHandler = (ScriptHandlerWindows) handler;
                File commandFile = winHandler.writeLogCatScript(this);
                winHandler.runScriptWindows(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                File commandFile = unixHandler.writeLogCatScript(this);
                unixHandler.runScriptUnix(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }            
        }        
    }

}
