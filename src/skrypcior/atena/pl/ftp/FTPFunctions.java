/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.ftp;


import java.io.FileInputStream;

import java.io.IOException;

import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPFunctions
{

    public static void uploadFtp()
    {
        
        
        String server = "ftp.atena.pl";
        
        int port = 21;
        String user = "at_perlejewski";
        String pass = "Baq6YuGdQg4X";
        String filePath = "d:/KLIENT_PW/Skrypty_do_klienta/2017-07-12_FAZA3/01-PART-N-20170712_PRC_EP_O_RAPORT_GONET.prc";
        String uploadPath = "/home/at_perlejewski/TO_GOTHAER/test/01-PART-N-20170712_PRC_EP_O_RAPORT_GONET.prc";
        
        // get an ftpClient object  
        FTPClient ftpClient = new FTPClient();
        FileInputStream inputStream = null;

        try
        {
            // pass directory path on server to connect  
            ftpClient.connect(server);

            // pass username and password, returned true if authentication is  
            // successful  
            boolean login = ftpClient.login(user, pass);

            if (login)
            {
                System.out.println("Connection established...");
                inputStream = new FileInputStream(filePath);

                boolean uploaded = ftpClient.storeFile(uploadPath,
                        inputStream);
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
