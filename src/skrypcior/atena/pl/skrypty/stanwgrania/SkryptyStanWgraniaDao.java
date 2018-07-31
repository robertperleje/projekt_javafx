/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.stanwgrania;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.tools.showInfoAlertBox;

/**
 *
 * @author perlro1
 */
public class SkryptyStanWgraniaDao
{

    Connection conn = DbConnect.createConnection();

    public void insertWynik(Integer id, String srodowisko, String status, String wynik) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String skryptOperator = "ROBERT1";

        String qu = "INSERT INTO skrypty_stan_wgrania (skrypt_id, srodowisko, status, wynik, data, operator ) VALUES (?,?,?,?,?,?)";

        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, srodowisko);
            preparedStatement.setString(3, status);
            preparedStatement.setString(4, wynik);
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, skryptOperator);

            System.out.println(qu);

        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyStanWgraniaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            preparedStatement.execute();
            preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Wynik z wykonania skryptu zapisano");
        }
    }
    
    public Integer selectCzyUruchomiony(Integer skrypt_id ) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM skrypty_stan_wgrania WHERE skrypt_id = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, skrypt_id);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyStanWgraniaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
