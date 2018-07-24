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


/**
 *
 * @author perlro1
 */
public class SkryptyWgranie
{

    public static void uruchomSkrypt(Integer id, String srodowisko) throws SQLException, UnsupportedEncodingException
    {
        //Pobieramy schemat czyli login z jakiego ma iść skrypt
        String login =  PobierzSrodSchemat.pobierzSchemat(id);
        
        //Na podsatwie loginu i śrdowiska na jakie wfrazamy pobieramy dane bazy
        BazyDao bazyDao = new BazyDao();
        Integer id_bazy = bazyDao.pobierzId(srodowisko, login);
        
        
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "klrice", "klrice");
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
        sqlcl.setStmt("@/Users/klrice/workspace_commons/sqlcl-java/myfile.sql");
        sqlcl.run();

        String results = bout.toString("UTF8");
        results = results.replaceAll(" force_print\n", "");
        System.out.println(results);
    }
}
