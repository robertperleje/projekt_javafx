/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;



/**
 *
 * @author perlro1
 */
public class SubjectEmail
{
    public static String subjectMail(String modul,String folder, String srod){
        
        folder = folder.substring(0,folder.length()-1);
        String subject = modul + " " + folder + " na środowisko " + srod;
        return  subject;
       
    }
}
