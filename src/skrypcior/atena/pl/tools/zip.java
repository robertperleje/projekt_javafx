/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import static javax.management.Query.gt;

/**
 *
 * @author perlro1
 */
public class zip
{

    public static byte[] zipFiles(String addToZip)
            throws IOException
    {

       if (isNull(addToZip) || addToZip.length() == 0) {
            return null;
        }

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final GZIPOutputStream gzipOutput = new GZIPOutputStream(baos)) {
            gzipOutput.write(addToZip.getBytes(UTF_8));
            gzipOutput.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Error while compression!", e);
        }
    }
}
