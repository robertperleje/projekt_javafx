/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypt;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.skrypty.email.SendEmail;
import skrypcior.atena.pl.skrypty.email.SkryptyEmailDao;
import skrypcior.atena.pl.skrypty.email.SubjectEmail;
import skrypcior.atena.pl.tools.showInfoAlertBox;

/**
 *
 * @author perlro1
 */
public class SkryptyDao
{
    
    Connection conn = DbConnect.createConnection();
    
    public Integer pobierzIdOpekuna(String osobaOdp) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM KONTA WHERE login = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, osobaOdp);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Integer pobierzIdSrod(String oznSrod) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM SRODOWISKO WHERE NAZWA = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, oznSrod);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void SkryptUpdate(String newStatus, Integer idStatus, String folder, String jira, String srod, String bazytestowe, String uwaga, String nazwa) throws MessagingException
    {
        
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String qu = "UPDATE SKRYPTY SET STATUS = (SELECT id FROM SKRYPTY_STATUS WHERE NAZWA = ? ) where id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, idStatus);
            System.out.println(qu);

            //tu musimy wysłac maila
            //Tu wywołamy w przypadku zmiany rózne przypadki
            switch (newStatus)
            {
                case "Utworzony":
                    //może kiedyś 
                    break;
                case "Przygotowany":
                    SkryptyEmailDao dao = new SkryptyEmailDao();
                    //pobieramy liste mailowa
                    String email = dao.selectMail("Przygotowany");
                    //pobieramy tytul maila
                    String subject = SubjectEmail.subjectMail("skrypt",nazwa,folder,srod);
                    
                    SendEmail.sendMail(email, subject, "Test2",nazwa,folder, srod);
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

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Status Zmieniono");
        

    }
}
