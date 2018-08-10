package skrypcior.atena.pl.ftp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.sql.Blob;
import java.sql.SQLException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import skrypcior.atena.pl.skrypt.SkryptyDao;
import skrypcior.atena.pl.tools.Encryption_Decryption;


public class FTPFunctions
{
    
    
    public static void uploadFtp(Integer id, String modul) throws SQLException, FileNotFoundException, IOException, Exception
    {
        
        SkryptyDao skryptyDao = new SkryptyDao();
        FtpDao ftpDao = new FtpDao();
        
        String host = ftpDao.selectFtp(modul, "host");
        String port = ftpDao.selectFtp(modul, "port");
        String user = ftpDao.selectFtp(modul, "login");
        String pass = Encryption_Decryption.decrypt(ftpDao.selectFtp(modul, "password"));
        
        //Folder
        String folder = skryptyDao.pobierzWartoscKolumny(id, "folder");
        //Nazwa skryptu
        String nazwa = skryptyDao.pobierzWartoscKolumny(id, "nazwa");
        
        String nowyFolder = "/home/at_perlejewski/TO_GOTHAER/test/" + folder;
        String uploadPath = "/home/at_perlejewski/TO_GOTHAER/test/" + folder + "/" + nazwa;
        
        //Pobieramy plik z bazy i robimy konwersje aby wysłac go na ftp
        Blob fileBlob = skryptyDao.pobierzPlikBinary(id);
        InputStream blobStream = fileBlob.getBinaryStream();
        
        
        // get an ftpClient object  
        FTPClient ftpClient = new FTPClient();
        //FileInputStream inputStream = null;

        try
        {
            // pass directory path on server to connect  
            ftpClient.connect(host);

            // pass username and password, returned true if authentication is  
            // successful  
            boolean login = ftpClient.login(user, pass);

            if (login)
            {
                System.out.println("Connection established...");
               
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                boolean mkdirFolder = ftpClient.makeDirectory(nowyFolder);
                boolean uploaded = ftpClient.storeFile(uploadPath,
                        blobStream);
                
                if (uploaded)
                {
                    System.out.println("File uploaded successfully !");
                } else
                {
                    System.out.println("Error in uploading file !");
                }

                // logout the user, returned true if logout successfully  
                boolean logout = ftpClient.logout();
                if (logout)
                {
                    System.out.println("Connection close...");
                }
            } else
            {
                System.out.println("Connection fail...");
            }

        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                ftpClient.disconnect();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
