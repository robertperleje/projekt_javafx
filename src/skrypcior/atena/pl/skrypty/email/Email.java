/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypty.email;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author perlro1
 */
public class Email
{
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty email;
    //private CheckBox adresat;
    private final SimpleBooleanProperty adresat;
    private final SimpleBooleanProperty przygot;
    private final SimpleStringProperty grupa;
    private final SimpleStringProperty data_utw;
    private final SimpleStringProperty operator;

    public Email(Integer id, String email, Boolean adresat, Boolean przygot, String grupa, String data_utw, String operator)
    {
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        //this.adresat = new CheckBox();
        this.adresat = new SimpleBooleanProperty(adresat);
        this.przygot = new SimpleBooleanProperty(przygot);
        this.grupa = new SimpleStringProperty(grupa);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId()
    {
        return id.get();
    }

    public String getEmail()
    {
        return email.get();
    }

    /*
    public CheckBox getAdresat() {
        return adresat;
    }
     */
    public Boolean getAdresat()
    {
        return adresat.get();
    }

    public Boolean getPrzygot()
    {
        return przygot.get();
    }

    public String getGrupa()
    {
        return grupa.get();
    }

    public String getData_utw()
    {
        return data_utw.get();
    }

    public String getOperator()
    {
        return operator.get();
    }

}
