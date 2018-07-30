/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import static javax.management.Query.gt;

/**
 *
 * @author perlro1
 */
public class zip
{

    public static File zipFiles(String zipFileName, List<File> addToZip) 
         throws IOException 
    {

        String zipPath = System.getProperty("java.io.tmpdir") 
         + File.separator + zipFileName;
    new File(zipPath).delete(); //delete if zip file already exist
 
    try (FileOutputStream fos = new FileOutputStream(zipPath);
         ZipOutputStream zos = new ZipOutputStream(
              new BufferedOutputStream(fos))) {
        zos.setLevel(9); //level of compression
 
        for (File file : addToZip) {
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    for (int c = fis.read(); c != -1; c = fis.read()) {
                        zos.write(c);
                    }
                    zos.flush();
                }
            }
        }
    }
    File zip = new File(zipPath);
    if (!zip.exists()) {
        throw new FileNotFoundException("The created zip file could not be found");
    }
    return zip;
}
}
