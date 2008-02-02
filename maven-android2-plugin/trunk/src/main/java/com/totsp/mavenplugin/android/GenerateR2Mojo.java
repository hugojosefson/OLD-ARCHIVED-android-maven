package com.totsp.mavenplugin.android;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Run the android aapt tool to generate the R resource file.
 * 
 * Modifiers:
   -d  one or more device assets to include, separated by commas
   -c  compile resources from assets
   -f  force overwrite of existing files
   -g  only write generated assets; do not package source assets
   -j  specify a jar or zip file containing classes to include
   -l  one or more locale assets to include, separated by commas
   -m  make package directories under location specified by -J
   -s  sync existing packages (update + remove deleted files)
   -u  update existing packages (add new, replace older)
   -v  verbose output
   -x  create extending (non-application) resource IDs
   -z  require localization of resource attributes marked with localization="suggested"
   -A  additional directory in which to find raw asset files
   -I  add an existing package to base include set
   -J  specify where to output R.java resource constant definitions
   -M  specify full path to AndroidManifest.xml to include in zip
   -P  specify where to output public resource definitions
   -R  specify directory containing generated resource files (in or output)
   -S  additional directory in which to find resource source assets
   -0  don't compress files we're adding
 * 
 * 
 * @goal genr
 * @requiresDependencyResolution compile
 * @description Generates the Android R.java resource file
 * 
 * @author charlie collins
 */
public class GenerateRMojo extends AbstractAndroidMojo {

  
    public GenerateRMojo() {
        super();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        // aapt compile -m -j [SRC] -M AndroidManifest.xml -S [RES_DIR] -I
        // android.jar

        // TODO debug logging in validator
        System.out.println("srcDir - " + this.getSrcDir());
        System.out.println("resDir - " + this.getResDir());
        System.out.println("android.home - " + this.getAndroidHome());
        System.out.println("android jar - " + this.getAndroidJar());
        System.out.println("aaptTool - " + this.getAaptTool());

        if (this.getAaptTool() == null || this.getSrcDir() == null || this.getResDir() == null
                || this.getAndroidJar() == null) {
            // TODO have validator handle all the validation?
            throw new MojoFailureException("VALIDATION/ENV PROBLEM - abort");
        }

        if( !this.getOutput().exists() ){
            this.getOutput().mkdirs();
        }
        
        if( System.getProperty("os.name").toLowerCase(Locale.US).startsWith("windows") ){
            
            this.getLog().info("os = WINDOWS");
            
            /*
            ScriptWriterWindows writer = new ScriptWriterWindows();
            try{               
                File exec = writer.writeRScript(this);
                ProcessWatcher    pw = new ProcessWatcher("\""+exec.getAbsolutePath()+"\"");
                pw.startProcess(System.out, System.err);
                int retVal = pw.waitFor();
                if( retVal != 0 ){
                    throw new MojoFailureException("Compilation failed.");
                }
            } catch(Exception e){
                throw new MojoExecutionException("Exception attempting compile.", e);
            }
            */
        } else {
            
            this.getLog().info("os = UNIX (based, Linux/OS X)");
            
            ScriptWriterUnix writer = new ScriptWriterUnix();
            try{
                File exec = writer.writeRScript(this);
                /*
                ProcessWatcher pw = new ProcessWatcher(exec.getAbsolutePath().replaceAll(" ", "\\ "));
                pw.startProcess(System.out, System.err);
                int retVal = pw.waitFor();
                if( retVal != 0 ){
                    throw new MojoFailureException("Compilation failed.");
                }
                */
            } catch(Exception e){
                throw new MojoExecutionException("Exception attempting compile.", e);
            }
        }
   
    
        
        
        /*
        try {
            command = this.getAaptTool().getCanonicalPath();
            command += " compile -m -j " + this.getSrcDir().getCanonicalPath() + " -M "
                    + this.getResourcesDir().getCanonicalPath() + "/AndroidManifest.xml -S "
                    + this.getResDir().getCanonicalPath() + " -I " + this.getAndroidJar().getCanonicalPath();
            this.getLog().warn("command = \"" + command + "\"");
        } catch (IOException e) {
            // TODO handle this better
            System.out.println(e.getStackTrace());
        }

        if (command != null) {
            // use commons-exec to launch emulator
            CommandLine cl = new CommandLine(command);

            try {
                exec.execute(cl);

               

            } catch (ExecuteException e) {
                throw new MojoFailureException(e.getLocalizedMessage());
            } catch (IOException e) {
                throw new MojoFailureException(e.getLocalizedMessage());
            }
        } else {
            throw new MojoFailureException("ERROR, command null, nothing to execute");
        }
        */

    }
    
   

}
