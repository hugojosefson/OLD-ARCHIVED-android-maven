/*
 * ScriptWriterWindows.java
 *
 * Created on November 23, 2007, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import org.apache.maven.artifact.DependencyResolutionRequiredException;

/**
 *
 * @author ccollins
 */
public class ScriptWriterWindows {
    
    /** Creates a new instance of ScriptWriterWindows */
    public ScriptWriterWindows() {
    }
    
    /*
    public File writeRunScript(AbstractGWTMojo mojo) throws IOException,
            DependencyResolutionRequiredException {
        String filename = mojo instanceof DebugMojo ? "debug.cmd": "run.cmd";
        File file = new File(mojo.getBuildDir(), filename );
        PrintWriter writer = new PrintWriter( new FileWriter( file ) );
        Collection<File> classpath = mojo.buildRuntimeClasspathList();
        writer.print( "set CLASSPATH=");
        for(File f : classpath ){
            writer.print("\""+f.getAbsolutePath()+"\";");
        }
        writer.println();
        String extra = mojo.getExtraJvmArgs() != null ? mojo.getExtraJvmArgs() : "";
        writer.print("\""+mojo.JAVA_COMMAND+"\" "+extra+" -cp %CLASSPATH% ");
        if( mojo instanceof DebugMojo ){
            writer.print(" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=");
            writer.print( mojo.getPort() );
            writer.print(",suspend=y " );
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
        return file;
    }
    
    public File writeCompileScript(AbstractGWTMojo mojo) throws IOException,
            DependencyResolutionRequiredException {
        File file = new File(mojo.getBuildDir(), "compile.cmd" );
        PrintWriter writer = new PrintWriter( new FileWriter( file ) );
        Collection<File> classpath = mojo.buildClasspathList(false);
        writer.print( "set CLASSPATH=");
        for(File f : classpath ){
            writer.print("\""+f.getAbsolutePath()+"\";");
        }
        writer.println();
        for( String target : mojo.getCompileTarget() ){
            String extra = mojo.getExtraJvmArgs() != null ? mojo.getExtraJvmArgs() : "";
            writer.print("\""+mojo.JAVA_COMMAND+"\" "+extra+" -cp %CLASSPATH% ");
            writer.print(" com.google.gwt.dev.GWTCompiler ");
            writer.print(" -gen \"");
            writer.print( mojo.getGen().getAbsolutePath() );
            writer.print("\" -logLevel ");
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
        return file;
    }
    
    */
    
}
