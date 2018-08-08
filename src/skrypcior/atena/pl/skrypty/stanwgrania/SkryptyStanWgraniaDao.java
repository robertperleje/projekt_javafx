/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.stanwgrania;

import com.mysql.jdbc.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    public void insertRows(Integer id, String srodowisko, String status, String wynik) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String skryptOperator = "ROBERT1";

        String qu = "INSERT INTO skrypty_stan_wgrania (skrypt_id, srodowisko, status, data_wdr, wynik, data, operator ) VALUES (?,?,?,?,?,?,?)";

        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, srodowisko);
            preparedStatement.setString(3, status);
            preparedStatement.setNull(4, java.sql.Types.NULL);
            preparedStatement.setString(5, wynik);
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, skryptOperator);

            System.out.println(qu);

        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyStanWgraniaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            preparedStatement.execute();
            preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Wynik zapisano");
        }
    }
    
    public Integer selectCzyUruchomiony(Integer skrypt_id, String srodowisko ) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM skrypty_stan_wgrania WHERE skrypt_id = ? and srodowisko = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, skrypt_id);
            preparedStatement.setString(2, srodowisko);
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
        return 0;
    }
    
     public void updateRowsDate(Integer id, String srodowisko, String data_wdr) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String skryptOperator = "ROBERT1";

        String qu = "UPDATE skrypty_stan_wgrania SET data_wdr =  ? WHERE skrypt_id = ? AND srodowisko = ?";

        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setString(1, data_wdr);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, srodowisko);

            System.out.println(qu);

        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyStanWgraniaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            preparedStatement.execute();
            preparedStatement.close();
        }
    }

}
