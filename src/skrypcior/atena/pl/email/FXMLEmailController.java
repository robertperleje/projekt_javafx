/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.email;

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
public class FXMLEmailController implements Initializable {
    
    ObservableList<Email> list = FXCollections.observableArrayList();
    Connection conn = DbConnect.createConnection();
    
    @FXML
    private JFXTextField tf_nazwa;
    @FXML
    private TableView<Email> tabela_status;
    @FXML
    private TableColumn<Email, Integer> col_id;
    @FXML
    private TableColumn<Email, String> col_nazwa;
    @FXML
    private TableColumn<Email, String> col_data;
    @FXML
    private TableColumn<Email, String> col_operator;
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
        col_nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        
}
    private void zaladuj(){
        try {
            list.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_STATUS");
            while (rs.next()) {
             list.add(new Email(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            } catch (SQLException ex) {
            Logger.getLogger(FXMLEmailController.class.getName()).log(Level.SEVERE, null, ex);
            }
       tabela_status.getItems().setAll(list); 
    }
    

    @FXML
    private void dodajKompilat(ActionEvent event) throws SQLException {
        
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        String ozn_status = (String) tf_nazwa.getText();
               
       if(ozn_status.isEmpty() || ozn_status.isEmpty() ){
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }
       
       String skryptOperator = "ROBERT1";
       
       String qu = "INSERT INTO SKRYPTY_STATUS (nazwa, data_utw, operator ) VALUES (?,?,?)";

        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            
            preparedStatement.setString(1, ozn_status);
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(3, skryptOperator);
            
            System.out.println(qu);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        preparedStatement.execute();
        preparedStatement.close();
        showInfoAlertBox.showInformationAlertBox("Słowniki - Status skryptu dopisano");
        }
        zaladuj();
    }

    @FXML
    private void usunWiersz(ActionEvent event) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        Email status = (Email) tabela_status.getSelectionModel().getSelectedItem();
        
        String ls = "SELECT count(*) FROM SKRYPTY where status = " + status.getId();
        System.out.println(status.getId());
        preparedStatement = (PreparedStatement) conn.prepareStatement(ls);
        rs = preparedStatement.executeQuery(ls);
        
        System.out.println(rs);
        rs.next();
        int j = rs.getInt(1);
        if (j>0) {
             showInfoAlertBox.showInformationAlertBox("Status jest używany, usunięcie niemożliwe");
        } else {
            
            String qu = "DELETE FROM SKRYPTY_STATUS where nazwa = ?";
                try {
                    preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
                    preparedStatement.setString(1, status.getNazwa());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
            
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLEmailController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
        
                zaladuj();   
        }
        
        
        
    }

    @FXML
    private void zamknijOkno(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
}
