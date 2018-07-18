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
    public static String subjectMail(String modul,String nazwa, String folder, String srod){
        
        String tyt = modul + folder + "na środowisko " + srod;
        return  tyt;
       
    }
}
