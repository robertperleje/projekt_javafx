/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author perlro1
 */
public class OknoWyboru
{

    private String selection;

    public Boolean oknoWyboruTakNie(String subject, String text)
    {
        ButtonType yesButtonType = new ButtonType("Tak");
        ButtonType noButtonType = new ButtonType("Nie");

        //czy napewno zmieniamy status
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, selection, yesButtonType, noButtonType);
        alert.setTitle(subject);
        alert.setHeaderText(text);
        alert.showAndWait();
        if (alert.getResult() == yesButtonType)
        {
          return true;
        } else
        {
          return false;
        }
    }

}
