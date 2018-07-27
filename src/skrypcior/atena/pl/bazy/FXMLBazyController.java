/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.bazy;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.skrypty.schemat.SkryptySchematDao;
import skrypcior.atena.pl.srodowiska.SrodowiskaDao;
import skrypcior.atena.pl.tools.MD5;
import skrypcior.atena.pl.tools.RestrictiveTextField;
import skrypcior.atena.pl.tools.showInfoAlertBox;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLBazyController implements Initializable
{

    
    Connection conn = DbConnect.createConnection();
    
    @FXML
    private Button btn_zapisz;
    @FXML
    private Button btn_usun;
    @FXML
    private Button btn_anuluj;
    @FXML
    private ComboBox<String> combobox_srodowisko;
    ObservableList<String> srodowiskoList = FXCollections.observableArrayList();
    @FXML
    private Label label_srodowisko;
    @FXML
    private ComboBox<String> combobox_schemat;
    ObservableList<String> schematList = FXCollections.observableArrayList();
   
    @FXML
    private Label label_schemat;
    @FXML
    private JFXTextField textfield_url;
    @FXML
    private JFXTextField textfield_uzytkownik;
    @FXML
    private JFXPasswordField textfield_haslo;
    @FXML
    private Label label_url;
    @FXML
    private Label label_uzytkownik;
    @FXML
    private Label label_haslo;
    @FXML
    private TableView<Bazy> tabela_bazy;
    @FXML
    private TableColumn<Bazy, Integer> col_id;
    @FXML
    private TableColumn<Bazy, String> col_srodowisko;
    @FXML
    private TableColumn<Bazy, String> col_schemat;
    @FXML
    private TableColumn<Bazy, String> col_url;
    @FXML
    private TableColumn<Bazy, String> col_uzytkownik;
    @FXML
    private TableColumn<Bazy, String> col_data;
    @FXML
    private TableColumn<Bazy, String> col_operator;
    
    ObservableList<Bazy> listBaz = FXCollections.observableArrayList();

    SrodowiskaDao srodowiskaDao = new SrodowiskaDao();
    SkryptySchematDao schematDao = new SkryptySchematDao();    

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        wypelnijComboBoxSrodowisko();
        wczytajComboBoxSchemat();
        przypcol();
        zaladujTabele();
    }    
    
    private void przypcol()
    {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_srodowisko.setCellValueFactory(new PropertyValueFactory<>("srodowisko"));
        col_schemat.setCellValueFactory(new PropertyValueFactory<>("schemat"));
        col_url.setCellValueFactory(new PropertyValueFactory<>("url"));
        col_uzytkownik.setCellValueFactory(new PropertyValueFactory<>("uzytkownik"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));

    }

    private void zaladujTabele()
    {
        try
        {
            listBaz.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT b.id, s.nazwa as srodowisko, sch.schemat, b.url, b.uzytkownik, b.data_utw, b.operator "
                    + "FROM baza b,srodowisko s, skrypty_schemat sch "
                    + "WHERE b.srodowiskoid = s.id and b.schematid = sch.id");
            while (rs.next())
            {
                listBaz.add(new Bazy(rs.getInt(1),  rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLBazyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabela_bazy.getItems().setAll(listBaz);
    }

    @FXML
    private void usunWiersz(ActionEvent event)
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        Bazy id = (Bazy) tabela_bazy.getSelectionModel().getSelectedItem();

        String qu = "DELETE FROM baza WHERE id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLBazyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        zaladujTabele();
        showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
    }

    @FXML
    private void zamknijOkno(ActionEvent event)
    {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void dodajWiersz(ActionEvent event) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        
        String srodowiskoNazwa = (String) combobox_srodowisko.getSelectionModel().getSelectedItem();
        String schematNazwa = (String) combobox_schemat.getSelectionModel().getSelectedItem();
        String url = textfield_url.getText();
        String uzytkownik = textfield_uzytkownik.getText();
        String haslo =  textfield_haslo.getText();
        
        //boolean nazwaLenght = RestrictiveTextField.textLenght(tf_nazwa.getText(), lb_nazwa, "Maksymalna ilość znaków 25", "25");       
       
        if(srodowiskoNazwa.isEmpty() || schematNazwa.isEmpty() ){
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }
        
//if (!nazwaLenght ){
        //   return;
       //}
       
       String skryptOperator = "ROBERT1";
       
       String qu = "INSERT INTO baza (srodowiskoid, schematid, url, uzytkownik, haslo, data_utw, operator ) VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            
            preparedStatement.setInt(1, srodowiskaDao.pobierzIdSrod(srodowiskoNazwa));
            preparedStatement.setInt(2, schematDao.pobierzIdSchemat(schematNazwa));
            preparedStatement.setString(3, url);
            preparedStatement.setString(4, uzytkownik);
            preparedStatement.setString(5, haslo);
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, skryptOperator);
            
            System.out.println(qu);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBazyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        preparedStatement.execute();
        preparedStatement.close();
        showInfoAlertBox.showInformationAlertBox("Słowniki - konfiguracja bazy została dopisana");
        }
        
        zaladujTabele();
        wypelnijComboBoxSrodowisko();
        wczytajComboBoxSchemat();
        textfield_url.setText("");
        textfield_uzytkownik.setText("");
        textfield_haslo.setText("");
    }
   
    private void wypelnijComboBoxSrodowisko()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SRODOWISKO");
            while (rs.next())
            {
                srodowiskoList.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLBazyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        combobox_srodowisko.getItems().setAll(srodowiskoList);
    }
    
    private void wczytajComboBoxSchemat()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_SCHEMAT");
            while (rs.next())
            {
                schematList.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLBazyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        combobox_schemat.getItems().setAll(schematList);
    }
}
