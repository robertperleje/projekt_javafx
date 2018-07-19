/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypt;

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
 //public static String UTF8 = "<meta charset=\"utf-8"\" />";
 public static String TABLE_START_BORDER = "<table border=\"1\">";
 public static String TABLE_START = "<table>";
 public static String TABLE_END = "</table>";
 public static String HEADER_START = "<th>";
 public static String HEADER_END = "</th>";
 public static String ROW_START = "<tr>";
 public static String ROW_END = "</tr>";
 public static String COLUMN_START = "<td>";
public static String COLUMN_END = "</td>";
    
    public static String bodyMailSkrypt(){
    BodyMailSkrypt htmBodyMail = new BodyMailSkrypt("To jest", true, 2, 2);
    htmBodyMail.addTableHeader("Środowisko:","PROD");
    htmBodyMail.addRowValues("Schemat/Użytkownik:", "2");
    htmBodyMail.addRowValues("Czas trwania:", "5");
    htmBodyMail.addRowValues("Można uruchomić podczas pracy użytkowników:", "8");
    htmBodyMail.addRowValues("Czy należy przeładować Hurtownię:", "8");
    String table = htmBodyMail.build();
    //System.out.println(table.toString());   
    return table.toString();
    }
    
    public BodyMailSkrypt(String header, boolean border, int rows, int columns) {
  this.columns = columns;
  if (header != null) {
   table.append("<b>");
   table.append(header);
   table.append("</b>");
  }
  table.append(HTML_START);
  //table.append(UTF8);
  table.append(border ? TABLE_START_BORDER : TABLE_START);
  table.append(TABLE_END);
  table.append(HTML_END);
}
    
    public void addTableHeader(String... values) {
  if (values.length != columns) {
   System.out.println("Error column lenth");
  } else {
   int lastIndex = table.lastIndexOf(TABLE_END);
   if (lastIndex > 0) {
    StringBuilder sb = new StringBuilder();
    sb.append(ROW_START);
    for (String value : values) {
     sb.append(HEADER_START);
     sb.append(value);
     sb.append(HEADER_END);
    }
    sb.append(ROW_END);
    table.insert(lastIndex, sb.toString());
   }
  }
}
    
 public void addRowValues(String... values) {
  if (values.length != columns) {
   System.out.println("Error column lenth");
  } else {
   int lastIndex = table.lastIndexOf(ROW_END);
   if (lastIndex > 0) {
    int index = lastIndex + ROW_END.length();
    StringBuilder sb = new StringBuilder();
    sb.append(ROW_START);
    for (String value : values) {
     sb.append(COLUMN_START);
     sb.append(value);
     sb.append(COLUMN_END);
    }
    sb.append(ROW_END);
    table.insert(index, sb.toString());
   }
  }
}   
    
 
 public String build() {
  return table.toString();
}
    
    
}
