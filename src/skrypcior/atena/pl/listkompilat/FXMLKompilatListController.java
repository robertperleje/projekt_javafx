/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.listkompilat;

import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import skrypcior.atena.pl.addkompilat.FXMLKompilatController;
import skrypcior.atena.pl.addkompilat.Kompilat;
import skrypcior.atena.pl.database2.DbConnect;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLKompilatListController implements Initializable {

    @FXML
    private TableView<listaKompilat> lista_kompilat;
    @FXML
    private TableColumn<listaKompilat, Integer> col_Id;
    @FXML
    private TableColumn<listaKompilat, String> col_Ozn;
    @FXML
    private TableColumn<listaKompilat, String> col_Sat;
    @FXML
    private TableColumn<listaKompilat, String> col_Main;
    @FXML
    private TableColumn<listaKompilat, String> col_Prep;
    @FXML
    private TableColumn<listaKompilat, String> col_Rel;
    @FXML
    private TableColumn<listaKompilat, String> col_Faza3;
    @FXML
    private TableColumn<listaKompilat, String> col_PrepG;
    @FXML
    private TableColumn<listaKompilat, String> col_Prod;

    ObservableList<listaKompilat> list = FXCollections.observableArrayList();
    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.createConnection();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        przypcol();
        zaladuj();
    }    
    
    private void przypcol(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Ozn.setCellValueFactory(new PropertyValueFactory<>("oznaczenie"));
        col_Sat.setCellValueFactory(new PropertyValueFactory<>("satelita"));
        col_Main.setCellValueFactory(new PropertyValueFactory<>("main"));
        col_Prep.setCellValueFactory(new PropertyValueFactory<>("prep"));
        col_Rel.setCellValueFactory(new PropertyValueFactory<>("rel"));
        col_Faza3.setCellValueFactory(new PropertyValueFactory<>("faza3"));
        col_PrepG.setCellValueFactory(new PropertyValueFactory<>("prepg"));
        col_Prod.setCellValueFactory(new PropertyValueFactory<>("prod"));
}
    private void zaladuj(){
        try {
            //DatabaseConnect databaseConnect = DatabaseConnect.getInstance();
            

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM KOMPILAT");
            
            
            while (rs.next()) {
             list.add(new listaKompilat(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
            } catch (SQLException ex) {
            Logger.getLogger(FXMLKompilatController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       lista_kompilat.getItems().setAll(list); 
    }
    
}
