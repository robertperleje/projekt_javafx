/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.wgranie;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLSkryptyWgranieController implements Initializable
{

    @FXML
    private TextArea text_wynik;
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
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    public void setText(String wynik){
        this.text_wynik.setText(wynik);
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
