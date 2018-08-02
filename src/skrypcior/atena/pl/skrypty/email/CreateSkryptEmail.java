package skrypcior.atena.pl.skrypty.email;


import java.sql.SQLException;
import javax.mail.MessagingException;
import skrypcior.atena.pl.skrypt.BodyMailSkrypt;


/**
 *
 * @author perlro1
 */
public class CreateSkryptEmail
{
    
    
    public static String createSkryptEmail(String status, Integer idrekord) throws SQLException, MessagingException
    {
        SkryptyEmailDao dao = new SkryptyEmailDao();
        SubjectEmail subject = new SubjectEmail();
        String email = null;
        //Tu wywołamy w przypadku zmiany rózne przypadki
        switch (status)
        {
            case "Utworzony":
                //może kiedyś 
                break;
            case "Przygotowany":

                //pobieramy liste mailowa
                email = dao.selectMail("Przygotowany");
                break;
            case "Wysłany":
                //pobieramy liste mailowa
                email = dao.selectMail("Wysłany");
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
                //pobieramy tytul maila
        
        String tytul = subject.subjectMail("skrypt", idrekord);
        //pobieramybody
        String body = BodyMailSkrypt.bodyMailSkrypt(idrekord);
        SendEmail.sendMail(email, tytul, body);
        return null;
    }
}
