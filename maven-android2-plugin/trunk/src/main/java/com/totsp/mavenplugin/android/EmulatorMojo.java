
package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.Executor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;



/**
 * @goal emul
 * @requiresDependencyResolution compile
 * @description Runs the Android emulator
 * //@execute phase=package
 * 
 * @author charlie collins
 */
public class EmulatorMojo extends AbstractAndroidMojo {
    
    private static final String EMULATOR_COMMAND = 
            ANDROID_PATH + File.separator + "tools" + File.separator + "emulator";
    
    private Executor exec;
    
    public EmulatorMojo() {
        super();
        exec =  new DefaultExecutor();
        //exec.setWorkingDirectory(new File("."));
        
        //Map env = new HashMap();
        //env.put("TEST_ENV_VAR", "XYZ");
        //exitValue = exec.execute(cl, env);
    }
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        
       // use commons-exec to launch emulator
        CommandLine cl = new CommandLine(EMULATOR_COMMAND);
        
        try
        {
        exec.execute(cl, new ExecuteResultHandler() {
            public void onProcessComplete(int exitValue)
            {
                System.out.println("exitValue of process - " + exitValue);
            }
            public void onProcessFailed(ExecuteException e)
            {
                System.out.println(e.getMessage());
            }
            
        });
        
        }
        catch (ExecuteException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
   
    
    
    
}