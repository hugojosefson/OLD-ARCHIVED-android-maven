package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * Properties for android-maven.
 * 
 * 
 * @author charlie collins
 */
public abstract class AbstractAndroidMojo extends AbstractMojo {

    public static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
    protected AbstractScriptHandler handler;
    protected boolean isUnix;

    // GLOBAL PROPS
    /**
     * Project instance, used to add new source directory to the build.
     * 
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;
    /**
     * @parameter expression="${project.build.sourceDirectory}"
     */
    private File srcDir;
    /**
     * @parameter expression="${basedir}/src/main/resources"
     */
    private File resourcesDir;
    /**
     * @parameter expression="${project.build.directory}"
     */
    private File buildDir;

    // PROJECT PROPS
    /**
     * @parameter expression="${android.home}"
     */
    private File androidHome;

    // TOOLS
    /**
     * @parameter expression="${android.home}/tools/aapt"
     */
    private File aaptTool;
    /**
     * @parameter expression="${android.home}/tools/aidl"
     */
    private File aidlTool;
    /**
     * @parameter expression="${android.home}/tools/dx"
     */
    private File dxTool;
    /**
     * @parameter expression="${android.home}/tools/adb"
     */
    private File adbTool;
    /**
     * @parameter expression="${android.home}/tools/emulator"
     */
    private File emulTool;

    // INPUT/OUTPUT ASSETS
    /**
     * @parameter expression="${basedir}/src/main/resources/res"
     */
    private File resDir;
    /**
     * @parameter expression="${basedir}/src/main/resources/assets"
     */
    private File assetDir;
    /**
     * @parameter expression="${project.build.directory}/classes.dex"
     */
    private File dexFile;
    /**
     * @parameter expression="${project.build.directory}/${project.artifactId}.apk"
     */
    private String apkArtifactName;

    /** Creates a new instance of AbstractAndroidMojo */
    public AbstractAndroidMojo() {
        super();

        if (OS_NAME.startsWith("windows")) {
            this.getLog().info("os = WINDOWS");
            // handler = new ScriptHandlerWindows();
        } else {
            this.getLog().info("os = UNIX (variant)");
            isUnix = true;
            handler = new ScriptHandlerUnix();
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (this.getBuildDir() == null || !this.getBuildDir().exists()) {
            this.getBuildDir().mkdirs();
        }
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public File getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public File getBuildDir() {
        return buildDir;
    }

    public void setBuildDir(File buildDir) {
        this.buildDir = buildDir;
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

    public File getAdbTool() {
        return this.adbTool;
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

    public String getApkArtifactName() {
        return apkArtifactName;
    }

    public void setApkArtifactName(String apkArtifactName) {
        this.apkArtifactName = apkArtifactName;
    }

    public void setAdbTool(File adbTool) {
        this.adbTool = adbTool;
    }

    public File getEmulTool() {
        return emulTool;
    }

    public void setEmulTool(File emulTool) {
        this.emulTool = emulTool;
    }

}
