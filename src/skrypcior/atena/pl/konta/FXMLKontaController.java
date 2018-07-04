/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.konta;


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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.tools.MD5;
import skrypcior.atena.pl.tools.showInfoAlertBox;


/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLKontaController implements Initializable {
    
    ObservableList<Konta> list = FXCollections.observableArrayList();
    Connection conn = DbConnect.createConnection();
    
    @FXML
    private Button btn_zapisz;
    @FXML
    private Button btn_usun;
    @FXML
    private Button btn_anuluj;
    @FXML
    private JFXTextField tf_login;
    @FXML
    private JFXTextField tf_haslo;
    @FXML
    private JFXTextField tf_imie;
    @FXML
    private JFXTextField tf_nazwisko;
    @FXML
    private JFXTextField tf_email;
    @FXML
    private CheckBox cb_zablokowane;
    @FXML
    private TableView<Konta> tabela_konta;
    @FXML
    private TableColumn<Konta, Integer> col_id;
    @FXML
    private TableColumn<Konta, String> col_login;
    @FXML
    private TableColumn<Konta, String> col_haslo;
    @FXML
    private TableColumn<Konta, String> col_imie;
    @FXML
    private TableColumn<Konta, String> col_nazwisko;
    @FXML
    private TableColumn<Konta, String> col_email;
    @FXML
    private TableColumn<Konta, Boolean> col_zablokowane;
    @FXML
    private TableColumn<Konta, String> col_date;
    @FXML
    private TableColumn<Konta, String> col_operator;
    
    

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
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_haslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));
        col_imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        col_nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_zablokowane.setCellValueFactory(new PropertyValueFactory<>("blokowac"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        
        
}
    private void zaladuj(){
        try {
            list.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM KONTA");
            while (rs.next()) {
             list.add(new Konta(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBoolean(7),rs.getString(8),rs.getString(9),rs.getString(10)));
            }
            } catch (SQLException ex) {
            Logger.getLogger(FXMLKontaController.class.getName()).log(Level.SEVERE, null, ex);
            }
       tabela_konta.getItems().setAll(list); 
    }
    

    @FXML
    private void usunWiersz(ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        Konta konta = (Konta) tabela_konta.getSelectionModel().getSelectedItem();
        String qu = "DELETE FROM KONTA where login = ?";
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, konta.getLogin());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKontaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
        
        zaladuj();
    }

    @FXML
    private void zamknijOkno(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void dodajKonto(ActionEvent event) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        String login = (String) tf_login.getText();
        String haslo = (String) tf_haslo.getText();
        String imie = (String) tf_imie.getText();
        String nazwisko = (String) tf_nazwisko.getText();
        String email = (String) tf_email.getText();
        Boolean zablokowane = (Boolean) cb_zablokowane.isSelected();
        
               
       if(login.isEmpty() || haslo.isEmpty() || imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty() ){
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }
       //hash hasła
       String md5hasło = MD5.PasswordToMD5(haslo);
               
       String skryptOperator = "ROBERT1";
       
       String qu = "INSERT INTO KONTA (login, haslo, imie, nazwisko, email, blokowac, data_utw, data_mod, operator ) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, md5hasło);
            preparedStatement.setString(3, imie);
            preparedStatement.setString(4, nazwisko);
            preparedStatement.setString(5, email);
            preparedStatement.setBoolean(6, zablokowane);
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setNull(8, java.sql.Types.NULL);
            preparedStatement.setString(9, skryptOperator);
            
            System.out.println(qu);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKontaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        preparedStatement.execute();
        preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Słowniki - Schemat dopisano");
        }
        zaladuj();
    }
    
    

    
}
