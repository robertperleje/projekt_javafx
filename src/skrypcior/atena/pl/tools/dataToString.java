/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author perlro1
 */
public class dataToString {
    
    
    public static String dataZMysln(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }
    
    public static String dataBezMysln(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }
    
}
