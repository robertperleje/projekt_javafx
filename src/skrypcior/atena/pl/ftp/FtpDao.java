/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.ftp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
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
public class FtpDao
{

    Connection conn = DbConnect.createConnection();
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public void insertRows(String host, Integer port, String login, String pass, String modul, String sciezka) throws SQLException
    {

        String qu = "INSERT INTO ftp (host, port, login, password, modul, sciezka, data_utw, operator ) VALUES (?,?,?,?,?,?,?,?)";
        String skryptOperator = "perlro1";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setString(1, host);
            preparedStatement.setInt(2, port);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, pass);
            preparedStatement.setString(5, modul);
            preparedStatement.setString(6, sciezka);
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, skryptOperator);

            System.out.println(qu);

        } catch (SQLException ex)
        {
            Logger.getLogger(FtpDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            preparedStatement.execute();
            preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Dodano do słownika");
        }
    }

    public String selectFtp(String modul, String kolumn)
    {

        try
        {
            String qu = ("SELECT " + kolumn + " FROM ftp WHERE modul = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, modul);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getString(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FtpDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public Boolean deleteRows(Integer id)
    {
        String qu = "DELETE FROM ftp WHERE id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(FtpDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

}
