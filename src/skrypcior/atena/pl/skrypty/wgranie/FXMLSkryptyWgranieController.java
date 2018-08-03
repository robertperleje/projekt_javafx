/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.wgranie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.mail.MessagingException;
import skrypcior.atena.pl.konta.KontaDao;
import skrypcior.atena.pl.skrypt.SkryptyDao;
import skrypcior.atena.pl.skrypty.email.SendEmail;
import skrypcior.atena.pl.skrypty.stanwgrania.SkryptyStanWgraniaDao;
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
    @FXML
    private Label label_srodowisko;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    public void setText(String wynik, String id, String srodowisko)
    {
        this.text_wynik.setText(wynik);
        this.label_id.setText(id);
        this.label_srodowisko.setText(srodowisko);
        System.err.println(id);
    }

    @FXML
    private void wyslijMail(ActionEvent event) throws SQLException, IOException
    {
        int id = Integer.parseInt(label_id.getText());
        String tresc = text_wynik.getText();
        //nazwa skryuptu
        String nazwaSkryptu = skryptyDao.pobierzWartoscKolumny(id, "nazwa");
        //id uzytkownika odpowiedzialnego
        String idDevOdp = skryptyDao.pobierzWartoscKolumny(id, "opodp");
        //jego email
        List konto_email = kontaDao.selectEmail(Integer.parseInt(idDevOdp));
        String adress_emailDo = String.join(";", konto_email);
        String adress_emailDw = null;
        try
        {
            //wyslanie maila
            SendEmail.sendMail(adress_emailDo, adress_emailDw, nazwaSkryptu, tresc);
            showInfoAlertBox.showInformationAlertBox("Mail wysłany");
        } catch (MessagingException ex)
        {
            Logger.getLogger(FXMLSkryptyWgranieController.class.getName()).log(Level.SEVERE, null, ex);
            showInfoAlertBox.showInformationAlertBox("Błąd wysłania maila");
        }

    }

    @FXML
    private void bladWykonania(ActionEvent event) throws SQLException, IOException
    {
        int id = Integer.parseInt(label_id.getText());
        String tresc = text_wynik.getText().substring(0, 4000);
        String srodowisko = label_srodowisko.getText();
        //zapisz do bazy
        SkryptyStanWgraniaDao stanWgraniaDao = new SkryptyStanWgraniaDao();
        stanWgraniaDao.insertWynik(id, srodowisko ,"Błąd", tresc);
        wyslijMail(event);
    }

    @FXML
    private void zapiszOk(ActionEvent event) throws SQLException
    {

        int id = Integer.parseInt(label_id.getText());
        String tresc = text_wynik.getText();
        if (tresc.indexOf("ORA-") != -1)
        {
            //czy napewno zmieniamy status
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Status wykonania skryptu");
            alert.setHeaderText("Wystąpił błąd. Czy napewno chcesz zapisać jako poprawnie wykonany... ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                //pobieram srodowisko
                String srodowisko = label_srodowisko.getText();
                //zapisz do bazy
                SkryptyStanWgraniaDao stanWgraniaDao = new SkryptyStanWgraniaDao();
                stanWgraniaDao.insertWynik(id, srodowisko ,"OK", tresc.substring(0, 4000));
            } else
            {
                return;
            }
        }

    }

    public byte[] givenWritingStringToFile_whenUsingFileOutputStream_thenCorrect(String tresc, String nazwa)
            throws IOException
    {
        String str = tresc;
        byte[] strToBytes;
        try (FileOutputStream outputStream = new FileOutputStream(nazwa))
        {
            strToBytes = str.getBytes();
            outputStream.write(strToBytes);
        }
        return strToBytes;
    }
}
