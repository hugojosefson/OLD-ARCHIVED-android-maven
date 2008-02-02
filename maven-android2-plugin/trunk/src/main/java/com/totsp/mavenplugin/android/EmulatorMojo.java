package com.totsp.mavenplugin.android;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal emul
 * @phase 
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

        // install and run the apk file 
        // TODO
    }

}
