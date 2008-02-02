package com.totsp.mavenplugin.android;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal emul
 * 
 * @requiresDependencyResolution compile
 * @description Runs the Android emulator //@execute phase=package
 * 
 * @author charlie collins
 */
public class EmulatorMojo extends AbstractAndroidMojo {


    public EmulatorMojo() {
        super();
        
    }

    public void execute() throws MojoExecutionException, MojoFailureException {

        // removed commons exec, TODO replace with script writer stuff
    }

}
