/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import java.sql.SQLException;
import skrypcior.atena.pl.skrypt.SkryptyDao;



/**
 *
 * @author perlro1
 */
public class SubjectEmail
{
    public String subjectMail(String modul,Integer idrekord ) throws SQLException{
        
        SkryptyDao dao2 = new SkryptyDao();
        String folder = dao2.pobierzWartoscKolumny(idrekord,"folder");
        String srodowisko = dao2.pobierzSrod(idrekord);
        
        folder = folder.substring(0,folder.length()-1);
        String subject = modul + " " + folder + " na środowisko " + srodowisko;
        return  subject;
       
    }
}
