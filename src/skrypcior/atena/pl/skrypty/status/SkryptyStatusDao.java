/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.status;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import skrypcior.atena.pl.database2.DbConnect;

/**
 *
 * @author perlro1
 */
public class SkryptyStatusDao
{
    Connection conn = DbConnect.createConnection();
    
    public void SkryptUpdate(String newStatus, Integer idSkrypt ) throws MessagingException, SQLException
    {
        
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String qu = "UPDATE SKRYPTY SET statusid = (SELECT id FROM SKRYPTY_STATUS WHERE NAZWA = ? ) where id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, idSkrypt);
            System.out.println(qu);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyStatusDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       }
}
