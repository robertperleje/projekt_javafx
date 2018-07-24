/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.menu;

import com.jfoenix.effects.JFXDepthManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.nashorn.internal.objects.Global;
import skrypcior.atena.pl.database.DatabaseConnect;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLMenuController implements Initializable
{
 
    
    private HBox skrypt_info;
    private TextField skryptNazwaInput;
    private Text skryptNazwa;
    private Text skryptStatus;
    private Text skryptId;
    private Text skryptSrodowisko;
    private Text skryptDataUtw;
    private Text skryptDataWysl;
    private Text skryptPrzelad;
    private Text skryptFolder;
    private Text skryptJira;
    private Text skryptOdpowiedzialny;

    DatabaseConnect databaseConnect;
    @FXML
    private StackPane rootPane;
    @FXML
    private BorderPane BorderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //JFXDepthManager.setDepth(skrypt_info, 1);
        databaseConnect = DatabaseConnect.getInstance(); 
    }

    @FXML
    private void loadAddSkrypt(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/skrypt/FXMLDocument.fxml", "Dodanie Skryptu");
        //setCenter("/skrypcior/atena/pl/skrypt/FXMLDocument.fxml");
    }

    
    public void setCenter(String fxmlPath){
        FXMLLoader loader = new FXMLLoader (this.getClass().getResource(fxmlPath));
        //ResourceBundle bundle = ResourceBundle.getBundle("Dupa");
        //loader.setResources(bundle);
        Parent parent = null;
        try
        {
            parent = loader.load();
        } catch (Exception e)
        {
        }
        BorderPane.setCenter(parent);
    }
    
    public void loadWindows(String loc, String title)
    {

        try
        {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadSkryptInfo(ActionEvent event)
    {
        wyczyscInfoOSkrypt();
        String nazwa = skryptNazwaInput.getText();
        String qu = "SELECT * FROM SKRYPTY WHERE nazwa = '" + nazwa + "'";
        ResultSet rs = databaseConnect.execQuery(qu);
        boolean flag = false;

        try
        {
            while (rs.next())
            {
                skryptNazwa.setText(rs.getString("nazwa"));
                skryptStatus.setText(rs.getString("status"));
                skryptId.setText(rs.getString("id"));
                skryptSrodowisko.setText(rs.getString("srodowisko"));
                skryptDataUtw.setText(rs.getString("data_utw"));
                String datawysl = rs.getString("data_wysl");
                if (datawysl == null)
                {
                    skryptDataWysl.setText("Nie Wysłany");
                } else
                {
                    skryptDataWysl.setText(rs.getString("data_wysl"));
                }
                skryptPrzelad.setText(rs.getString("przeladowanie"));
                skryptFolder.setText(rs.getString("folder"));
                skryptJira.setText(rs.getString("jira"));
                skryptOdpowiedzialny.setText(rs.getString("odpowiedzialny"));
                /*
                 Blob blob = rs.getBlob("plik");
                 InputStream inputStream = blob.getBinaryStream();
                 FileOutputStream fileOutputStream = new FileOutputStream("C:\\test.txt");
                 byte[] buffer = new byte[256];
                 int bytesRead = 0;
                 while ((bytesRead = inputStream.read(buffer))!=-1) {
                    fileOutputStream.write(buffer,0,bytesRead);
                    //fileOutputStream.close();
                    //inputStream.close();
                }
                 */
                flag = true;
            }
            if (!flag)
            {
                skryptNazwa.setText("Brak skryptu o takiej nazwie");
                wyczyscInfoOSkrypt();
            }

        } catch (Exception ex)
        {
            Logger.getLogger(FXMLMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void wyczyscInfoOSkrypt()
    {
        skryptStatus.setText("");
        skryptId.setText("");
        skryptSrodowisko.setText("");
        skryptDataUtw.setText("");
        skryptDataWysl.setText("");
        skryptPrzelad.setText("");
        skryptFolder.setText("");
        skryptJira.setText("");
        skryptOdpowiedzialny.setText("");
    }

    @FXML
    private void menuZamknij(ActionEvent event)
    {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void menuDodanieSkryptu(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/addskrypt/FXMLDocument.fxml", "Dodanie Skryptu");
    }

    @FXML
    private void menuDodanieKompilatu(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/addkompilat/FXMLKompilat.fxml", "Dodanie Kompilatu");
    }

    private void menuWczytajTabeleSkryptow(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/listskrypt/FXMLSkryptList.fxml", "Lista Skryptów");
    }

    @FXML
    private void dodajKompilat(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/addkompilat/FXMLKompilat.fxml", "Dodanie Kompilatu");
    }

    @FXML
    private void dodajMediacje(ActionEvent event)
    {
    }

    @FXML
    private void dodajPW(ActionEvent event)
    {
    }

    @FXML
    private void menuStatusSkryptow(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/skrypty/status/FXMLSkryptyStatus.fxml", "Słownik - Status Skryptów");
    }

    @FXML
    private void menuSchematy(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/skrypty/schemat/FXMLSkryptySchemat.fxml", "Słownik - Schematy");
    }

    @FXML
    private void menuKonta(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/konta/FXMLKonta.fxml", "Słownik - Konta Użytkowników");
    }

    @FXML
    private void menuSrodowiska(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/srodowiska/FXMLSrodowisko.fxml", "Słownik - Środowiska");

    }

    @FXML
    private void menuEmail(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/skrypty/email/FXMLSkryptyEmail.fxml", "Słownik - Email");
    }

    @FXML
    private void menuBazy(ActionEvent event)
    {
        loadWindows("/skrypcior/atena/pl/bazy/FXMLBazy.fxml", "Słownik - Bazy");
    }

}
