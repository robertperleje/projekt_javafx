/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.konta;

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
public class KontaDao
{
    Connection conn = DbConnect.createConnection();
    
    public String pobierzWartoscKolumnyKonta(Integer id, String nazwakolumny) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT " + nazwakolumny + " FROM konta WHERE id = ?");
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
            Logger.getLogger(KontaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
