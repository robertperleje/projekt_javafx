/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.schemat;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class Schemat {
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty schemat;
    private final SimpleStringProperty data_utw;
    private final SimpleStringProperty operator;

    public Schemat(Integer id, String schemat, String data_utw, String operator) {
        this.id = new SimpleIntegerProperty(id);
        this.schemat = new SimpleStringProperty(schemat);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId() {
        return id.get();
    }

    public String getSchemat() {
        return schemat.get();
    }

    public String getData_utw() {
        return data_utw.get();
    }

    public String getOperator() {
        return operator.get();
    }
    
}
