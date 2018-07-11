/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.addskrypt;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import skrypcior.atena.pl.tools.showInfoAlertBox;

import skrypcior.atena.pl.database2.DbConnect;

/**
 *
 * @author perlro1
 */
public class SkryptyStany           
{
    Connection conn = DbConnect.createConnection();
    
    public void SkryptUpdate(String newStatus, Integer idStatus){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        String qu = "UPDATE SKRYPTY SET STATUS = (SELECT id FROM SKRYPTY_STATUS WHERE NAZWA = ? ) where id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(1, idStatus);
            //System.out.println(skrypt.getNazwa());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Status Zminiono");
        
    }
}