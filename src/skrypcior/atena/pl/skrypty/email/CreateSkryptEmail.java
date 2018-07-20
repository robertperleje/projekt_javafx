/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import java.sql.SQLException;
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

                SendEmail.sendMail(email, subject, body);
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
