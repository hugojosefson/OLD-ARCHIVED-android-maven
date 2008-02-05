package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

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
     * project-helper instance, used to make addition of resources simpler.
     * 
     * @component
     */
    private MavenProjectHelper helper;
    /**
     * @parameter expression="${plugin.artifacts}"
     */
    private List pluginArtifacts;
    /**
     * @parameter expression="${component.org.apache.maven.artifact.factory.ArtifactFactory}"
     * @required
     * @readonly
     */
    private ArtifactFactory artifactFactory;
    /**
     * @component role="org.apache.maven.artifact.resolver.ArtifactResolver"
     * @required
     * @readonly
     */
    private ArtifactResolver artifactResolver;
    /**
     * @parameter expression="${localRepository}"
     * @required
     */
    private ArtifactRepository localRepository;
    /**
     * @parameter expression="${project.build.sourceDirectory}"
     */
    private File srcDir;
    /**
     * @parameter expression="${basedir}${file.separator}src${file.separator}main${file.separator}resources"
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
     * @parameter expression="${android.home}${file.separator}tools${file.separator}aapt"
     */
    private File aaptTool;
    /**
     * @parameter expression="${android.home}${file.separator}tools${file.separator}aidl"
     */
    private File aidlTool;
    /**
     * @parameter expression="${android.home}${file.separator}tools${file.separator}dx"
     */
    private File dxTool;
    /**
     * @parameter expression="${android.home}${file.separator}tools${file.separator}adb"
     */
    private File adbTool;
    /**
     * @parameter expression="${android.home}${file.separator}tools${file.separator}emulator"
     */
    private File emulTool;

    // TOOL OPTIONS
    /**
     * @parameter default-value="true"
     */
    private boolean logCat;
    /**
     * @parameter default-value="DEBUG"
     */
    private String logTags;
    /**
     * @parameter default-value="false"
     */
    private boolean emulWipeData;
    /**
     * @parameter default-value="none"
     */
    private String emulNetDelay;
    /**
     * @parameter default-value="full"
     */
    private String emulNetSpeed;
    /**
     * @parameter default-value="QVGA-P"
     */
    private String emulSkin;

    // INPUT/OUTPUT ASSETS
    /**
     * @parameter expression="${basedir}${file.separator}src${file.separator}main${file.separator}resources${file.separator}res"
     */
    private File resDir;
    /**
     * @parameter expression="${basedir}${file.separator}src${file.separator}main${file.separator}resources${file.separator}assets"
     */
    private File assetDir;
    /**
     * @parameter expression="${project.build.directory}${file.separator}classes.dex"
     */
    private File dexFile;
    /**
     * @parameter expression="${project.build.directory}${file.separator}${project.artifactId}.apk"
     */
    private String apkArtifactName;

    /** Creates a new instance of AbstractAndroidMojo */
    public AbstractAndroidMojo() {
        super();

        if (OS_NAME.startsWith("windows")) {
            handler = new ScriptHandlerWindows();
        } else {
            isUnix = true;
            handler = new ScriptHandlerUnix();
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (this.getBuildDir() == null || !this.getBuildDir().exists()) {
            this.getBuildDir().mkdirs();
        }
    }

    public AbstractScriptHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractScriptHandler handler) {
        this.handler = handler;
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

    public java.util.List getPluginArtifacts() {
        return pluginArtifacts;
    }

    public void setPluginArtifacts(java.util.List pluginArtifacts) {
        this.pluginArtifacts = pluginArtifacts;
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
        return adbTool;
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

    public ArtifactFactory getArtifactFactory() {
        return artifactFactory;
    }

    public void setArtifactFactory(ArtifactFactory artifactFactory) {
        this.artifactFactory = artifactFactory;
    }

    public ArtifactResolver getArtifactResolver() {
        return artifactResolver;
    }

    public void setArtifactResolver(ArtifactResolver artifactResolver) {
        this.artifactResolver = artifactResolver;
    }

    public ArtifactRepository getLocalRepository() {
        return localRepository;
    }

    public void setLocalRepository(ArtifactRepository localRepository) {
        this.localRepository = localRepository;
    }

    public boolean isLogCat() {
        return logCat;
    }

    public void setLogCat(boolean logCat) {
        this.logCat = logCat;
    }

    public boolean isEmulWipeData() {
        return emulWipeData;
    }

    public void setEmulWipeData(boolean emulWipeData) {
        this.emulWipeData = emulWipeData;
    }

    public String getEmulSkin() {
        return emulSkin;
    }

    public void setEmulSkin(String emulSkin) {
        this.emulSkin = emulSkin;
    }

    public String getEmulNetDelay() {
        return emulNetDelay;
    }

    public void setEmulNetDelay(String emulNetDelay) {
        this.emulNetDelay = emulNetDelay;
    }

    public String getEmulNetSpeed() {
        return emulNetSpeed;
    }

    public void setEmulNetSpeed(String emulNetSpeed) {
        this.emulNetSpeed = emulNetSpeed;
    }

    public String getLogTags() {
        return logTags;
    }

    public void setLogTags(String logTags) {
        this.logTags = logTags;
    }

}
