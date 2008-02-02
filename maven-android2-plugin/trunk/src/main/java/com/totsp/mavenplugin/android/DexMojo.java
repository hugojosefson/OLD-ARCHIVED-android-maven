package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Run the android dx tool to create dex resources.
 * 
 * @goal dex
 * @phase process-classes
 * @requiresDependencyResolution compile
 * @description Generates the Android dex resource file
 * 
 * @author charlie collins
 */
public class DexMojo extends AbstractAndroidMojo {

    private AbstractScriptHandler handler;
    private boolean isUnix;

    public DexMojo() {
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

        if (!this.getOutput().exists()) {
            this.getLog().info("creating output - " + this.getOutput().getAbsolutePath());
            this.getOutput().mkdirs();
        }

        if (!isUnix) {
            // WINDOWS
            // TODO windows
            throw new UnsupportedOperationException("Windows not yet supported");
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;
                File commandFile = unixHandler.writeDexScript(this);
                unixHandler.runScriptUnix(commandFile, this);
            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }
    }

}
