/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class Skrypt {
        private final SimpleIntegerProperty  id;
        private final SimpleStringProperty nazwa;
        private final SimpleStringProperty srodowisko;
        private final SimpleStringProperty datautw;
        private final SimpleStringProperty operator;
        private final SimpleStringProperty datawysl;
        private final SimpleStringProperty status;
        private final SimpleStringProperty przeladowanie;
        private final SimpleStringProperty bazytestowe;
        private final SimpleStringProperty odwersji;
        private final SimpleStringProperty folder;
        private final SimpleStringProperty jira;
        private final SimpleStringProperty odpowiedzialny;
        private final SimpleStringProperty uwagi;
        

            

    Skrypt(Integer id, String nazwa, String srodowisko, String datautw, String operator, String datawysl, String status, String przeladowanie, String bazytestowe, String odwersji, String folder, String jira, String odpowiedzialny, String uwagi) {
        this.id = new SimpleIntegerProperty(id);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.srodowisko = new SimpleStringProperty(srodowisko);
        this.datautw = new SimpleStringProperty(datautw);
        this.operator = new SimpleStringProperty(operator);
        this.datawysl = new SimpleStringProperty(datawysl);
        this.status = new SimpleStringProperty(status);
        this.przeladowanie = new SimpleStringProperty(przeladowanie);
        this.bazytestowe = new SimpleStringProperty(bazytestowe);
        this.odwersji = new SimpleStringProperty(odwersji);
        this.folder = new SimpleStringProperty(folder);
        this.jira = new SimpleStringProperty(jira);
        this.odpowiedzialny = new SimpleStringProperty(odpowiedzialny);
        this.uwagi = new SimpleStringProperty(uwagi);
    }

    Skrypt()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id.get();
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public String getSrodowisko() {
        return srodowisko.get();
    }

    public String getDatautw() {
        return datautw.get();
    }

    public String getOperator() {
        return operator.get();
    }

    public String getDatawysl() {
        return datawysl.get();
    }

    public String getStatus() {
        return status.get();
    }
    
    public void setStatus(String strStatus){
        status.set(strStatus); 
    }

    public String getPrzeladowanie() {
        return przeladowanie.get();
    }

        public String getBazytestowe() {
        return bazytestowe.get();
    }
        
    public String getOdwersji() {
        return odwersji.get();
    }

    public String getFolder() {
        return folder.get();
    }

    public String getJira() {
        return jira.get();
    }

    public String getOdpowiedzialny() {
        return odpowiedzialny.get();
    }

    public String getUwagi() {
        return uwagi.get();
    }


        
       
        
    }

