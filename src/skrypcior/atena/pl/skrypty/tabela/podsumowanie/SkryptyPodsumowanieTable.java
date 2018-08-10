package skrypcior.atena.pl.skrypty.tabela.podsumowanie;

import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import skrypcior.atena.pl.database2.DbConnect;
import java.sql.SQLException;
import javax.swing.text.html.HTML;
import skrypcior.atena.pl.skrypt.SkryptyDao;

public class SkryptyPodsumowanieTable
{

    private int columns;
    private final StringBuilder table = new StringBuilder();
    public static String HTML_START = "<html>";
    public static String HTML_END = "</html>";
    public static String TABLE_START_BORDER = "<table border=\"1\">";
    public static String TABLE_START = "<table>";
    public static String TABLE_END = "</table>";
    public static String HEADER_START = "<th>";
    public static String HEADER_END = "</th>";
    public static String ROW_START = "<tr>";
    public static String ROW_END = "</tr>";
    public static String COLUMN_START = "<td>";
    public static String COLUMN_END = "</td>";

    public String createTableHtml() throws SQLException
    {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        StringBuilder tableEmail = new StringBuilder();
        tableEmail.append("<html><body>"
                + "<table style='border:2px solid black'>");

        try
        {

            String qu = ("SELECT sk.id, sk.nazwa, srd.nazwa, sk.datautw, sk.operator, sk.datawysl, sks.nazwa, sk.hurtprzelad, sk.bazytestur, sk.odwersji, sk.folder, sk.jira, k.login, sk.uwagi "
                    + " FROM skrypty sk, skrypty_status sks, srodowisko srd, konta k "
                    + " WHERE sk.Statusid = sks.id and sk.srodowiskoid = srd.id and sk.opodp = k.id order by sk.id asc");
            preparedStatement = (PreparedStatement) DbConnect.createConnection().prepareStatement(qu);
            //System.out.println(qu);
            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                if (rs.getString("sks.nazwa") == "Wdrożony")
                {
                    tableEmail.append("<tr bgcolor=\"#33CC99\">");
                }
                tableEmail.append(COLUMN_START);
                tableEmail.append(rs.getInt("id"));
                tableEmail.append(COLUMN_END);

                tableEmail.append(COLUMN_START);
                tableEmail.append(rs.getString("srd.nazwa"));
                tableEmail.append(COLUMN_END);

                tableEmail.append(COLUMN_START);
                tableEmail.append(rs.getString("sk.datawysl"));
                tableEmail.append(COLUMN_END);

                tableEmail.append(COLUMN_START);
                tableEmail.append(rs.getString("sks.nazwa"));
                tableEmail.append(COLUMN_END);

                tableEmail.append("<tr>");

            }

            tableEmail.append("</table></body></html>");
            System.err.println(tableEmail.toString());
            return tableEmail.toString();

        } catch (SQLException ex)
        {
            Logger.getLogger(SkryptyPodsumowanieTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
