/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.listkompilat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class listaKompilat {
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty oznaczenie;
    private final SimpleStringProperty satelita;
    private final SimpleStringProperty main;
    private final SimpleStringProperty prep;
    private final SimpleStringProperty rel;
    private final SimpleStringProperty faza3;
    private final SimpleStringProperty prepg;
    private final SimpleStringProperty prod;

    public listaKompilat(Integer id, String oznaczenie, String satelita, String main, String prep, String rel, String faza3, String prepg, String prod) {
        this.id = new SimpleIntegerProperty(id);
        this.oznaczenie = new SimpleStringProperty(oznaczenie);
        this.satelita = new SimpleStringProperty(satelita);
        this.main = new SimpleStringProperty(main);
        this.prep = new SimpleStringProperty(prep);
        this.rel = new SimpleStringProperty(rel);
        this.faza3 = new SimpleStringProperty(faza3);
        this.prepg = new SimpleStringProperty(prepg);
        this.prod = new SimpleStringProperty(prod);
    }

    public Integer getId() {
        return id.get();
    }

    public String getOznaczenie() {
        return oznaczenie.get();
    }

    public String getSatelita() {
        return satelita.get();
    }

    public String getMain() {
        return main.get();
    }

    public String getPrep() {
        return prep.get();
    }

    public String getRel() {
        return rel.get();
    }

    public String getFaza3() {
        return faza3.get();
    }

    public String getPrepg() {
        return prepg.get();
    }

    public String getProd() {
        return prod.get();
    }
    
    
    
    
}
