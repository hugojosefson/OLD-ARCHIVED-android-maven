package com.totsp.mavenplugin.android;

import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Validate Android SDK is present and we are ready to roll.
 * 
 * @phase validate
 * @goal validate
 * 
 * @author charlie collins
 */
public class ValidateMojo extends AbstractAndroidMojo {

    public ValidateMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {

        if (this.getAndroidHome() == null || !this.getAndroidHome().exists()) {
            throw new MojoFailureException("Android SDK not found (did you define android.home in ~/.m2/settings.xml?)");
        }
        
        // TODO check all required params are present/rwable, etc

        try {
            this.getLog().info("Found Android SDK - " + this.getAndroidHome().getCanonicalPath());
        } catch (IOException e) {
            // swallow
        }

    }

}
