/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.email;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class Email {
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nazwa;
    private final SimpleStringProperty data_utw;
    private final SimpleStringProperty operator;

    public Email(Integer id, String nazwa, String data_utw, String operator) {
        this.id = new SimpleIntegerProperty(id);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId() {
        return id.get();
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public String getData_utw() {
        return data_utw.get();
    }

    public String getOperator() {
        return operator.get();
    }
    
}
