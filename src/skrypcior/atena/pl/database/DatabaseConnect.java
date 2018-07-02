
package skrypcior.atena.pl.database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class DatabaseConnect {
    
    private static DatabaseConnect connect=null;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gtu_dro";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    public DatabaseConnect() {
        createConnection();
    }
    
    public static DatabaseConnect getInstance(){
        if (connect==null) {
            connect = new DatabaseConnect();
        }
        return connect;
     }
    
    void createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(DB_URL,"dupa","");
            } catch (Exception e){
                e.printStackTrace();
            }
    }
    
    
    public ResultSet execQuery(String query){
        ResultSet result;
        try {
            stmt = (Statement) conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (Exception ex) {
            System.out.println("Błąd" + ex.getLocalizedMessage());
            return null;
        } finally{
            
        }
        return result;
    }
    
    public boolean execAction(String qu) {
        try {
            stmt = (Statement) conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Błąd" + ex.getMessage(),"Błąd ",JOptionPane.ERROR_MESSAGE);
            System.out.println("Błąd" +ex.getLocalizedMessage());
            return false;                    
        } finally {
        }
    }
    
    
    
    public PreparedStatement getInstance(String qu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}
