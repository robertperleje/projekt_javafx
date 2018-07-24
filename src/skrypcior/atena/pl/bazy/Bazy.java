/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.bazy;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class Bazy
{
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty srodowiskoid;
    private final SimpleStringProperty schematid;
    private final SimpleStringProperty url;
    private final SimpleStringProperty uzytkownik;
   // private final SimpleStringProperty haslo;
    private final SimpleStringProperty datautw;
    private final SimpleStringProperty operator;

    public Bazy(Integer id, String srodowiskoid, String schematid, String url, String uzytkownik, /*String haslo,*/ String datautw, String operator) {
        this.id = new SimpleIntegerProperty(id);
        this.srodowiskoid = new SimpleStringProperty(srodowiskoid);
        this.schematid = new SimpleStringProperty(schematid);
        this.url = new SimpleStringProperty(url);
        this.uzytkownik = new SimpleStringProperty(uzytkownik);
        //this.haslo = new SimpleStringProperty(haslo);
        this.datautw = new SimpleStringProperty(datautw);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId() {
        return id.get();
    }
    
    public String getSrodowisko() {
        return srodowiskoid.get();
    }
    
    public String getSchemat() {
        return schematid.get();
    }
    public String getUrl() {
        return url.get();
    }

    public String getUzytkownik() {
        return uzytkownik.get();
    }
    
    //public String getHaslo() {
    //    return haslo.get();
    //}
    
    public String getData_utw() {
        return datautw.get();
    }

    public String getOperator() {
        return operator.get();
    }
}
