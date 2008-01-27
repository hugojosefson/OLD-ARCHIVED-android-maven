package com.totsp.mavenplugin.android;

import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;

/**
 * Properties for android-maven.
 * 
 * @author charlie collins
 */
public abstract class AbstractAndroidMojo extends AbstractMojo {

    static public final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
    
    // TODO lookup ANDROID_HOME or such
    static public String ANDROID_PATH = "/opt/android_sdk_darwin_m3-rc37a";
    
    /**
     * Delete all data on user-data disk image before starting.
     */
    private boolean wipeData;

    /**
     * Verbose output.
     */
    private boolean verbose;

    /** Creates a new instance of AbstractAndroidMojo */
    public AbstractAndroidMojo() {
    }

    public boolean isWipeData() {
        return wipeData;
    }

    public void setWipeData(boolean wipeData) {
        this.wipeData = wipeData;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    

}
