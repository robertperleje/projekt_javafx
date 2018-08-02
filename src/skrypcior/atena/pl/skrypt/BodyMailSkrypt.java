/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypt;

import java.sql.SQLException;
import javax.swing.text.html.HTML;

/**
 *
 * @author perlro1
 */
public class BodyMailSkrypt
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

    public static String bodyMailSkrypt(Integer idrekord) throws SQLException
    {

        SkryptyDao dao = new SkryptyDao();
        
        String srodowisko = dao.pobierzSrod(idrekord);
        
        BodyMailSkrypt htmBodyMail = new BodyMailSkrypt(true, 2, 2, srodowisko);
        htmBodyMail.addTableHeader("", "");
        htmBodyMail.addRowValues("<strong>Środowisko:</strong>", srodowisko);
        htmBodyMail.addRowValues("<strong>Schemat/Użytkownik:</strong>", dao.pobierzWartoscKolumny(idrekord, "schemat"));
        htmBodyMail.addRowValues("<strong>Czas trwania</strong>:", dao.pobierzWartoscKolumny(idrekord, "czaswykonywania"));
        htmBodyMail.addRowValues("<strong>Czy wymany restart serwera WAS:</strong>", dao.pobierzWartoscKolumny(idrekord, "zatrzymac_serwer"));
        htmBodyMail.addRowValues("<strong>Czy należy przeładować Hurtownię:</strong>",  dao.pobierzWartoscKolumny(idrekord, "hurtprzelad"));
        htmBodyMail.addRowValues("<strong>Można wgrać na bazy testowe:</strong>",  dao.pobierzWartoscKolumny(idrekord, "bazytestur"));
        htmBodyMail.addRowValues("<strong>Realizowany cel:</strong>",  "<em>" + dao.pobierzWartoscKolumny(idrekord, "opis") + "</em>");
        htmBodyMail.addRowValues("<strong>Kolejność uruchamiania:</strong>",  dao.pobierzWartoscKolumny(idrekord, "nazwa"));
        htmBodyMail.addRowValues("<strong>Uwagi:</strong>",  dao.pobierzWartoscKolumny(idrekord, "uwagi"));
        htmBodyMail.addRowValues("<strong>W razie pytań/problemów kontakt do <br /> pracownika Ateny:</strong>",  "607 110 278 - Robert Perlejewski");
        
        String table = htmBodyMail.build();
        System.out.println(table.toString());
        return table.toString();
    }

    public BodyMailSkrypt(boolean border, int rows, int columns, String srodowisko) throws SQLException
    {
        this.columns = columns;

        table.append("<p>");
        table.append("Witam");
        table.append("</p>");

        table.append("<p>Proszę o uruchomienie poniższego skryptu na środowisku " + srodowisko + " skrypt do zgłoszenia PARTWEBGTU-7648.</p>");
        /*
    table.append("<p>&nbsp;</p>");
    table.append("<p>Skrypt znajduje się na zasobie FTP:</p>");
    table.append("<p>serwer: <a href=\"ftp://ftp.atena.pl\"><strong>ftp.atena.pl</strong></a></p>");
    table.append("<p>katalog: <strong>FROM_BUM/Skrypty/2018-07-18_PROD/</strong></p>");
         */

        table.append(HTML_START);
        table.append(border ? TABLE_START_BORDER : TABLE_START);
        table.append(TABLE_END);
        table.append(HTML_END);
    }

    public void addTableHeader(String... values)
    {
        if (values.length != columns)
        {
            System.out.println("Error column lenth");
        } else
        {
            int lastIndex = table.lastIndexOf(TABLE_END);
            //Na razie nie wykorzystujemy nagłowków w tabeli ale obsługa może się przydać
            //if (lastIndex > 0) {
            StringBuilder sb = new StringBuilder();

            sb.append(ROW_START);
            //for (String value : values) {
            //sb.append(HEADER_START);
            //sb.append(value);
            //sb.append(HEADER_END);
            //}
            sb.append(ROW_END);
            table.insert(lastIndex, sb.toString());

            //}
        }
    }

    public void addRowValues(String... values)
    {
        if (values.length != columns)
        {
            System.out.println("Error column lenth");
        } else
        {
            int lastIndex = table.lastIndexOf(ROW_END);
            if (lastIndex > 0)
            {
                int index = lastIndex + ROW_END.length();
                StringBuilder sb = new StringBuilder();
                sb.append(ROW_START);
                for (String value : values)
                {
                    sb.append(COLUMN_START);
                    sb.append(value);
                    sb.append(COLUMN_END);
                }
                sb.append(ROW_END);
                table.insert(index, sb.toString());
            }
        }
    }

    public String build()
    {
        return table.toString();
    }

    
}
