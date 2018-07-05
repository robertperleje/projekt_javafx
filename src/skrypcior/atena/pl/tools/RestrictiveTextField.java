/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.print.attribute.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



public class RestrictiveTextField extends PlainDocument {

    public static boolean dataLenght (TextField inputTextField, Label inputLabel, String validationText, String reqiredLenght){
      boolean isDataLenght = true;
      String validationString = null;
      
        if (!inputTextField.getText().matches("\\b\\w' - '{" + reqiredLenght + "}" + "\\b")) {
            isDataLenght = false;
            validationString = validationText;            
        }
        inputLabel.setText(validationString);
        inputLabel.setTextFill(Color.RED);
        return isDataLenght;      
    }
    
    
}