package com.totsp.mavenplugin.android;

/**
 * 
 * @author ccollins
 */
public abstract class AbstractScriptWriter {

    /**
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