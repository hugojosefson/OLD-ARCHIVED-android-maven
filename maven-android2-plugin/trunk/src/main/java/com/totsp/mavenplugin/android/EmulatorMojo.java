package com.totsp.mavenplugin.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal emulrun
 * @phase package
 * 
 * @requiresDependencyResolution compile
 * @description Runs the Android emulator //@execute phase=package
 * 
 * @author charlie collins
 */
public class EmulatorMojo extends AbstractAndroidMojo {

    public EmulatorMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        if (!isUnix) {
            // WINDOWS
            // TODO windows
            throw new UnsupportedOperationException("Windows not yet supported");
        } else {
            // UNIX
            try {
                ScriptHandlerUnix unixHandler = (ScriptHandlerUnix) handler;

                // first phase, process classes into DX
                File commandFile = unixHandler.writeDexScript(this);
                unixHandler.runScriptUnix(commandFile, this);

                // second phase, process res and assets into APK file
                commandFile = unixHandler.writePackageResScript(this);
                unixHandler.runScriptUnix(commandFile, this);

                // third phase, add classes.dex into APK (archive file)
                PackageMojo.addFilesToExistingZip(new File(this.getApkArtifactName()), new File[] {this.getDexFile()});

            } catch (Exception e) {
                this.getLog().error(e);
                throw new MojoExecutionException(e.getLocalizedMessage());
            }
        }
        
    }

}
