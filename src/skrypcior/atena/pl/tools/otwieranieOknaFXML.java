/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author perlro1
 */
public class otwieranieOknaFXML
{
    public void utworzOkno(String sciezka, String tytul){
        
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource(sciezka));
        try
        {
            Loader.load();
        } catch (IOException ex)
        {
            Logger.getLogger(otwieranieOknaFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kontroler display = Loader.getController();
        
                
        
        Parent p = Loader.getRoot();
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Wynik wgrania skryptu");
        stage.setScene(new Scene(p));
        stage.showAndWait();
    }
}
