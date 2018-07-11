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
        private final SimpleStringProperty data_utw;
        private final SimpleStringProperty operator;
        private final SimpleStringProperty data_wysl;
        private final SimpleStringProperty status;
        private final SimpleStringProperty przeladowanie;
        private final SimpleStringProperty zalezy_od_wersji;
        private final SimpleStringProperty folder;
        private final SimpleStringProperty jira;
        private final SimpleStringProperty odpowiedzialny;
        private final SimpleStringProperty uwagi;
        

    Skrypt(Integer id, String nazwa, String srodowisko, String data_utw, String operator, String data_wysl, String status, String przeladowanie, String zalezy_od_wersji, String folder, String jira, String odpowiedzialny, String uwagi) {
        this.id = new SimpleIntegerProperty(id);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.srodowisko = new SimpleStringProperty(srodowisko);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.operator = new SimpleStringProperty(operator);
        this.data_wysl = new SimpleStringProperty(data_wysl);
        this.status = new SimpleStringProperty(status);
        this.przeladowanie = new SimpleStringProperty(przeladowanie);
        this.zalezy_od_wersji = new SimpleStringProperty(zalezy_od_wersji);
        this.folder = new SimpleStringProperty(folder);
        this.jira = new SimpleStringProperty(jira);
        this.odpowiedzialny = new SimpleStringProperty(odpowiedzialny);
        this.uwagi = new SimpleStringProperty(uwagi);
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

    public String getData_utw() {
        return data_utw.get();
    }

    public String getOperator() {
        return operator.get();
    }

    public String getData_wysl() {
        return data_wysl.get();
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

    public String getZalezy_od_wersji() {
        return zalezy_od_wersji.get();
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

