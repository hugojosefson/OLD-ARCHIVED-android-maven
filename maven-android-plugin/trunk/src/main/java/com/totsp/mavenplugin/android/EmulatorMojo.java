package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal install
 * @phase install  
 * @requiresDependencyResolution compile
 * @description Runs the Android emulator and installs APK file
 * 
 * @author charlie collins
 */
public class EmulatorMojo extends AbstractAndroidMojo {

    public EmulatorMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        if (!isUnix) {
            // WINDOWS
            try {
                ScriptHandlerWindows winHandler = (ScriptHandlerWindows) handler;
                File commandFile = winHandler.writeInstallApkScript(this);
                winHandler.runScriptWindows(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                File commandFile = unixHandler.writeInstallApkScript(this);
                unixHandler.runScriptUnix(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }
        
    }

}
