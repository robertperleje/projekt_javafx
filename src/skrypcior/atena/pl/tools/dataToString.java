/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.DatePicker;

/**
 *
 * @author perlro1
 */
public class dataToString {
    
    
    public static String dataZMysln(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(currentDate);
        if (dateString != null && dateString.isEmpty()) {
            return dateString;
            } else {
            return "";
            }
        
    }
    
    public static String dataBezMysln(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(currentDate);
        if (dateString != null && dateString.isEmpty()) {
            return dateString;
            } else {
            return "";
            }
    }

public static String toString(LocalDate date) {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

         if (date != null) {
             return dateFormatter.format(date);
         } else {
             return "";
         }
    
}
}