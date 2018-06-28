/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.nashorn.internal.objects.Global;


/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLMenuController implements Initializable {
    @FXML
    private Text nazwa;
    @FXML
    private Text status;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadAddSkrypt(ActionEvent event) {
        loadWindows("/skrypcior/atena/pl/addskrypt/FXMLDocument.fxml", "Dodanie Skryptu");
    }

    @FXML
    private void loadSkryptTable(ActionEvent event) {
        loadWindows("/skrypcior/atena/pl/listskrypt/FXMLSkryptList.fxml", "Lista Skryptów");
    }    
     
    void loadWindows(String loc, String title){
        
            try {
                Parent parent = FXMLLoader.load(getClass().getResource(loc));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle(title);
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    
    
}
