/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.bazy;

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
public class BazyDao
{
    Connection conn = DbConnect.createConnection();
    
    
    public Integer pobierzId(String srodowisko, String schemat) throws SQLException
    {
        Connection conn = DbConnect.createConnection();
        
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT b.id "
                    + "FROM baza b, srodowisko s, skrypty_schemat sch "
                    + "WHERE b.srodowiskoid = s.id AND b.schematid = sch.Id "
                    + "AND s.nazwa = ? and sch.schemat = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, srodowisko);
            preparedStatement.setString(2, schemat);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(BazyDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public String pobierzWartoscKolumny(Integer id, String nazwakol) throws SQLException
    {
        Connection conn = DbConnect.createConnection();
        
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT " + nazwakol + " FROM baza WHERE id = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getString(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(BazyDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
