/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.ftp;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.tools.RestrictiveTextField;
import skrypcior.atena.pl.tools.showInfoAlertBox;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLFtpController implements Initializable
{

    Connection conn = DbConnect.createConnection();
    ObservableList<Ftp> ftplist = FXCollections.observableArrayList();

    @FXML
    private JFXTextField textfield_host, testfield_port, textfield_login, textfield_filepath;
    @FXML
    private JFXPasswordField textfield_password;
    @FXML
    private JFXComboBox<String> combobox_modul;
    ObservableList<String> comboModulList = FXCollections.observableArrayList("Skrypt", "Mediacja", "Kompilat", "Partenon");
    @FXML
    private TableView<Ftp> table_ftp;
    @FXML
    private TableColumn<Ftp, Integer> col_id;
    @FXML
    private TableColumn<Ftp, String> col_host;
    @FXML
    private TableColumn<Ftp, String> col_port;
    @FXML
    private TableColumn<Ftp, String> col_login;
    @FXML
    private TableColumn<Ftp, String> col_modul;
    @FXML
    private TableColumn<Ftp, String> col_filepatch;
    @FXML
    private TableColumn<Ftp, String> col_datautw;
    @FXML
    private TableColumn<Ftp, String> col_operator;

    @FXML
    private Button btn_zapisz, btn_usun, btn_anuluj;
    @FXML
    private Label label_host, label_port, label_login, login_password, label_modul, label_filepath;

    FtpDao ftpDao = new FtpDao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        combobox_modul.setItems(comboModulList);
        przypcol();
        zaladuj();
    }

    private void przypcol()
    {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_host.setCellValueFactory(new PropertyValueFactory<>("host"));
        col_port.setCellValueFactory(new PropertyValueFactory<>("port"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_modul.setCellValueFactory(new PropertyValueFactory<>("modul"));
        col_filepatch.setCellValueFactory(new PropertyValueFactory<>("sciezka"));
        col_datautw.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));

    }

    private void zaladuj()
    {
        try
        {
            ftplist.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT id, host, port, login, modul, sciezka, data_utw, operator FROM ftp");
            while (rs.next())
            {
                ftplist.add(new Ftp(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLFtpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_ftp.getItems().setAll(ftplist);
        
    }

    @FXML
    private void dodajWiersz(ActionEvent event) throws SQLException
    {
        String host = textfield_host.getText();
        Integer port = Integer.parseInt(testfield_port.getText());
        String login = textfield_login.getText();
        String pass = textfield_password.getText();
        String modul = (String) combobox_modul.getSelectionModel().getSelectedItem();
        String filepath = textfield_filepath.getText();

        Boolean hostLenght = RestrictiveTextField.textLenght(host, label_host, "Maksymalna ilość znaków 50", "50");
        //Boolean textNumeric = RestrictiveTextField.textNumeric(testfield_port.getText(), label_port, "Tylko wartości liczbowe");
        Boolean loginLenght = RestrictiveTextField.textLenght(login, label_login, "Maksymalna ilość znaków 25", "25");
        Boolean passLenght = RestrictiveTextField.textLenght(pass, login_password, "Maksymalna ilość znaków 25", "25");
        //Boolean filepathLenght = RestrictiveTextField.textLenght(filepath, label_filepath, "Maksymalna ilość znaków 250", "250");

        if (host.isEmpty() || login.isEmpty() || pass.isEmpty() || modul.isEmpty() || filepath.isEmpty())
        {
            showInfoAlertBox.showInformationAlertBox("Wypełnij wszystkie pola");
            return;
        }

        if (!loginLenght || !passLenght /*|| !filepathLenght  */)
        {
            return;
        }
        ftpDao.insertRows(host, port, login, pass, modul, filepath);

        clearTextField();
        zaladuj();

    }

    @FXML
    private void usunWiersz(ActionEvent event)
    {
        Ftp rows = (Ftp) table_ftp.getSelectionModel().getSelectedItem();
        Boolean wynik = ftpDao.deleteRows(rows.getId());
        if (wynik){
            showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
        }
        zaladuj();
    }

    @FXML
    private void zamknijOkno(ActionEvent event)
    {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    private void clearTextField()
    {
        textfield_host.setText("");
        testfield_port.setText("");
        textfield_login.setText("");
        textfield_password.setText("");
        combobox_modul.setPromptText("Wbierz moduł");
        textfield_filepath.setText("");
    }
}
