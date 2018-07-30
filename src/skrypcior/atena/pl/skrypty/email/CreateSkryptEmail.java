/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import java.io.File;
import static java.nio.file.Files.list;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import javax.mail.MessagingException;
import skrypcior.atena.pl.skrypt.BodyMailSkrypt;
import skrypcior.atena.pl.skrypt.SkryptyDao;

/**
 *
 * @author perlro1
 */
public class CreateSkryptEmail
{

    public static String createSkryptEmail(String status, Integer idrekord ) throws SQLException, MessagingException
    {
        //Tu wywołamy w przypadku zmiany rózne przypadki
        switch (status)
        {
            case "Utworzony":
                //może kiedyś 
                break;
            case "Przygotowany":
                SkryptyEmailDao dao = new SkryptyEmailDao();
                //pobieramy liste mailowa
                String email = dao.selectMail("Przygotowany");
                //pobieramy tytul maila
                String subject = SubjectEmail.subjectMail("skrypt", idrekord);
                //pobieramybody
                String body = BodyMailSkrypt.bodyMailSkrypt(idrekord);
                //
                File file = new File("Skrypt");
                List<File> listZacznik = new ArrayList<>();
                listZacznik.add(file);

                SendEmail.sendMail(email, subject, body, (File) listZacznik);
                //FTPFunctions.uploadFtp();

                break;
            case "Wysłany":
                //
                break;
            case "Wdrożony":
                //
                break;
            case "Błąd":
                //
                break;
            default:
                break;
        }
        return null;
        
    }
}
