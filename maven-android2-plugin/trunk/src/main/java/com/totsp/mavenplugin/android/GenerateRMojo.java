package com.totsp.mavenplugin.android;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.Executor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Generate Android R.java resource file.
 * 
 * @goal genr
 * @requiresDependencyResolution compile
 * @description Generates the Android R.java resource file
 * 
 * @author charlie collins
 */
public class GenerateRMojo extends AbstractAndroidMojo {

    private String command;
    private Executor exec;

    public GenerateRMojo() {
        super();
        exec = new DefaultExecutor();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        // aapt compile -m -j [SRC] -M AndroidManifest.xml -S [RES_DIR] -I
        // android.jar

        // TODO debug logging in validator
        System.out.println("srcDir - " + this.getSrcDir());
        System.out.println("resDir - " + this.getResDir());
        System.out.println("android.home - " + this.getAndroidHome());
        System.out.println("android jar - " + this.getAndroidJar());
        System.out.println("aaptTool - " + this.getAaptTool());

        if (this.getAaptTool() == null || this.getSrcDir() == null || this.getResDir() == null
                || this.getAndroidJar() == null) {
            // TODO have validator handle all the validation?
            System.out.println("VALIDATION/ENV PROBLEM - abort");
            return;
        }

        try {
            command = this.getAaptTool().getCanonicalPath();
            command += " compile -m -j " + this.getSrcDir().getCanonicalPath() + " -M "
                    + this.getResourcesDir().getCanonicalPath() + "/AndroidManifest.xml -S "
                    + this.getResDir().getCanonicalPath() + " -I " + this.getAndroidJar().getCanonicalPath();
            this.getLog().warn("command = \"" + command + "\"");
        } catch (IOException e) {
            // TODO handle this better
            System.out.println(e.getStackTrace());
        }

        if (command != null) {
            // use commons-exec to launch emulator
            CommandLine cl = new CommandLine(command);

            try {
                exec.execute(cl);

                /*
                 * , new ExecuteResultHandler() { public void
                 * onProcessComplete(int exitValue) {
                 * System.out.println("exitValue of process - " + exitValue); }
                 * 
                 * public void onProcessFailed(ExecuteException e) {
                 * //System.out.println(e.getMessage()); throw new
                 * MojoFailureException(e.getLocalizedMessage()); } }); // TODO
                 * stop using the result handler with callback, mojo is done and
                 * gone
                 */

            } catch (ExecuteException e) {
                throw new MojoFailureException(e.getLocalizedMessage());
            } catch (IOException e) {
                throw new MojoFailureException(e.getLocalizedMessage());
            }
        } else {
            throw new MojoFailureException("ERROR, command null, nothing to execute");
        }

    }

}
