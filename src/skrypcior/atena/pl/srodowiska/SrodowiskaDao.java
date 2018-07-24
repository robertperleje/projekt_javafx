/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.srodowiska;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import skrypcior.atena.pl.database2.DbConnect;

/**
 *
 * @author perlro1
 */
public class SrodowiskaDao
{
    Connection conn = DbConnect.createConnection();
    
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
            Logger.getLogger(SrodowiskaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}