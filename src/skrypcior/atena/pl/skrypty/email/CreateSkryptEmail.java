package skrypcior.atena.pl.skrypty.email;

import java.sql.SQLException;
import java.util.List;
import javax.mail.MessagingException;
import skrypcior.atena.pl.skrypt.SkryptyDao;
import skrypcior.atena.pl.skrypty.tabela.podsumowanie.SkryptyPodsumowanieTable;



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
        List email_do = null;
        List email_dw = null;
        String adress_emailDw = null;
        //Tu wywołamy w przypadku zmiany rózne przypadki
        switch (status)
        {
            case "Przygotowany":

                //pobieramy liste mailowa - nazwa_kolumny
                email_do = dao.selectRowsPrzyg(true, "Skrypt");
                break;
            case "Wysłany":
                //pobieramy liste mailowa
                email_do = dao.selectRowsDo("Skrypt");
                email_dw = dao.selectRowsDw("Skrypt");
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
        String adress_emailDo = String.join(";", email_do);
        if (email_dw != null)
        {
            adress_emailDw = String.join(";", email_dw);
        }
        
        String tytul = subject.subjectMail("skrypt", idrekord);
        //pobieramybody
        String body = BodyMailSkrypt.bodyMailSkrypt(idrekord);
        
        //
        SkryptyPodsumowanieTable podsumowanieTabela = new SkryptyPodsumowanieTable();
        body = body + "<p>&nbsp;</p>" + podsumowanieTabela.createTableHtml();
        SendEmail.sendMail(adress_emailDo, adress_emailDw, tytul, body);
        return null;
    }
}
