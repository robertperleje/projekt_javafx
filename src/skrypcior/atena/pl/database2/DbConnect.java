/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.database2;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author perlro1
 */
public class DbConnect {
    
    static Connection conn = null;
    
    private DbConnect(){
        
    }
//public Connection connection;  

    public static Connection createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             Connection conn = null;
            try {
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/gtu_dro?useUnicode=yes&characterEncoding=UTF-8","dupa","");
            } catch (SQLException ex) {
                Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Połączenie do bazy działa");
            return conn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        
    }
    
}
}
