/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.wgranie;

import java.sql.SQLException;
import skrypcior.atena.pl.skrypt.SkryptyDao;

/**
 *
 * @author perlro1
 */
public class PobierzSrodSchemat
{
    public static String pobierzSrod(Integer id) throws SQLException{
        
        SkryptyDao skryptyDao = new SkryptyDao();
        
        String srodowisko = skryptyDao.pobierzSrod(id);
        return srodowisko;
    }
    
    public static String pobierzSchemat(Integer id) throws SQLException{
        
        SkryptyDao skryptyDao = new SkryptyDao();
        
        String schemat = skryptyDao.pobierzWartoscKolumny(id, "schemat");
        return schemat;
    }
}
