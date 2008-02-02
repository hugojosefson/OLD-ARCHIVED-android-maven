/*
 * ScriptWriterUnix.java
 *
 * Created on November 23, 2007, 12:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ccollins
 */
public class ScriptWriterUnix extends AbstractScriptWriter{
    
    private String command;
    
    /** Creates a new instance of ScriptWriterUnix */
    public ScriptWriterUnix() {        
    }
    
    public File writeRScript(AbstractAndroidMojo mojo) throws IOException
    {                
        String filename = "genr.sh";
        File file = new File(mojo.getBuildDir(), filename);
        PrintWriter writer = new PrintWriter( new FileWriter( file ) );
        //Collection<File> classpath = mojo.buildRuntimeClasspathList();
        File sh = new File("/bin/bash");
        if( !sh.exists() ){
            sh = new File("/usr/bin/bash");
        }
        if( !sh.exists() ){
            sh = new File("/bin/sh");
        }
        writer.println("#!"+sh.getAbsolutePath());
        
        /*
        writer.print( "CLASSPATH=");
        for(File f : classpath ){
            writer.print("\""+f.getAbsolutePath()+"\":");
        }
        writer.println();
        writer.println("export CLASSPATH");
        */
        
        /*
        String extra = mojo.getExtraJvmArgs() != null ? mojo.getExtraJvmArgs() : "";
        if (System.getProperty( "os.name" ).toLowerCase( Locale.US ).startsWith("mac")&&
                    extra.indexOf("-XstartOnFirstThread") == -1 ) {
            extra ="-XstartOnFirstThread "+extra;
        }
        */
        
        // aapt compile -m -j [SRC] -M AndroidManifest.xml -S [RES_DIR] -I
        // android.jar
        
        command = mojo.getAaptTool().getCanonicalPath();
        command += " compile -m -j " + mojo.getSrcDir().getCanonicalPath() + " -M "
                + mojo.getResourcesDir().getCanonicalPath() + "/AndroidManifest.xml -S "
                + mojo.getResDir().getCanonicalPath() + " -I " + mojo.getAndroidJar().getCanonicalPath();
        
        mojo.getLog().warn("command = \"" + command + "\"");
        
        writer.print(mojo.getAaptTool().getCanonicalPath());
        
        writer.print(" compile ");
        writer.print(" -m ");
        writer.print(" -j " + mojo.getSrcDir());
        writer.print(" " + mojo.getResourcesDir().getCanonicalPath() + "/AndroidManifest.xml");
        writer.print(" -S " + mojo.getResDir().getCanonicalPath());
        writer.print(" -I " + mojo.getAndroidJar().getCanonicalPath());       
        writer.println();
        writer.flush();
        writer.close();
        try{
            ProcessWatcher pw = new ProcessWatcher("chmod +x "+file.getAbsolutePath() );
            pw.startProcess(System.out, System.err );
            pw.waitFor();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        
        return file;
    }
    
    /*
    @SuppressWarnings("static-access")
    public File writeRunScript(AbstractAndroidMojo mojo) throws IOException,
            DependencyResolutionRequiredException {
        
        String filename = mojo instanceof DebugMojo ? "debug.sh": "run.sh";
        File file = new File(mojo.getBuildDir(), filename );
        PrintWriter writer = new PrintWriter( new FileWriter( file ) );
        Collection<File> classpath = mojo.buildRuntimeClasspathList();
        File sh = new File("/bin/bash");
        if( !sh.exists() ){
            sh = new File("/usr/bin/bash");
        }
        if( !sh.exists() ){
            sh = new File("/bin/sh");
        }
        writer.println("#!"+sh.getAbsolutePath());
        writer.print( "CLASSPATH=");
        for(File f : classpath ){
            writer.print("\""+f.getAbsolutePath()+"\":");
        }
        writer.println();
        writer.println("export CLASSPATH");
        String extra = mojo.getExtraJvmArgs() != null ? mojo.getExtraJvmArgs() : "";
        if (System.getProperty( "os.name" ).toLowerCase( Locale.US ).startsWith("mac")&&
                    extra.indexOf("-XstartOnFirstThread") == -1 ) {
            extra ="-XstartOnFirstThread "+extra;
        }
        writer.print("\""+AbstractGWTMojo.JAVA_COMMAND+"\" "+extra+" -cp $CLASSPATH ");
        if( mojo instanceof DebugMojo ){
            writer.print(" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=");
            writer.print( mojo.getDebugPort() );
            writer.print( mojo.isDebugSuspend() ? ",suspend=y "  : ",suspend=n " );
        }
        writer.print("-Dcatalina.base=\""+mojo.getTomcat().getAbsolutePath()+"\" ");
        writer.print(" com.google.gwt.dev.GWTShell");
        writer.print(" -gen \"");
        writer.print( mojo.getGen().getAbsolutePath() );
        writer.print("\" -logLevel ");
        writer.print(mojo.getLogLevel());
        writer.print(" -style ");
        writer.print(mojo.getStyle());
        writer.print(" -out ");
        writer.print("\""+mojo.getOutput().getAbsolutePath()+"\"");
        writer.print(" -port ");
        writer.print(Integer.toString( mojo.getPort() ));
        
        if( mojo.isNoServer() ){
            writer.print(" -noserver ");
        }
        writer.print( " "+mojo.getRunTarget() );
        writer.println();
        writer.flush();
        writer.close();
        try{
            ProcessWatcher pw = new ProcessWatcher("chmod +x "+file.getAbsolutePath() );
            pw.startProcess(System.out, System.err );
            pw.waitFor();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return file;
    }
    
    @SuppressWarnings("static-access")
    public File writeCompileScript(AbstractGWTMojo mojo) throws IOException,
            DependencyResolutionRequiredException {
        File file = new File(mojo.getBuildDir(), "compile.sh" );
        PrintWriter writer = new PrintWriter( new FileWriter( file ) );
        File sh = new File("/bin/bash");
        if( !sh.exists() ){
            sh = new File("/usr/bin/bash");
        }
        if( !sh.exists() ){
            sh = new File("/bin/sh");
        }
        writer.println("#!"+sh.getAbsolutePath());
        Collection<File> classpath = mojo.buildClasspathList(false);
        writer.print( "CLASSPATH=");
        for(File f : classpath ){
            writer.print("\""+f.getAbsolutePath()+"\":");
        }
        writer.println();
        writer.println("export CLASSPATH");
        for( String target : mojo.getCompileTarget() ){
            String extra = mojo.getExtraJvmArgs() != null ? mojo.getExtraJvmArgs() : "";
            if (System.getProperty( "os.name" ).toLowerCase( Locale.US ).startsWith("mac") &&
                    extra.indexOf("-XstartOnFirstThread") == -1 ) {
                extra ="-XstartOnFirstThread "+extra;
            }
            writer.print("\""+AbstractGWTMojo.JAVA_COMMAND+"\" "+extra+" -cp $CLASSPATH ");
            writer.print(" com.google.gwt.dev.GWTCompiler ");
            writer.print(" -gen ");
            writer.print( mojo.getGen().getAbsolutePath() );
            writer.print(" -logLevel ");
            writer.print(mojo.getLogLevel());
            writer.print(" -style ");
            writer.print(mojo.getStyle());
            writer.print(" -out ");
            writer.print(mojo.getOutput().getAbsolutePath());
            writer.print(" ");
            writer.print(target);
            writer.println();
        }
        writer.flush();
        writer.close();
        try{
            ProcessWatcher pw = new ProcessWatcher("chmod +x "+file.getAbsolutePath() );
            pw.startProcess(System.out, System.err );
            pw.waitFor();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return file;
    }
    */
    
}
