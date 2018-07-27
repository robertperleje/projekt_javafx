/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.wgranie;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.dbtools.raptor.newscriptrunner.ScriptExecutor;
import oracle.dbtools.raptor.newscriptrunner.ScriptRunnerContext;
import skrypcior.atena.pl.bazy.BazyDao;
import skrypcior.atena.pl.skrypt.SkryptyDao;


/**
 *
 * @author perlro1
 */
public class SkryptyWgranie
{

    public static String uruchomSkrypt(Integer id, String srodowisko) throws SQLException, UnsupportedEncodingException
    {
        //Pobieramy schemat czyli login z jakiego ma iść skrypt
        String login =  PobierzSrodSchemat.pobierzSchemat(id);
        
        //Na podsatwie loginu i śrdowiska na jakie wfrazamy pobieramy id bazy
        BazyDao bazyDao = new BazyDao();
        Integer id_bazy = bazyDao.pobierzId(srodowisko, login);
        
        //Pobieram url i haslo
        String url = bazyDao.pobierzWartoscKolumny(id_bazy, "url");
        String haslo = bazyDao.pobierzWartoscKolumny(id_bazy, "haslo");
        
        Connection conn = DriverManager.getConnection(url, login, haslo);
        conn.setAutoCommit(false);
        
        // #create sqlcl
        ScriptExecutor sqlcl = new ScriptExecutor(conn);

        // #setup the context
        ScriptRunnerContext ctx = new ScriptRunnerContext();

        // #set the context
        sqlcl.setScriptRunnerContext(ctx);
        ctx.setBaseConnection(conn);

        // Capture the results without this it goes to STDOUT
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        BufferedOutputStream buf = new BufferedOutputStream(bout);
        sqlcl.setOut(buf);

        // # run a whole file 
        // adjust the path as it needs to be absolute
        
        SkryptyDao skryptyDao = new SkryptyDao();
        String skrypt = skryptyDao.pobierzPlik(id);
        sqlcl.setStmt(skrypt);
        sqlcl.run();

        String results = bout.toString("UTF8");
        results = results.replaceAll(" force_print\n", "");
        return  results;
    }
}
