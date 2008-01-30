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
        this.estCommand();
        exec = new DefaultExecutor();
    }

    private void estCommand() {
        try {
            command = this.getAaptTool().getCanonicalPath();
            command += "compile -m -j " + this.getSrcDir().getCanonicalPath() + " -M AndroidManifest.xml -S "
                    + this.getResDir().getCanonicalPath() + " -I " + this.getAndroidJar().getCanonicalPath();
            this.getLog().warn("command = \"" + command + "\"");
        } catch (IOException e) {
            // TODO handle this better
            System.out.println(e.getStackTrace());
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        // aapt compile -m -j [SRC] -M AndroidManifest.xml -S [RES_DIR] -I
        // android.jar

        // use commons-exec to launch emulator
        CommandLine cl = new CommandLine(command);

        try {
            exec.execute(cl, new ExecuteResultHandler() {
                public void onProcessComplete(int exitValue) {
                    System.out.println("exitValue of process - " + exitValue);
                }

                public void onProcessFailed(ExecuteException e) {
                    System.out.println(e.getMessage());
                }

            });

        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
