/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.addkompilat;


import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.tools.showInfoAlertBox;


/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLKompilatController implements Initializable {
    
    ObservableList<Kompilat> list = FXCollections.observableArrayList();
    DbConnect dbConnect = new DbConnect();
    Connection conn = dbConnect.createConnection();
    
    @FXML
    private JFXTextField nazwaKompilat;
    @FXML
    private JFXTextField satelitaKompilat;
    @FXML
    private TableView<Kompilat> tableView;
    @FXML
    private DatePicker dateMain;
    @FXML
    private DatePicker datePrep;
    @FXML
    private DatePicker dateRel;
    @FXML
    private DatePicker dateFaza3;
    @FXML
    private DatePicker datePrepG;
    @FXML
    private DatePicker dateProd;
    @FXML
    private TableColumn<Kompilat, String> tabNazwaKompilat;
    @FXML
    private TableColumn<Kompilat, String> tabSatelita;
    @FXML
    private TableColumn<Kompilat, String> tabDateMain;
    @FXML
    private TableColumn<Kompilat, String> tabDatePrep;
    @FXML
    private TableColumn<Kompilat, String> tabDateRel;
    @FXML
    private TableColumn<Kompilat, String> tabDateFaza3;
    @FXML
    private TableColumn<Kompilat, String> tabDatePrepG; 
    @FXML
    private TableColumn<Kompilat, String> tabDateProd;
    @FXML
    private TableColumn<Kompilat, Integer> tabDateId;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        przypcol();
        zaladuj();
    }    

    private void przypcol(){
        tabDateId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabNazwaKompilat.setCellValueFactory(new PropertyValueFactory<>("kompilat"));
        tabSatelita.setCellValueFactory(new PropertyValueFactory<>("satelita"));
        tabDateMain.setCellValueFactory(new PropertyValueFactory<>("main"));
        tabDatePrep.setCellValueFactory(new PropertyValueFactory<>("prep"));
        tabDateRel.setCellValueFactory(new PropertyValueFactory<>("rel"));
        tabDateFaza3.setCellValueFactory(new PropertyValueFactory<>("faza3"));
        tabDatePrepG.setCellValueFactory(new PropertyValueFactory<>("prepg"));
        tabDateProd.setCellValueFactory(new PropertyValueFactory<>("prod"));
}
    private void zaladuj(){
        try {
            //DatabaseConnect databaseConnect = DatabaseConnect.getInstance();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM KOMPILAT");
            while (rs.next()) {
             list.add(new Kompilat(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
            } catch (SQLException ex) {
            Logger.getLogger(FXMLKompilatController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       tableView.getItems().setAll(list); 
    }
    
    
    @FXML
    private void dodajKompilat(ActionEvent event) throws SQLException {
          
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        String nKompilat = (String) nazwaKompilat.getText();
        String satelita = (String) satelitaKompilat.getText();
        String dateWmain = dateMain.getValue().toString();
        String dateWprep = datePrep.getValue().toString();
        String dateWrel = dateRel.getValue().toString();
        String dateWfaza3 = dateFaza3.getValue().toString();
        String dateWprepg = datePrepG.getValue().toString();
        String dateWprod = dateProd.getValue().toString();
       
       if(nKompilat.isEmpty() || satelita.isEmpty() ){
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }
       
       //String qu = "INSERT INTO kompilat (nazwa, satelita, data_main /*, data_prep, data_rel, data_faza3_g, data_prep_g, data_prod_g) VALUES (?,?,?,?,?,?,?,?)";
       String qu = "INSERT INTO kompilat (kompilat, satelita, main, prep, rel, faza3, prepg, prod ) VALUES (?,?,?,?,?,?,?,?)";

       
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            
            preparedStatement.setString(1, nKompilat);
            preparedStatement.setString(2, satelita);
            preparedStatement.setString(3, dateWmain );
            preparedStatement.setString(4, dateWprep );
            preparedStatement.setString(5, dateWrel );
            preparedStatement.setString(6, dateWfaza3 );
            preparedStatement.setString(7, dateWprepg );
            preparedStatement.setString(8, dateWprod );
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKompilatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        preparedStatement.execute();
        preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Kompilat dopisano");
        }
        tableView.getItems().clear();
         zaladuj();   
    }

    @FXML
    private void zamknijOkno(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

        
}
