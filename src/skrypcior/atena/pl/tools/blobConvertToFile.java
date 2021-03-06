/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author perlro1
 */
public class blobConvertToFile {
    
    private static final String UPLOAD_FOLDER = "C:\\temp\\";

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;

        try {

            File file = new File("C:\\temp\\testing1.txt");
            byte[] bFile = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);

            //save bytes[] into a file
            writeBytesToFile(bFile, UPLOAD_FOLDER + "test1.txt");
            writeBytesToFileClassic(bFile, UPLOAD_FOLDER + "test2.txt");
            writeBytesToFileNio(bFile, UPLOAD_FOLDER + "test3.txt");

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //Classic, < JDK7
    private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {

        FileOutputStream fileOuputStream = null;

        try {
            fileOuputStream = new FileOutputStream(fileDest);
            fileOuputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //Since JDK 7 - try resources
    private static void writeBytesToFile(byte[] bFile, String fileDest) {

        try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
            fileOuputStream.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Since JDK 7, NIO
    private static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

