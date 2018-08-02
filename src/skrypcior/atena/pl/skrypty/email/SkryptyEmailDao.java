/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import skrypcior.atena.pl.skrypt.*;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.skrypty.email.FXMLSkryptyEmailController;

/**
 *
 * @author perlro1
 */
public class SkryptyEmailDao
{

    
    /**
     * Pobiera listę mailową w oparciu do kogo ma trafic mail
     * 
     * @param przeznaczenie
     * @return 
     */
    public  String selectMail(String przeznaczenie){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            
            
            switch (przeznaczenie)
            {
                case "glowny":
                    //
                    break;
                case "Przygotowany":
                    String qu = ("SELECT * FROM SKRYPTY_EMAIL WHERE PRZYGOTOWANY = ?");
                    preparedStatement = (PreparedStatement) DbConnect.createConnection().prepareStatement(qu);
                    preparedStatement.setBoolean(1, true);
                    break;
                case "Wysłany":
                    //
                    break;
                default:
                    break;
            }
            
            //System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
