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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.tools.dataToString;
import skrypcior.atena.pl.tools.showInfoAlertBox;


/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLKompilatController implements Initializable {
    
    ObservableList<Kompilat> list = FXCollections.observableArrayList();
    Connection conn = DbConnect.createConnection();
    
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
            list.clear();
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
       
        //textField.setText(textField.getText().substring(0, LIMIT));
       
       if(nKompilat.isEmpty() || satelita.isEmpty() ){
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }
       
       String qu = "INSERT INTO kompilat (kompilat, satelita, main, prep, rel, faza3, prepg, prod ) VALUES (?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            
            preparedStatement.setString(1, nKompilat);
            preparedStatement.setString(2, satelita);
            
            if (dateMain.getValue() == null) {
                preparedStatement.setNull(3, java.sql.Types.NULL);
            } else {                
                preparedStatement.setDate(3,Date.valueOf(dateMain.getValue()));
            }
            //preparedStatement.setDate(3, Objects.nonNull(dateMain) ? Date.valueOf(dateMain.getValue()): preparedStatement.setNull(3, java.sql.Types.DATE));
            
            if (datePrep.getValue() == null) {
                preparedStatement.setNull(4, java.sql.Types.NULL);
            } else {
                preparedStatement.setDate(4,Date.valueOf(datePrep.getValue()));
            }
            
            //preparedStatement.setDate(4, Objects.nonNull(datePrep) ? Date.valueOf(datePrep.getValue()): preparedStatement.setNull(4, java.sql.Types.DATE) );                
            
            if (dateRel.getValue() == null) {                  
                preparedStatement.setNull(5, java.sql.Types.NULL);
            } else {
               preparedStatement.setDate(5,Date.valueOf(dateRel.getValue()));  
            }
            
            //preparedStatement.setDate(5, Objects.nonNull(dateRel) ? Date.valueOf(dateRel.getValue()): preparedStatement.setNull(5, java.sql.Types.DATE));
           
            if (dateFaza3.getValue() ==null) {                
                preparedStatement.setNull(6, java.sql.Types.NULL);
            } else {
                preparedStatement.setDate(6,Date.valueOf(dateFaza3.getValue())); 
            }
            
            //preparedStatement.setDate(6, Objects.nonNull(dateFaza3) ? Date.valueOf(dateFaza3.getValue()): preparedStatement.setNull(6, java.sql.Types.DATE));
            
            if (datePrepG.getValue() ==null) {                
                preparedStatement.setNull(7, java.sql.Types.NULL);                
            } else {
               preparedStatement.setDate(7,Date.valueOf(datePrepG.getValue())); 
            }
            
            //preparedStatement.setDate(7, Objects.nonNull(datePrepG) ? Date.valueOf(datePrepG.getValue()): preparedStatement.setNull(7, java.sql.Types.DATE) );
            
            if (dateProd.getValue() ==null) {                
                preparedStatement.setNull(8, java.sql.Types.NULL);
            } else {
                preparedStatement.setDate(8,Date.valueOf(dateProd.getValue()));
            }
           // preparedStatement.setDate(8, Objects.nonNull(dateProd) ? Date.valueOf(dateProd.getValue()): preparedStatement.setNull(8, java.sql.Types.DATE));
            
            System.out.println(qu);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKompilatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        preparedStatement.execute();
        preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Kompilat dopisano");
        }
        zaladuj();
    }

    @FXML
    private void zamknijOkno(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void usunWiersz(ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        Kompilat kompilat = (Kompilat) tableView.getSelectionModel().getSelectedItem();
        String qu = "DELETE FROM kompilat where kompilat = ?";
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, kompilat.getKompilat());
            System.out.println(kompilat.getKompilat());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKompilatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
        
        zaladuj();
    }

        
}
