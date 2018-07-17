/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
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
public class FXMLSkryptyEmailController implements Initializable
{

    ObservableList<Email> list = FXCollections.observableArrayList();
    Connection conn = DbConnect.createConnection();

    @FXML
    private TableView<Email> tabela_email;
    @FXML
    private TableColumn<Email, Integer> col_id;
    @FXML
    private TableColumn<Email, String> col_email;
    @FXML
    private TableColumn<Email, Boolean> col_adresat;
    @FXML
    private TableColumn<Email, Boolean> col_przygot;
    @FXML
    private TableColumn<Email, String> col_grupa;
    @FXML
    private TableColumn<Email, String> col_data;
    @FXML
    private TableColumn<Email, String> col_operator;

    @FXML
    private JFXTextField tf_email;
    @FXML
    private CheckBox ccb_adresat;
    @FXML
    private ComboBox cmb_grupa;
    ObservableList<String> grupaList = FXCollections.observableArrayList("Skrypt", "Partenon");
    @FXML
    private Label lb_email;
    @FXML
    private Label lb_grupa;
    @FXML
    private CheckBox ccb_przygot;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        //col_adresat.setCellValueFactory(cellData -> cellData.getValue().checkedBoxProperty());
        //col_adresat.setCellFactory(param -> new CheckBoxTableCell<Email, Boolean>());
        //employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().employeeNameProperty());
        cmb_grupa.setItems(grupaList);
        przypcol();
        zaladuj();
    }

    private void przypcol()
    {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_adresat.setCellValueFactory(new PropertyValueFactory<>("adresat"));
        col_przygot.setCellValueFactory(new PropertyValueFactory<>("przygot"));
        /*col_adresat.setCellFactory(param -> new CheckBoxTableCell<Email, Boolean>(){
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null 
                        item.booleanValue() ? "1" : "0");
            }
        });
         */
        col_grupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));

    }

    private void zaladuj()
    {
        try
        {
            list.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_EMAIL");
            while (rs.next())
            {
                list.add(new Email(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getBoolean(4), rs.getString(5), rs.getString(6), rs.getString(7)));
                //list.setId(rs.getInt("id"));

                System.out.println(rs.getBoolean(3));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabela_email.getItems().setAll(list);
    }

    @FXML
    @SuppressWarnings("empty-statement")
    private void dodajWiersz(ActionEvent event) throws SQLException
    {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String email = (String) tf_email.getText();
        Boolean adresat = ccb_adresat.isSelected();
        Boolean przygot = ccb_przygot.isSelected();
        String grupa = (String) cmb_grupa.getSelectionModel().getSelectedItem();

        String skryptOperator = "ROBERT1";

        boolean grupaIsEmpty = RestrictiveTextField.StringIsEmpty(grupa, lb_grupa, "Pole wymagane");
        boolean emailRfc = RestrictiveTextField.emailFormat(tf_email.getText(), lb_email, "Wymagany format imie.nazwisko@atena.pl");

        if (!emailRfc || !grupaIsEmpty)
        {
            return;
        }

        String qu = "INSERT INTO SKRYPTY_EMAIL (email, czy_glowny, przygotowany, grupa, data_utw, operator ) VALUES (?,?,?,?,?,?)";

        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setString(1, email);
            preparedStatement.setBoolean(2, adresat);
            preparedStatement.setBoolean(3, przygot);
            preparedStatement.setString(4, grupa);
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, skryptOperator);

            System.out.println(qu);

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            preparedStatement.execute();
            preparedStatement.close();

        }
        tf_email.setText("");
        lb_email.setText("");
        lb_grupa.setText("");
        ccb_adresat.setSelected(false);
        ccb_przygot.setSelected(false);
        zaladuj();
        showInfoAlertBox.showInformationAlertBox("Email został dopisany do słownika.");

    }

    @FXML
    private void usunWiersz(ActionEvent event) throws SQLException
    {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        Email id = (Email) tabela_email.getSelectionModel().getSelectedItem();

        String qu = "DELETE FROM SKRYPTY_EMAIL where ID = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, id.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLSkryptyEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        zaladuj();
        showInfoAlertBox.showInformationAlertBox("Rekord usunięto");

    }

    @FXML
    private void zamknijOkno(ActionEvent event)
    {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
