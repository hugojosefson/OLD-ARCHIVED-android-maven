package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal generate-sources
 * @phase generate-sources
 * @description Generates the Android R.java resource file
 * 
 * @author charlie collins
 */
public class GenerateRMojo extends AbstractAndroidMojo {

    public GenerateRMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        if (!isUnix) {
            // WINDOWS
            // TODO windows
            throw new UnsupportedOperationException("Windows not yet supported");
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                File commandFile = unixHandler.writeRScript(this);
                unixHandler.runScriptUnix(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }
    }

}
