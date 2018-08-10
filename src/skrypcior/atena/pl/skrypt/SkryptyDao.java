/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypt;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import skrypcior.atena.pl.database2.DbConnect;


/**
 *
 * @author perlro1
 */
public class SkryptyDao
{
    
    Connection conn = DbConnect.createConnection();
    
    public Integer pobierzIdOpekuna(String osobaOdp) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM KONTA WHERE login = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, osobaOdp);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
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
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public String pobierzWartoscKolumny(Integer id, String nazwakol) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT " + nazwakol + " FROM SKRYPTY WHERE id = ?");
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
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String pobierzSrod(Integer id) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM SRODOWISKO WHERE id in (SELECT srodowiskoid FROM SKRYPTY WHERE id = ?)");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getString(2);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String pobierzPlik(Integer id) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT plik FROM skrypty WHERE id = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                Blob blob = rs.getBlob(1);
                String str =  new String(blob.getBytes(1l, (int) blob.length()));
                return str;
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Blob pobierzPlikBinary(Integer id) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT plik FROM skrypty WHERE id = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                Blob blob = rs.getBlob(1);
                return blob;
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<String> selectRowsSkrypty()
    {
        List<String> listaadressMail = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {

            String qu = ("SELECT sk.id, sk.nazwa, srd.nazwa, sk.datautw, sk.operator, sk.datawysl, sks.nazwa, sk.hurtprzelad, sk.bazytestur, sk.odwersji, sk.folder, sk.jira, k.login, sk.uwagi "
                    + " FROM skrypty sk, skrypty_status sks, srodowisko srd, konta k "
                    + " WHERE sk.Statusid = sks.id and sk.srodowiskoid = srd.id and sk.opodp = k.id order by sk.id asc");
            preparedStatement = (PreparedStatement) DbConnect.createConnection().prepareStatement(qu);
            //System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                System.out.println(rs.getInt(1));
                listaadressMail.add(rs.getInt(1), rs.getString(2));
            }
            preparedStatement.close();
            return listaadressMail;

        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
