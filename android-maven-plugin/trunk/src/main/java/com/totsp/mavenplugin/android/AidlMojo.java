package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.totsp.mavenplugin.android.AbstractAndroidMojo;
import com.totsp.mavenplugin.android.ScriptHandlerUnix;
import com.totsp.mavenplugin.android.ScriptHandlerWindows;

/**
 * @goal aidl
 * @phase generate-sources 
 * @description Generates Java classes from AIDL IPC files
 * 
 * @author charlie collins
 */
public class AidlMojo extends AbstractAndroidMojo {

    public AidlMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        if (!isUnix) {
            try {                 
                ScriptHandlerWindows winHandler = (ScriptHandlerWindows) handler;
                File commandFile = winHandler.writeAidlScript(this);
                winHandler.runScriptWindows(commandFile, this);                
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                File commandFile = unixHandler.writeAidlScript(this);
                unixHandler.runScriptUnix(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }
    }

}
