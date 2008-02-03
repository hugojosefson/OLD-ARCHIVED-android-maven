package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author ccollins
 */
public class ScriptHandlerWindows extends AbstractScriptHandler {

    public ScriptHandlerWindows() {
        super();
    }

    @SuppressWarnings("static-access")
    public File writeInstallApkScript(AbstractAndroidMojo mojo) throws IOException {
        String filename = "installapk.cmd";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        

        writer.print(mojo.getEmulTool().getAbsolutePath() + "&");
        writer.println();
        
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
        String filename = "packageres.cmd";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        

        writer.print(mojo.getAaptTool().getAbsolutePath());
        writer.print(" package");
        writer.print(" -f -c");
        writer.print(" -M " + mojo.getResourcesDir().getAbsolutePath() + "/AndroidManifest.xml");
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
        String filename = "dex.cmd";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));        

        writer.print(mojo.getDxTool().getAbsolutePath());
        writer.print(" -Jmx383m");
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
        String filename = "genr.cmd";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));
       
        writer.print(mojo.getAaptTool().getAbsolutePath());
        writer.print(" compile");
        writer.print(" -m");
        writer.print(" -J " + mojo.getBuildDir());
        writer.print(" -M " + mojo.getResourcesDir().getAbsolutePath() + "/AndroidManifest.xml");
        writer.print(" -S " + mojo.getResDir().getAbsolutePath());
        writer.print(" -A " + mojo.getAssetDir().getAbsolutePath());
        writer.print(" -I " + mojo.getAndroidHome() + File.separator + "android.jar");
        writer.println();

        writer.flush();
        writer.close();
        return file;
    }

}