package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @author ccollins
 */
public abstract class AbstractScriptHandler {

    /**
     * Run script file on unix systems.
     * 
     * @param file
     */
    public void runScriptUnix(File file, AbstractMojo mojo) throws MojoFailureException {
        try {
            
            // chmod 
            ProcessWatcher pw = new ProcessWatcher("chmod +x " + file.getAbsolutePath());
            pw.startProcess(System.out, System.err);
            int retVal = pw.waitFor();
            if (retVal != 0) {
                throw new MojoFailureException("chmod execution script failed");
            }
            
            // execute
            pw = new ProcessWatcher(file.getAbsolutePath().replaceAll(" ", "\\ "));
            pw.startProcess(System.out, System.err);
            retVal = pw.waitFor();
            if (retVal != 0) {
                throw new MojoFailureException("Script execution failed - " + file.getCanonicalPath());
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run script file on winders systems.
     * 
     * @param file
     */
    public void runScriptWindows(File file, AbstractMojo mojo) throws MojoFailureException {
        try {
            ProcessWatcher pw = new ProcessWatcher("\"" + file.getAbsolutePath() + "\"");
            pw.startProcess(System.out, System.err);
            int retVal = pw.waitFor();
            if (retVal != 0) {
                throw new MojoFailureException("Script execution failed - " + file.getCanonicalPath());
            } else {
                mojo.getLog().info("process execution complete");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Log error lines helper.
     * 
     * @param alloutput
     *                output to pick out error lines to be printed from
     */
    public void logErrorLines(String alloutput, AbstractAndroidMojo mojo) {
        final String errorTag = "[ERROR]";
        String[] lines = alloutput.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(errorTag)) {
                mojo.getLog().error(lines[i].replace(errorTag, ""));
            }
        }
    }

}