/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import skrypcior.atena.pl.skrypt.*;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    List<String> listaadressMail = new ArrayList<>();

    public List<String> selectRowsPrzyg(Boolean przygotowany, String grupa)
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {

            String qu = ("SELECT * FROM skrypty_email WHERE przygotowany = ? and grupa = ?");
            preparedStatement = (PreparedStatement) DbConnect.createConnection().prepareStatement(qu);
            preparedStatement.setBoolean(1, przygotowany);
            preparedStatement.setString(2, grupa);

            //System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                listaadressMail.add(rs.getString(2));
            }
            preparedStatement.close();
            return listaadressMail;

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> selectRowsDo(String grupa)
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {

            String qu = ("SELECT * FROM skrypty_email WHERE czy_glowny = true AND przygotowany = false AND grupa = ?");
            preparedStatement = (PreparedStatement) DbConnect.createConnection().prepareStatement(qu);
            preparedStatement.setString(1, grupa);

            //System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                //return rs.getString(2);
                listaadressMail.add(rs.getString(2));

            }
            preparedStatement.close();
            return listaadressMail;

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> selectRowsDw(String grupa)
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {

            String qu = ("SELECT * FROM skrypty_email WHERE czy_glowny = false AND przygotowany = false AND  grupa = ?");
            preparedStatement = (PreparedStatement) DbConnect.createConnection().prepareStatement(qu);
            preparedStatement.setString(1, grupa);

            //System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                //return rs.getString(2);
                listaadressMail.add(rs.getString(2));

            }
            preparedStatement.close();
            return listaadressMail;

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
