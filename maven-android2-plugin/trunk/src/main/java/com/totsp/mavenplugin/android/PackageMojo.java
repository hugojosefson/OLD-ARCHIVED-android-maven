package com.totsp.mavenplugin.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Three phases: run the android dx tool to create classes.dex from classes, run
 * the android appt tool to package resources and assets into an APK file, run
 * zip to add the dex files into the APK file.
 * 
 * @goal package
 * @phase process-classes
 * @requiresDependencyResolution compile
 * @description Package android resources into APK package.
 * 
 * @author charlie collins
 */
public class PackageMojo extends AbstractAndroidMojo {

    private AbstractScriptHandler handler;
    private boolean isUnix;

    public PackageMojo() {
        super();

        if (System.getProperty("os.name").toLowerCase(Locale.US).startsWith("windows")) {
            this.getLog().info("os = WINDOWS");
            // handler = new ScriptHandlerWindows();
        } else {
            this.getLog().info("os = UNIX (variant)");
            isUnix = true;
            handler = new ScriptHandlerUnix();
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {

        if (!this.getOutput().exists()) {
            this.getLog().info("creating output - " + this.getOutput().getAbsolutePath());
            this.getOutput().mkdirs();
        }

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

    // adapted from http://snippets.dzone.com/posts/show/3468
    public static void addFilesToExistingZip(File zipFile, File[] files) throws IOException {
        // get a temp file
        File tempFile = File.createTempFile(zipFile.getName(), null);
        // delete it, otherwise you cannot rename your existing zip to it.
        tempFile.delete();

        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            throw new RuntimeException("could not rename the file " + zipFile.getAbsolutePath() + " to "
                    + tempFile.getAbsolutePath());
        }
        byte[] buf = new byte[1024];

        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        ZipEntry entry = zin.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(name));
                // Transfer bytes from the ZIP file to the output file
                int len;
                while ((len = zin.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            entry = zin.getNextEntry();
        }
        // Close the streams
        zin.close();
        // Compress the files
        for (int i = 0; i < files.length; i++) {
            InputStream in = new FileInputStream(files[i]);
            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(files[i].getName()));
            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // Complete the entry
            out.closeEntry();
            in.close();
        }
        // Complete the ZIP file
        out.close();
        tempFile.delete();
    }

}
