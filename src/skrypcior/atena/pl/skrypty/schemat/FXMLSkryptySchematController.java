/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.schemat;

import skrypcior.atena.pl.skrypty.status.*;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class FXMLSkryptySchematController implements Initializable {
    
    ObservableList<Schemat> list = FXCollections.observableArrayList();
    Connection conn = DbConnect.createConnection();
    
    @FXML
    private JFXTextField tf_nazwa;
    @FXML
    private TableView<Schemat> tabela_schemat;
    @FXML
    private TableColumn<Schemat, Integer> col_id;
    @FXML
    private TableColumn<Schemat, String> col_schemat;
    @FXML
    private TableColumn<Schemat, String> col_data;
    @FXML
    private TableColumn<Schemat, String> col_operator;
    @FXML
    private Button btn_zapisz;
    @FXML
    private Button btn_usun;
    @FXML
    private Button btn_anuluj;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        przypcol();
        zaladuj();
    }    
    
    
    private void przypcol(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_schemat.setCellValueFactory(new PropertyValueFactory<>("schemat"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        
}
    private void zaladuj(){
        try {
            list.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_SCHEMAT");
            while (rs.next()) {
             list.add(new Schemat(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            } catch (SQLException ex) {
            Logger.getLogger(FXMLSkryptySchematController.class.getName()).log(Level.SEVERE, null, ex);
            }
       tabela_schemat.getItems().setAll(list); 
    }
    

    @FXML
    private void usunWiersz(ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        Schemat schemat = (Schemat) tabela_schemat.getSelectionModel().getSelectedItem();
        String qu = "DELETE FROM SKRYPTY_STATUS where schemat = ?";
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, schemat.getSchemat());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLSkryptySchematController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
        
        zaladuj();
    }

    @FXML
    private void zamknijOkno(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void dodajSchemat(ActionEvent event) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        String ozn_schemat = (String) tf_nazwa.getText();
               
       if(ozn_schemat.isEmpty() || ozn_schemat.isEmpty() ){
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }
       
       String skryptOperator = "ROBERT1";
       
       String qu = "INSERT INTO SKRYPTY_SCHEMAT (schemat, data_utw, operator ) VALUES (?,?,?)";

        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            
            preparedStatement.setString(1, ozn_schemat);
//            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(3, skryptOperator);
            
            System.out.println(qu);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLSkryptySchematController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        preparedStatement.execute();
        preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Słowniki - Schemat dopisano");
        }
        zaladuj();
    }
    
}
