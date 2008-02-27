package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Write scripts to execute Android tool commands on OS X and Linux.
 * 
 * @author ccollins
 */
public class ScriptHandlerUnix extends AbstractScriptHandler {

    private File sh;
    
    public ScriptHandlerUnix() {
        super();
        sh = new File("/bin/bash");
        if (!sh.exists()) {
            sh = new File("/usr/bin/bash");
        }
        if (!sh.exists()) {
            sh = new File("/bin/sh");
        }
    }

    @SuppressWarnings("static-access")
    public File writeLogCatScript(AbstractAndroidMojo mojo) throws IOException
    {
        String filename = "logcat.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());         
        writer.print(mojo.getAdbTool() + " logcat " + mojo.getLogTags());     
        writer.println(); 
        writer.flush();
        writer.close();
        return file;
    }
    
    @SuppressWarnings("static-access")
    public File writeEmulStartScript(AbstractAndroidMojo mojo) throws IOException
    {
        String filename = "startemul.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());     
        
        writer.println("result=`" + mojo.getAdbTool() + " get-serialno`");       
        writer.println("if [[ $result != \"unknown\" ]]");
        writer.println("  then");
        writer.println("    echo \"emulator already running - using existing instance (first ID)\"");
        writer.println("else");
        writer.println("    echo \"starting emulator\"");
        writer.print("    " + mojo.getEmulTool().getAbsolutePath()); 
        if (!mojo.getEmulSkin().equalsIgnoreCase("none")) {
            writer.print(" -skin " + mojo.getEmulSkin());
        }
        else {
            writer.print(" -noskin");
        }
        writer.print(" -netdelay " + mojo.getEmulNetDelay());
        writer.print(" -netspeed " + mojo.getEmulNetSpeed());        
        if (mojo.isEmulWipeData()) {
            writer.print(" -wipe-data");
        }  
        if (mojo.isEmulVerbose()) {
            writer.print(" -verbose");
        }  
        writer.print(" &");        
        writer.println(); 
        writer.println("fi");        
        writer.flush();
        writer.close();
        return file;
    }
    
    @SuppressWarnings("static-access")
    public File writeInstallApkScript(AbstractAndroidMojo mojo) throws IOException {
        String filename = "installapk.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());  
        
        writer.print(mojo.getAdbTool().getAbsolutePath());       
        writer.print(" wait-for-device install");       
        writer.print(" " + mojo.getApkArtifactName());
        writer.println();

        writer.flush();
        writer.close();
        return file;
    }
    
    @SuppressWarnings("static-access")
    public File writePackageResScript(AbstractAndroidMojo mojo) throws IOException {
        String filename = "packageres.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());

        writer.print(mojo.getAaptTool().getAbsolutePath());
        writer.print(" package");
        writer.print(" -f -c");
        writer.print(" -M " + mojo.getResourcesDir().getAbsolutePath() + File.separator + "AndroidManifest.xml");
        writer.print(" -S " + mojo.getResDir().getAbsolutePath());
        writer.print(" -A " + mojo.getAssetDir().getAbsolutePath());
        writer.print(" -I " + mojo.getAndroidHome() + File.separator + "android.jar");
        writer.print(" " + mojo.getApkArtifactName());
        
        writer.println();

        writer.flush();
        writer.close();
        return file;
    }

    @SuppressWarnings("static-access")
    public File writeDexScript(AbstractAndroidMojo mojo) throws IOException {
        String filename = "dex.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());

        writer.print(mojo.getDxTool().getAbsolutePath());
        writer.print(" -Jmx384m");
        writer.print(" --dex");
        writer.print(" --output=" + mojo.getDexFile().getAbsolutePath());
        writer.print(" --locals=full");
        writer.print(" --positions=lines");
        writer.print(" " + mojo.getBuildDir().getAbsolutePath() + File.separator + "classes");
        writer.println();

        writer.flush();
        writer.close();
        return file;
    }

    @SuppressWarnings("static-access")
    public File writeRScript(AbstractAndroidMojo mojo) throws IOException {
        String filename = "genr.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());

        writer.print(mojo.getAaptTool().getAbsolutePath());
        writer.print(" compile");
        writer.print(" -m");
        writer.print(" -J " + mojo.getSrcDir());
        writer.print(" -M " + mojo.getResourcesDir().getAbsolutePath() + File.separator + "AndroidManifest.xml");
        writer.print(" -S " + mojo.getResDir().getAbsolutePath());
        writer.print(" -A " + mojo.getAssetDir().getAbsolutePath());
        writer.print(" -I " + mojo.getAndroidHome() + File.separator + "android.jar");
        writer.println();

        writer.flush();
        writer.close();
        return file;
    }
    
    @SuppressWarnings("static-access")
    public File writeAidlScript(AbstractAndroidMojo mojo) throws IOException {
        String filename = "aidl.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        
        writer.println("#!" + sh.getAbsolutePath());

        writer.println("for file in $( find " + mojo.getSrcDir() + " -type f -name *.aidl )");
        writer.println("  do");
        writer.println("   " + mojo.getAidlTool() + " $file");
        writer.println("  done");
        writer.println();
        
        writer.flush();
        writer.close();
        return file;
    }

}
