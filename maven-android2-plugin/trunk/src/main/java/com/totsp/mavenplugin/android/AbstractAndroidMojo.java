package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

/**
 * Properties for android-maven.
 * 
 * Goddammmit, can't find a mapping of what maven property is what,
 * not even in the "better builds" book, an appendix, nothing. 
 * What property is what in the "defaults" or standards?
 *
 * 
 * throw examples from the interwebs here - fuck it
 * 
 * ${project.build.outputDirectory}
 * ${project.build.testOutputDirectory}
 * ${project.build.directory}
 * 
 * 
 * 
 * @author charlie collins
 */
public abstract class AbstractAndroidMojo extends AbstractMojo {

    public static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
    
    // GLOBAL PROPS
    /** 
     * Project instance, used to add new source directory to the build. 
     * @parameter default-value="${project}" 
     * @required 
     * @readonly 
     */ 
    private MavenProject project;
    /** 
     * project-helper instance, used to make addition of resources 
     * simpler. 
     * @component 
     */ 
    private MavenProjectHelper helper;
    /**
     * @parameter expression="${pom.sourceDirectory}"
     */
    private File srcDir;
    /**
     * @parameter expression="${project.build.directory}"
     */
    private File buildDir;
    
    
    /**
     * @parameter expression="${android.home}"
     */
    private File androidHome;
   

    // TOOLS
    /**
     * TODO - per platform version?
     * 
     * @parameter expression="${android.home}/tools/aapt"
     */
    private File aaptTool;
    /**
     * TODO - per platform version?
     * 
     * @parameter expression="${android.home}/tools/aidl"
     */
    private File aidlTool;
    /**
     * TODO - per platform version?
     * 
     * @parameter expression="${android.home}/tools/dx"
     */
    private File dxTool;
    /**
     * @parameter expression="${android.home}/android.jar"
     */
    private File androidJar;

    // TOOL OPTIONS
    // TODO

    // INPUT/OUTPUT ASSETS
    /**
     * @parameter expression="${project.build.directory}/res"
     */
    private File resDir;
    /**
     * @parameter expression="${project.build.directory}/assets"
     */
    private File assetDir;    
    /**
     * @parameter expression="${project.build.directory}/classes.dex"
     */
    private File dexFile;
    /**
     * @parameter expression="${project.build.directory}/${pom.artifactId}.apk"
     */
    private File apkArtifact;

    // EMULATOR OPTIONS
    /**
     * Delete all data on user-data disk image before starting.
     */
    private boolean wipeData;

    /** Creates a new instance of AbstractAndroidMojo */
    public AbstractAndroidMojo() {
    }    

    public File getAndroidHome() {
        return androidHome;
    }

    public void setAndroidHome(File androidHome) {
        this.androidHome = androidHome;
    }

    public File getAaptTool() {
        return aaptTool;
    }

    public void setAaptTool(File aaptTool) {
        this.aaptTool = aaptTool;
    }

    public File getAidlTool() {
        return aidlTool;
    }

    public void setAidlTool(File aidlTool) {
        this.aidlTool = aidlTool;
    }

    public File getDxTool() {
        return dxTool;
    }

    public void setDxTool(File dxTool) {
        this.dxTool = dxTool;
    }

    public File getAndroidJar() {
        return androidJar;
    }

    public void setAndroidJar(File androidJar) {
        this.androidJar = androidJar;
    }

    public File getResDir() {
        return resDir;
    }

    public void setResDir(File resDir) {
        this.resDir = resDir;
    }

    public File getAssetDir() {
        return assetDir;
    }

    public void setAssetDir(File assetDir) {
        this.assetDir = assetDir;
    }

    public File getDexFile() {
        return dexFile;
    }

    public void setDexFile(File dexFile) {
        this.dexFile = dexFile;
    }

    public File getApkArtifact() {
        return apkArtifact;
    }

    public void setApkArtifact(File apkArtifact) {
        this.apkArtifact = apkArtifact;
    }

    public boolean isWipeData() {
        return wipeData;
    }

    public void setWipeData(boolean wipeData) {
        this.wipeData = wipeData;
    }

    public static String getOS_NAME() {
        return OS_NAME;
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public MavenProjectHelper getHelper() {
        return helper;
    }

    public void setHelper(MavenProjectHelper helper) {
        this.helper = helper;
    }

    public File getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public File getBuildDir() {
        return buildDir;
    }

    public void setBuildDir(File buildDir) {
        this.buildDir = buildDir;
    }

}
