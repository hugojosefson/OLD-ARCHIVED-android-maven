package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Run the android aapt tool to generate the R resource file.
 * 
 * @goal genr
 * @phase generate-sources
 * @description Generates the Android R.java resource file
 * 
 * @author charlie collins
 */
public class GenerateRMojo extends AbstractAndroidMojo {

    private AbstractScriptHandler handler;
    private boolean isUnix;

    public GenerateRMojo() {
        super();

        if (System.getProperty("os.name").toLowerCase(Locale.US).startsWith("windows")) {
            this.getLog().info("os = WINDOWS");
            // handler = new ScriptHandlerWindows();
        } else {
            this.getLog().info("os = UNIX (variant)");
            isUnix = true;
            handler = new ScriptHandlerUnix();
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {

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
