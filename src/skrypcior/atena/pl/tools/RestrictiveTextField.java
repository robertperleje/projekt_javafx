/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.print.attribute.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



public class RestrictiveTextField extends PlainDocument {

    public static boolean textLenght (String inputTextField, Label inputLabel, String validationText, String reqiredLenght){
      boolean isDataLenght = true;
      String validationString = null;
      
        if (!inputTextField.matches("^[a-z A-Z 0-9_-]{0," + reqiredLenght + "}$")) {
            
            isDataLenght = false;
            validationString = validationText;            
            inputLabel.setText(validationString);
            inputLabel.setTextFill(Color.RED);
            }
        return isDataLenght;
        
    }
    
    public static boolean textAlphabet(TextField inputTextField, Label inputLabel, String validationText){
        boolean isAlphabet = true;
        String validationString = null;
        
        if (!inputTextField.getText().matches("[a-z A-Z]+")) {
            isAlphabet = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isAlphabet;
    }
    
    public static boolean textNumeric(TextField inputTextField, Label inputLabel, String validationText){
        boolean isNumeric = true;
        String validationString = null;
        
        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumeric = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isNumeric;
    }
    
    public static boolean emailFormat(String inputTextField, Label inputLabel, String validationText){
        boolean isEmail = true;
        String validationString = null;
        
        if (!inputTextField.matches("^[0-9a-zA-Z_.-]+@[0-9a-zA-Z.-]+\\.[a-zA-Z]{2,3}$")) {
            isEmail = false;
            validationString = validationText;
            inputLabel.setText(validationString);
            inputLabel.setTextFill(Color.RED);
        }
        return isEmail;
    }
    
    public static boolean StringIsEmpty(String inputTextField, Label inputLabel, String validationText ){
        boolean isStringEmpty = true;
        String validationString = null;
        
        if (inputTextField == null || inputTextField.isEmpty())
        {
            isStringEmpty = false;
            validationString = validationText;
            inputLabel.setText(validationString);
            inputLabel.setTextFill(Color.RED);
        }
        return isStringEmpty;
    }

        
}