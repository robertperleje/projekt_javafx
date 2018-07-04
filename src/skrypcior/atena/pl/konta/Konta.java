/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.konta;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author perlro1
 */
public class Konta {
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty login;
    private final SimpleStringProperty haslo;
    private final SimpleStringProperty imie;
    private final SimpleStringProperty nazwisko;
    private final SimpleStringProperty email;
    private final SimpleBooleanProperty blokowac;
    private final SimpleStringProperty data_utw;
    private final SimpleStringProperty data_mod;
    private final SimpleStringProperty operator;

    public Konta(Integer id, String login, String haslo, String imie, String nazwisko, String email, Boolean blokowac, String data_utw, String data_mod, String operator) {
        this.id = new SimpleIntegerProperty(id);
        this.login = new SimpleStringProperty(login);
        this.haslo = new SimpleStringProperty(haslo);
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.email = new SimpleStringProperty(email);
        this.blokowac = new SimpleBooleanProperty(blokowac);
        this.data_utw = new SimpleStringProperty(data_utw);
        this.data_mod = new SimpleStringProperty(data_mod);
        this.operator = new SimpleStringProperty(operator);
    }

    public Integer getId() {
        return id.get();
    }

    public String getLogin() {
        return login.get();
    }

    public String getHaslo() {
        return haslo.get();
    }

    public String getImie() {
        return imie.get();
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public String getEmail() {
        return email.get();
    }

    public Boolean getBlokowac() {
        return blokowac.get();
    }

    public String getData_utw() {
        return data_utw.get();
    }

    public String getData_mod() {
        return data_mod.get();
    }

    public String getOperator() {
        return operator.get();
    }

    

    
    
}
