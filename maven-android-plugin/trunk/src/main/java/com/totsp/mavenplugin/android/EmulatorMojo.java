package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal emul
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
                
                // start emul
                File commandFile = winHandler.writeStartEmulScript(this);
                winHandler.runScriptWindows(commandFile, this);
                
                // pause for emul to start
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // swallow
                }
                
                // install apk
                commandFile = winHandler.writeInstallApkScript(this);
                winHandler.runScriptWindows(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                
                // start emul
                File commandFile = unixHandler.writeStartEmulScript(this);
                unixHandler.runScriptUnix(commandFile, this);
                
                // pause for emul to start
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // swallow
                }
                
                // install apk
                commandFile = unixHandler.writeInstallApkScript(this);
                unixHandler.runScriptUnix(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }
        
    }

}
