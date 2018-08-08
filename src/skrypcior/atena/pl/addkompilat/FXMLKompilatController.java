/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.addkompilat;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLKompilatController implements Initializable
{

    @FXML
    private JFXTextField nazwaKompilat;
    @FXML
    private JFXTextField satelitaKompilat;
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
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> tabDateId;
    @FXML
    private TableColumn<?, ?> tabNazwaKompilat;
    @FXML
    private TableColumn<?, ?> tabSatelita;
    @FXML
    private TableColumn<?, ?> tabDateMain;
    @FXML
    private TableColumn<?, ?> tabDatePrep;
    @FXML
    private TableColumn<?, ?> tabDateRel;
    @FXML
    private TableColumn<?, ?> tabDateFaza3;
    @FXML
    private TableColumn<?, ?> tabDatePrepG;
    @FXML
    private TableColumn<?, ?> tabDateProd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void dodajKompilat(ActionEvent event)
    {
    }

    @FXML
    private void usunWiersz(ActionEvent event)
    {
    }

    @FXML
    private void zamknijOkno(ActionEvent event)
    {
    }
    
}
