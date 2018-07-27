/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.wgranie;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.mail.MessagingException;
import skrypcior.atena.pl.konta.KontaDao;
import skrypcior.atena.pl.skrypt.SkryptyDao;
import skrypcior.atena.pl.skrypty.email.SendEmail;
import skrypcior.atena.pl.tools.showInfoAlertBox;

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
    private Button button_mail;
    @FXML
    private Button button_blad;
    @FXML
    private Button button_ok;
    @FXML
    private Label label_id;

    SkryptyDao skryptyDao = new SkryptyDao();
    KontaDao kontaDao = new KontaDao();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
     
        

    }    
    
    public void setText(String wynik, String id){
        this.text_wynik.setText(wynik);
        this.label_id.setText(id);
       System.err.println(id); 
    }

    
    @FXML
    private void wyslijMail(ActionEvent event) throws SQLException          
    {
       int id = Integer.parseInt(label_id.getText());
       String tresc = text_wynik.getText();
       //nazwa skryuptu
       String nazwaSkryptu = skryptyDao.pobierzWartoscKolumny(id, "nazwa");
       //id uzytkownika odpowiedzialnego
       String idDevOdp = skryptyDao.pobierzWartoscKolumny(id, "opodp");
       //jego email
       String konto_email = kontaDao.pobierzWartoscKolumnyKonta(Integer.parseInt(idDevOdp), "email");
       
        try
        {
            //wyslanie maila
            SendEmail.sendMail(konto_email, nazwaSkryptu, tresc);
            showInfoAlertBox.showInformationAlertBox("Mail wysłany");
        } catch (MessagingException ex)
        {
            Logger.getLogger(FXMLSkryptyWgranieController.class.getName()).log(Level.SEVERE, null, ex);
            showInfoAlertBox.showInformationAlertBox("Błąd wysłania maila");
        }
       
    }

    @FXML
    private void bladWykonania(ActionEvent event)
    {
    }

    @FXML
    private void zapiszOk(ActionEvent event)
    {
    }
    
    
}
