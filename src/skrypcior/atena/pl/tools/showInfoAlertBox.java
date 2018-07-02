/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import javafx.scene.control.Alert;

/**
 *
 * @author perlro1
 */
public class showInfoAlertBox {
    
    public static void showInformationAlertBox(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
    }
    
}
