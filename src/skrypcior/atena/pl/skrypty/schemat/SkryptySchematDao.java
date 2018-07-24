/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.schemat;

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
public class SkryptySchematDao
{
    Connection conn = DbConnect.createConnection();
    
    public String pobierzSchemat() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM SKRYPTY_SCHEMAT");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            //preparedStatement.setInt(1, id);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptySchematDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Integer pobierzIdSchemat(String nazwa ) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM skrypty_schemat WHERE schemat = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, nazwa);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptySchematDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
